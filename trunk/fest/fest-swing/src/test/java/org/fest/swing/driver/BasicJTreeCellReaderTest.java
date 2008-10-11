/*
 * Created on Apr 12, 2008
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
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiTask;
import org.fest.swing.core.Robot;
import org.fest.swing.testing.CustomCellRenderer;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicJTreeCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJTreeCellReaderTest {

  private Robot robot;
  private JTree tree;
  private BasicJTreeCellReader reader;
  private DefaultMutableTreeNode root;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = new MyWindow();
    root = window.root;
    tree = window.tree;
    reader = new BasicJTreeCellReader();
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabel() {
    JLabel label = new JLabel("First");
    setCellRendererComponent(tree, label);
    robot.waitForIdle();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo("First");
  }

  public void shouldReturnTextFromTreeIfRendererIsNotJLabel() {
    setCellRendererComponent(tree, unrecognizedRenderer());
    robot.waitForIdle();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isEqualTo(root.getUserObject());
  }

  public void shouldReturnNullIfTextOfModelValueIsDefaultToString() {
    class Person {}
    root = new DefaultMutableTreeNode(new Person());
    setRootInTree(tree, root);
    setCellRendererComponent(tree, unrecognizedRenderer());
    robot.waitForIdle();
    Object value = reader.valueAt(tree, root);
    assertThat(value).isNull();
  }

  private static void setRootInTree(final JTree tree, final DefaultMutableTreeNode root) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        ((DefaultTreeModel)tree.getModel()).setRoot(root);
      }
    });
  }

  private static void setCellRendererComponent(final JTree tree, final Component renderer) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.setCellRenderer(new CustomCellRenderer(renderer));
      }
    });
  }

  private static Component unrecognizedRenderer() {
    return new JToolBar();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JTree tree;
    final DefaultMutableTreeNode root;

    private MyWindow() {
      super(BasicJTreeCellReaderTest.class);
      root = newRoot();
      tree = new JTree(root);
      tree.setPreferredSize(new Dimension(100, 100));
      addComponents(tree);
    }

    private static DefaultMutableTreeNode newRoot() {
      DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
      rootNode.add(new DefaultMutableTreeNode("Node1"));
      return rootNode;
    }
  }
}