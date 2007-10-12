/*
 * Created on Oct 11, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.monitor;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static java.awt.AWTEvent.PAINT_EVENT_MASK;
import static org.fest.assertions.Assertions.assertThat;

import static org.fest.swing.util.ToolkitUtils.toolkitHasListenerUnderEventMask;

import org.fest.swing.TestFrame;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link WindowAvailabilityMonitor}</code>.
 *
 * @author Alex Ruiz
 */
public class WindowAvailabilityMonitorTest {

  private static final long EVENT_MASK = MOUSE_MOTION_EVENT_MASK | MOUSE_EVENT_MASK | PAINT_EVENT_MASK;

  private WindowAvailabilityMonitor monitor;
  
  private Windows windows;
  private TestFrame frame;

  @BeforeMethod public void setUp() {
    frame = new TestFrame(getClass());
    windows = new Windows();
  }

  @AfterMethod public void tearDown() {
    frame.beDisposed();
  }

  @Test public void shouldAttachItSelfToToolkit() {
    attachMonitor();
    WeakEventListener l = new WeakEventListener(monitor);
    assertThat(toolkitHasListenerUnderEventMask(l, EVENT_MASK)).isTrue();
  }

  @Test(dependsOnMethods = "shouldAttachItSelfToToolkit") 
  public void shouldMarkSourceWindowAsReadyIfEventIsMouseEvent() {
    attachMonitor();
    windows.simulatePendingWindow(frame);
    monitor.eventDispatched(mouseEvent(frame));
    assertThat(windows.isReady(frame)).isTrue();
  }
  
  @Test(dependsOnMethods = "shouldAttachItSelfToToolkit") 
  public void shouldMarkSourceWindowAncestorAsReadyIfEventIsMouseEvent() {
    attachMonitor();
    windows.simulatePendingWindow(frame);
    JTextField source = new JTextField();
    frame.add(source);
    monitor.eventDispatched(mouseEvent(source));
    assertThat(windows.isReady(frame)).isTrue();
  }
  
  @Test(dependsOnMethods = "shouldAttachItSelfToToolkit") 
  public void shouldNotMarkSourceWindowAsReadyIfEventIsNotMouseEvent() {
    attachMonitor();
    windows.simulatePendingWindow(frame);
    monitor.eventDispatched(new KeyEvent(frame, 8, 9238, 0, 0, 'a'));
    assertThat(windows.isReady(frame)).isFalse();
    assertThat(windows.isShowingButNotReady(frame)).isTrue();
  }

  private void attachMonitor() {
    monitor = WindowAvailabilityMonitor.attachWindowAvailabilityMonitor(windows);
  }
  
  private MouseEvent mouseEvent(Component source) {
    return new MouseEvent(source, 8, 8912, 0, 0, 0, 0, false, 0);
  }
}
