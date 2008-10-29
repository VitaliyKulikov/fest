/*
 * Created on Jan 27, 2008
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
package org.fest.swing.awt;

import java.awt.*;
import java.awt.event.InputEvent;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.fest.swing.edt.GuiActionExecutionType;
import org.fest.swing.util.MouseEventTarget;

import static java.awt.event.InputEvent.*;
import static javax.swing.SwingUtilities.convertPoint;

import static org.fest.reflect.core.Reflection.method;
import static org.fest.swing.edt.GuiActionExecutionType.RUN_IN_EDT;
import static org.fest.swing.query.ComponentLocationOnScreenQuery.locationOnScreen;
import static org.fest.swing.query.ComponentNameQuery.nameOf;
import static org.fest.swing.query.ComponentParentQuery.parentOf;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;
import static org.fest.swing.query.ComponentSizeQuery.sizeOf;
import static org.fest.swing.query.ContainerInsetsQuery.insetsOf;
import static org.fest.swing.query.JComponentVisibleRectQuery.visibleRectOf;
import static org.fest.swing.util.Platform.isWindows;
import static org.fest.util.Strings.*;

/**
 * Understands utility methods related to AWT.
 *
 * @author Alex Ruiz
 */
public class AWT {

  private static final String APPLET_APPLET_VIEWER_CLASS = "sun.applet.AppletViewer";
  private static final String ROOT_FRAME_CLASSNAME = concat(SwingUtilities.class.getName(), "$");

  // Abbot: Macintosh *used* to map button2 to the pop-up trigger (1.3). Not clear when this changed.
  private static final boolean POPUP_ON_BUTTON2 = false;

  
  /**
   * Returns a point at the center of the given <code>{@link Component}</code>.
   * @param c the given <code>Component</code>.
   * @param executionType specifies in which thread this method should be executed.
   * @return a point at the center of the given <code>Component</code>.
   */
  public static Point centerOf(Component c, GuiActionExecutionType executionType) {
    if (shouldRunInEDT(executionType)) return centerOf(c);
    return centerOf(c.getSize());
  }

  /**
   * Returns a point at the center of the given <code>{@link Component}</code>. This method is executed in the event 
   * dispatch thread.
   * @param c the given <code>Component</code>.
   * @return a point at the center of the given <code>Component</code>.
   */
  public static Point centerOf(Component c) {
    return centerOf(sizeOf(c));
  }

  private static Point centerOf(Dimension d) {
    return new Point(d.width / 2, d.height / 2);
  }

  /**
   * Returns a point at the center of the visible rectangle of the given <code>{@link JComponent}</code>. This method is
   * executed in the event dispatch thread.
   * @param c the given <code>JComponent</code>.
   * @param executionType specifies in which thread this method should be executed.
   * @return a point at the center of the visible rectangle of the given <code>JComponent</code>.
   */
  public static Point centerOfVisibleRect(JComponent c, GuiActionExecutionType executionType) {
    if (shouldRunInEDT(executionType)) return centerOfVisibleRect(c);
    return centerOf(c.getVisibleRect());
  }

  /**
   * Returns a point at the center of the visible rectangle of the given <code>{@link JComponent}</code>. This method is
   * executed in the event dispatch thread.
   * @param c the given <code>JComponent</code>.
   * @return a point at the center of the visible rectangle of the given <code>JComponent</code>.
   */
  public static Point centerOfVisibleRect(JComponent c) {
    return centerOf(visibleRectOf(c));
  }

  private static Point centerOf(Rectangle r) {
    return new Point((r.x + (r.width / 2)), (r.y + (r.height / 2)));
  }

  /**
   * Returns the insets of the given <code>{@link Container}</code>, or an empty one if no insets can be found.
   * @param c the given <code>Container</code>.
   * @return the insets of the given <code>Container</code>, or an empty one if no insets can be found.
   */
  public static Insets insetsFrom(Container c) {
    try {
      Insets insets = insetsOf(c);
      if (insets != null) return insets;
    } catch (Exception e) {}
    return new Insets(0, 0, 0, 0);
  }

  /**
   * Returns <code>true</code> if the given component is an Applet viewer.
   * @param c the component to check.
   * @return <code>true</code> if the given component is an Applet viewer, <code>false</code> otherwise.
   */
  public static boolean isAppletViewer(Component c) {
    return c != null && APPLET_APPLET_VIEWER_CLASS.equals(c.getClass().getName());
  }

  /**
   * Returns whether the given component is the default Swing hidden frame.
   * @param c the component to check.
   * @return <code>true</code> if the given component is the default hidden frame, <code>false</code> otherwise.
   */
  public static boolean isSharedInvisibleFrame(Component c) {
    if (c == null) return false;
    // Must perform an additional check, since applets may have their own version in their AppContext
    return c instanceof Frame
        && (c == JOptionPane.getRootFrame() || c.getClass().getName().startsWith(ROOT_FRAME_CLASSNAME));
  }

  /**
   * Returns the name of the given component. If the component is <code>null</code>, this method will return
   * <code>null</code>.
   * @param c the given component.
   * @return the name of the given component, or <code>null</code> if the component is <code>null</code>.
   */
  public static String quoteNameOf(Component c) {
    if (c == null) return null;
    return quote(nameOf(c));
  }

