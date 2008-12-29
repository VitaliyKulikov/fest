/*
 * Created on Aug 6, 2008
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
package org.fest.swing.query;

import static java.awt.Color.BLUE;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithCurrentAwtHierarchy;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;
import static org.fest.swing.testing.TestGroups.GUI;

import java.awt.Color;
import java.awt.Dimension;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ComponentForegroundQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class ComponentForegroundQueryTest {

  private static final Color FOREGROUND = BLUE;

  private Robot robot;
  private MyWindow window;

  @BeforeMethod public void setUp() {
    robot = robotWithCurrentAwtHierarchy();
    window = MyWindow.createNew();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnComponentForeground() {
    window.startRecording();
    assertThat(ComponentForegroundQuery.foregroundOf(window)).isEqualTo(FOREGROUND);
    window.requireInvoked("getForeground");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(ComponentForegroundQueryTest.class);
      setForeground(FOREGROUND);
      setPreferredSize(new Dimension(500, 300));
    }

    @Override public Color getForeground() {
      if (recording) methodInvocations.invoked("getForeground");
      return super.getForeground();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}