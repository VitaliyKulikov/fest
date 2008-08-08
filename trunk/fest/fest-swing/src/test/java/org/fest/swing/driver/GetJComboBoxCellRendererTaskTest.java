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
package org.fest.swing.driver;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiTask;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.task.GetJLabelTextTask.textOf;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link GetJComboBoxCellRendererTask}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GetJComboBoxCellRendererTaskTest {

  private MyFrame frame;

  @BeforeMethod public void setUp() {
    frame = new GuiTask<MyFrame>() {
      protected MyFrame executeInEDT() throws Throwable {
        return new MyFrame();
      }
    }.run();
    frame.display();
  }

  @AfterMethod public void tearDown() {
    frame.destroy();
  }

  @Test(dataProvider = "comboBoxContents", groups = GUI)
  public void shouldReturnCellRendererComponentOfJComboBox(int index, String itemText) {
    Component renderer = GetJComboBoxCellRendererTask.cellRendererIn(frame.comboBox, index);
    assertThat(renderer).isInstanceOf(JLabel.class);
    assertThat(textOf((JLabel)renderer)).isEqualTo(itemText);
  }

  @DataProvider(name = "comboBoxContents") public Object[][] comboBoxContents() {
    return new Object[][] {
        { 0, "one" },
        { 1, "two" },
        { 2, "three" },
    };
  }

  private static class MyFrame extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(new Object[] { "one", "two", "three" });

    MyFrame() {
      super(GetJComboBoxCellRendererTaskTest.class);
      add(comboBox);
    }
  }
}