  /**
   * Returns the invoker, if any, of the given <code>{@link Component}</code>; or <code>null</code>, if the
   * <code>Component</code> is not on a pop-up of any sort. <b>Note:</b> This method is <b>not</b> executed in the event
   * dispatch thread.
   * @param c the given <code>Component</code>.
   * @return the invoker, if any, of the given <code>Component</code>; or <code>null</code>, if the
   *         <code>Component</code> is not on a pop-up of any sort.
   */
  public static Component invokerOf(final Component c) {
    if (c instanceof JPopupMenu) return ((JPopupMenu)c).getInvoker();
    Container parent = c.getParent();
    return parent != null ? invokerOf(parent) : null;
  } 

  /**
   * Safe version of <code>{@link Component#getLocationOnScreen}</code>, which avoids lockup if an AWT pop-up menu is
   * showing. The AWT pop-up holds the AWT tree lock when showing, which lock is required by
   * <code>{@link Component#getLocationOnScreen}.</code>
   * @param c the given <code>Component</code>.
   * @return the a point specifying the <code>Component</code>'s top-left corner in the screen's coordinate space, or
   *         <code>null</code>, if the <code>Component</code> is not showing on the screen.
   */
  public static Point locationOnScreenOf(Component c) {
    // TODO one query only!
    if (!isAWTTreeLockHeld()) return new Point(locationOnScreen(c));
    if (!isShowing(c)) return null;
    Point location = new Point(c.getLocation());
    if (c instanceof Window) return location;
    Container parent = c.getParent();
    if (parent == null) return null;
    Point parentLocation = locationOnScreenOf(parent);
    location.translate(parentLocation.x, parentLocation.y);
    return location;
  }

  /**
   * Returns whether there is an AWT pop-up menu currently showing.
   * @return <code>true</code> if an AWT pop-up menu is currently showing, <code>false</code> otherwise.
   */
  public static boolean isAWTPopupMenuBlocking() {
    // Abbot: For now, just do a quick check to see if a PopupMenu is active on w32. Extend it if we find other common
    // situations that might block the EDT, but for now, keep it simple and restricted to what we've run into.
    return /* Bugs.showAWTPopupMenuBlocks() && */isAWTTreeLockHeld();
  }

  /**
   * If a <code>Component</code> does not have mouse events enabled, use the first ancestor which does.
   * @param c the given <code>Component</code>.
   * @param eventId the id of the mouse event to verify.
   * @param where the x,y coordinates relative to the given <code>Component</code>.
   * @return the new target for the mouse event.
   */
  public static MouseEventTarget retargetMouseEvent(final Component c, int eventId, final Point where) {
    Component source = c;
    Point coordinates = where;
    while (!(c instanceof Window) && !eventTypeEnabled(source, eventId)) {
      coordinates = convertPoint(source, coordinates.x, coordinates.y, parentOf(source));
      source = parentOf(source);
    }
    return new MouseEventTarget(source, coordinates);
  }

  /**
   * Returns whether the platform registers a pop-up on mouse press.
   * @return <code>true</code> if the platform registers a pop-up on mouse press, <code>false</code> otherwise.
   */
  public static boolean popupOnPress() {
    // Only w32 is pop-up on release
    return !isWindows();
  }

  /**
   * Returns the <code>{@link InputEvent}</code> mask for the pop-up trigger button.
   * @return the <code>InputEvent</code> mask for the pop-up trigger button.
   */
  public static int popupMask() {
    return POPUP_ON_BUTTON2 ? BUTTON2_MASK : BUTTON3_MASK;
  }

  public static boolean eventTypeEnabled(Component c, int eventId) {
    // certain AWT components should have events enabled, even if they claim not to.
    if (c instanceof Choice) return true;
    try {
      AWTEvent event = fakeAWTEventFrom(c, eventId);
      return method("eventEnabled").withReturnType(boolean.class)
                                    .withParameterTypes(AWTEvent.class)
                                    .in(c).invoke(event);
    } catch (RuntimeException e) {
      return true;
    }
  }

  private static AWTEvent fakeAWTEventFrom(Component c, int eventId) {
    return new AWTEvent(c, eventId) {
      private static final long serialVersionUID = 1L;
    };
  }

  private static boolean shouldRunInEDT(GuiActionExecutionType executionType) {
    return RUN_IN_EDT.equals(executionType);
  }

  /**
   * Indicates whether the AWT Tree Lock is currently held.
   * @return <code>true</code> if the AWT Tree Lock is currently held, <code>false</code> otherwise.
   */
  public static boolean isAWTTreeLockHeld() {
    Frame[] frames = Frame.getFrames();
    if (frames.length == 0) return false;
    // From Abbot: Hack based on 1.4.2 java.awt.PopupMenu implementation, which blocks the event dispatch thread while
    // the pop-up is visible, while holding the AWT tree lock.
    // Start another thread which attempts to get the tree lock.
    // If it can't get the tree lock, then there is a pop-up active in the current tree.
    // Any component can provide the tree lock.
    ThreadStateChecker checker = new ThreadStateChecker(frames[0].getTreeLock());
    try {
      checker.start();
      // wait a little bit for the checker to finish
      if (checker.isAlive()) checker.join(100);
      return checker.isAlive();
    } catch (InterruptedException e) {
      return false;
    }
  }

  // Try to lock the AWT tree lock; returns immediately if it can
  private static class ThreadStateChecker extends Thread {
    public boolean started;
    private final Object lock;

    public ThreadStateChecker(Object lock) {
      super("Thread state checker");
      setDaemon(true);
      this.lock = lock;
    }

    @Override public synchronized void start() {
      super.start();
      try {
        wait(30000);
      } catch (InterruptedException e) {}
    }

    @Override public void run() {
      synchronized (this) {
        started = true;
        notifyAll();
      }
      synchronized (lock) {
        setName(super.getName()); // dummy operation
      }
    }
  }

  private AWT() {}
}