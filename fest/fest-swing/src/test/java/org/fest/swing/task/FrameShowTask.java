/*
 * Created on Sep 9, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.task;

import java.awt.Dimension;
import java.awt.Frame;

import org.fest.swing.core.Condition;
import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that makes a <code>{@link Frame}</code> visible. This task is executed in the event dispatch
 * thread.
 * 
 * @author Alex Ruiz
 */
public final class FrameShowTask {

  public static void packAndSetVisible(final Frame frame) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        frame.pack();
        frame.setVisible(true);
      }
    }, new FrameIsShowingCondition(frame));
  }
  
  public static void packAndSetVisible(final Frame frame, final Dimension preferredSize) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        frame.setPreferredSize(preferredSize);
        frame.pack();
        frame.setVisible(true);
      }
    }, new FrameIsShowingCondition(frame));
  }

  private static class FrameIsShowingCondition extends Condition {
    private final Frame frame;

    FrameIsShowingCondition(Frame frame) {
      super("Frame is showing");
      this.frame = frame;
    }

    public boolean test() {
      return frame.isShowing();
    }
  }
  
  private FrameShowTask() {}
}
