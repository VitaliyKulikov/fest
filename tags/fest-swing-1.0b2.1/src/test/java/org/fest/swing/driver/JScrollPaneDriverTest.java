/*
 * Created on Sep 2, 2008
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

import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link JScrollPaneDriver}</code>.
 *
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class JScrollPaneDriverTest {

  private Robot robot;
  private MyWindow window;
  private JScrollPaneDriver driver;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    window = MyWindow.createNew();
    driver = new JScrollPaneDriver(robot);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnHorizontalScrollBar() {
    JScrollBar horizontalScrollBar = driver.horizontalScrollBarIn(window.scrollPane);
    assertThat(horizontalScrollBar).isSameAs(window.horizontalScrollBar);
  }

  public void shouldReturnVerticalScrollBar() {
    JScrollBar verticalScrollBar = driver.verticalScrollBarIn(window.scrollPane);
    assertThat(verticalScrollBar).isSameAs(window.verticalScrollBar);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JScrollPane scrollPane = new JScrollPane();
    final JScrollBar horizontalScrollBar = new JScrollBar();
    final JScrollBar verticalScrollBar = new JScrollBar();

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JScrollPane.class);
      add(scrollPane);
      scrollPane.setHorizontalScrollBar(horizontalScrollBar);
      scrollPane.setVerticalScrollBar(verticalScrollBar);
      setPreferredSize(new Dimension(300, 300));
    }
  }

}
