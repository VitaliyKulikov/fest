/*
 * Created on Aug 11, 2008
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
package org.fest.swing.driver;

import javax.swing.JTextField;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTextComponentSelectTextTask.selectTextInRange;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JTextComponentSelectionEndQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = { GUI, EDT_ACTION })
public class JTextComponentSelectionEndQueryTest {

  private Robot robot;
  private MyTextField textField;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    textField = window.textField;
    robot.showWindow(window);
  }
  
  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }
  
  public void shouldReturnSelectionEndOfJTextComponent() {
    selectTextInRange(textField, 0, 5);
    robot.waitForIdle();
    textField.startRecording();
    assertThat(JTextComponentSelectionEndQuery.selectionEndOf(textField)).isEqualTo(5);
    textField.requireInvoked("getSelectionEnd");
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }
    
    final MyTextField textField = new MyTextField();
    
    private MyWindow() {
      super(JTextComponentSelectionEndQueryTest.class);
      addComponents(textField);
    }
  }
  
  private static class MyTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyTextField() {
      super(20);
      setText("Hello World");
    }

    @Override public int getSelectionEnd() {
      if (recording) methodInvocations.invoked("getSelectionEnd");
      return super.getSelectionEnd();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}
