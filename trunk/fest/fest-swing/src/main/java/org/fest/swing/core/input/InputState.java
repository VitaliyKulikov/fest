/*
 * Created on Mar 28, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.core.input;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import abbot.util.EventNormalizer;

import org.fest.swing.listener.SingleThreadedEventListener;

import static java.awt.AWTEvent.*;
import static javax.swing.SwingUtilities.getDeepestComponentAt;

import static org.fest.swing.core.input.MouseInfo.BUTTON_MASK;
import static org.fest.swing.util.AWT.locationOnScreenOf;

/**
 * Class to keep track of a given input state. Includes mouse/pointer position and keyboard modifier key state.
 * <p>
 * Synchronization assumes that any given instance might be called from more than one event dispatch thread.
 * <p>
 */
// TODO: add a BitSet with the full keyboard key press state
public class InputState {

  private final MouseInfo mouseInfo = new MouseInfo();
  private final DragDropInfo dragDropInfo = new DragDropInfo();

  private int modifiers;
  private long lastEventTime;
  private EventNormalizer normalizer;

  public InputState() {
    long mask = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | KEY_EVENT_MASK;
    AWTEventListener listener = new SingleThreadedEventListener() {
      protected void processEvent(AWTEvent event) {
        update(event);
      }
    };
    normalizer = new EventNormalizer();
    normalizer.startListening(listener, mask);
  }

  public synchronized void clear() {
    mouseInfo.clear();
    dragDropInfo.clear();
    modifiers = 0;
    lastEventTime = 0;
  }

  public void dispose() {
    normalizer.stopListening();
    normalizer = null;
  }

  /*
   * Explicitly update the internal state. Allows Robot to update the state with events it has just posted.
   */
  void update(AWTEvent event) {
    if (event instanceof KeyEvent) updateState((KeyEvent) event);
    if (event instanceof MouseEvent) updateState((MouseEvent) event);
  }

  protected void updateState(KeyEvent event) {
    if (isOld(event)) return;
    synchronized (this) {
      lastEventTime(event);
      modifiers(event.getModifiers());
      // FIXME add state of individual keys
    }
  }

  protected void updateState(MouseEvent event) {
    if (isOld(event)) return;
    // childAt and locationOnScreenOf want the tree lock, so be careful not to use any additional locks at the same time
    // to avoid deadlock.
    Point eventScreenLocation = null;
    // Determine the current mouse position in screen coordinates
    try {
      eventScreenLocation = locationOnScreenOf(event.getComponent());
    } catch (IllegalComponentStateException e) {
      // component might be hidden by the time we process this event
    }
    synchronized (this) {
      lastEventTime(event);
      dragDropInfo.update(event);
      mouseInfo.modifiers(modifiers);
      mouseInfo.update(event, eventScreenLocation);
      modifiers(mouseInfo.modifiers());
    }
  }

  private boolean isOld(InputEvent event) {
    return event.getWhen() < lastEventTime;
  }

  /**
   * Returns the most deeply nested component which currently contains the pointer.
   * @return the most deeply nested component which currently contains the pointer.
   */
  public synchronized Component deepestComponentUnderMousePointer() {
    Component c = mouseComponent();
    if (c != null) c = childAt(c, mouseLocation());
    return c;
  }

  /**
   * Returns the last known Component to contain the pointer, or <code>null</code> if none. Note that this may not
   * correspond to the component that actually shows up in AWTEvents.
   * @return the last known Component to contain the pointer, or <code>null</code> if none.
   */
  public synchronized Component mouseComponent() {
    return mouseInfo.component();
  }

  /**
   * Returns the component under the given coordinates in the given parent component. Events are often generated only
   * for the outermost container, so we have to determine if the pointer is actually within a child. Basically the same
   * as Component.getComponentAt, but recurses to the lowest-level component instead of only one level. Point is in
   * component coordinates.
   * <p>
   * The default Component.getComponentAt can return invisible components (JRootPane has an invisible JPanel (glass
   * pane?) which will otherwise swallow everything).
   * <p>
   * NOTE: childAt grabs the TreeLock, so this should *only* be invoked on the event dispatch thread, preferably with no
   * other locks held. Use it elsewhere at your own risk.
   * <p>
   * @param parent the given parent.
   * @param where the given coordinates.
   * @return the component under the given coordinates in the given parent component.
   */
  public static Component childAt(Component parent, Point where) {
    return getDeepestComponentAt(parent, where.x, where.y);
  }

  public synchronized boolean isDragging() {
    return dragDropInfo.isDragging();
  }

  public synchronized Component dragSource() {
    return dragDropInfo.source();
  }

  public synchronized void dragSource(Component c) {
    dragDropInfo.source(c);
  }

  public synchronized Point dragOrigin() {
    return dragDropInfo.origin();
  }

  public synchronized int clickCount() {
    return mouseInfo.clickCount();
  }

  protected synchronized void clickCount(int clickCount) {
    mouseInfo.clickCount(clickCount);
  }

  public synchronized long lastEventTime() {
    return lastEventTime;
  }

  protected synchronized void lastEventTime(InputEvent event) {
    lastEventTime = event.getWhen();
  }

  /**
   * Returns all currently active modifiers.
   * @return all currently active modifiers.
   */
  public synchronized int modifiers() {
    return modifiers;
  }

  protected synchronized void modifiers(int modifiers) {
    this.modifiers = modifiers;
  }

  /**
   * Returns the currently pressed key modifiers.
   * @return the currently pressed key modifiers.
   */
  public synchronized int keyModifiers() {
    return modifiers & ~BUTTON_MASK;
  }

  public synchronized int buttons() {
    return mouseInfo.buttons();
  }

  protected synchronized void buttons(int buttons) {
    mouseInfo.button(buttons);
  }

  /**
   * Returns the mouse location relative to the component that currently contains the pointer, or <code>null</code> if
   * outside all components.
   * @return the mouse location relative to the component that currently contains the pointer, or <code>null</code> if
   *         outside all components.
   */
  public synchronized Point mouseLocation() {
    return mouseInfo.location();
  }

  /**
   * Returns the last known mouse location.
   * @return the last known mouse location.
   */
  public synchronized Point mouseLocationOnScreen() {
    return mouseInfo.locationOnScreen();
  }

  /**
   * Return whether there is a native drag/drop operation in progress.
   * @return <code>true</code> if there is a native drag/drop operation in progress.
   */
  public boolean isNativeDragActive() {
    return dragDropInfo.isNativeDragActive();
  }
}
