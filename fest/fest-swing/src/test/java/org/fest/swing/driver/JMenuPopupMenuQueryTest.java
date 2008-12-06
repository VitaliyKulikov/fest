/*
 * Created on Aug 9, 2008
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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.testing.MethodInvocations;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JMenuPopupMenuQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, ACTION })
public class JMenuPopupMenuQueryTest {

  private Robot robot;
  private MyMenu menu;

  @BeforeClass public void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    menu = window.menu;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnJPopupMenuInJMenu() {
    JPopupMenu expected = popupMenuOf(menu);
    menu.startRecording();
    JPopupMenu actual = JMenuPopupMenuQuery.popupMenuOf(menu);
    assertThat(actual).isNotNull()
                      .isSameAs(expected);
    menu.requireInvoked("getPopupMenu");
  }

  @RunsInEDT
  private static JPopupMenu popupMenuOf(final MyMenu menu) {
    return execute(new GuiQuery<JPopupMenu>() {
      protected JPopupMenu executeInEDT() {
        return menu.getPopupMenu();
      }
    });
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyMenu menu = new MyMenu("Hello");

    @RunsInEDT
    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JMenuPopupMenuQueryTest.class);
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(menu);
      setJMenuBar(menuBar);
    }
  }

  private static class MyMenu extends JMenu {
    private static final long serialVersionUID = 1L;

    private boolean recording;
    private final MethodInvocations methodInvocations = new MethodInvocations();

    MyMenu(String text) {
      super(text);
    }

    @Override public JPopupMenu getPopupMenu() {
      if (recording) methodInvocations.invoked("getPopupMenu");
      return super.getPopupMenu();
    }

    void startRecording() { recording = true; }

    MethodInvocations requireInvoked(String methodName) {
      return methodInvocations.requireInvoked(methodName);
    }
  }
}