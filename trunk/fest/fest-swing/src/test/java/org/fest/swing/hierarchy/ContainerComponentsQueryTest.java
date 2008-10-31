/*
 * Created on Aug 26, 2008
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
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.util.List;

import javax.swing.JButton;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.ScreenLock;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link ContainerComponentsQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ContainerComponentsQueryTest {

  private MyWindow window;

  @BeforeMethod public void setUp() {
    ScreenLock.instance().acquire(this);
    window = MyWindow.createAndShow();
  }

  @AfterMethod public void tearDown() {
    try {
      window.destroy();
    } finally {
      ScreenLock.instance().release(this);
    }
  }

  public void shouldReturnComponentsOfContainer() {
    window.startRecording();
    assertThat(componentsOf(window)).containsOnly(window.button);
    window.requireInvoked("getComponents");
  }

  private static List<Component> componentsOf(final MyWindow window) {
    return execute(new GuiQuery<List<Component>>() {
      protected List<Component> executeInEDT() {
        return ContainerComponentsQuery.componentsOf(window.getContentPane());
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JButton button = new JButton("A button");

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    @RunsInEDT
    static MyWindow createAndShow() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          MyWindow window = new MyWindow();
          window.displayInCurrentThread();
          return window;
        }
      });
    }

    private void displayInCurrentThread() {
      TestWindow.display(this);
    }

    private MyWindow() {
      super(ContainerComponentsQueryTest.class);
      addComponents(button);
    }

    @Override public Component[] getComponents() {
      if (recording) methodInvocations.invoked("getComponents");
      return super.getComponents();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
