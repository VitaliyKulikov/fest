/*
 * Created on Jan 17, 2008
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
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.*;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JTreePathBoundsQuery.pathBoundsOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.swing.timing.Pause.pause;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JTreeLocation}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTreeLocationTest {

  private Robot robot;
  private JTree tree;
  private DefaultMutableTreeNode treeRoot;
  private JTreeLocation location;
  private List<TreePath> paths;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    tree = window.tree;
    treeRoot = window.treeRoot;
    robot.showWindow(window, new Dimension(200, 200));
    location = new JTreeLocation();
    populatePaths();
  }

  private void populatePaths() {
    paths = new ArrayList<TreePath>();
    paths.add(rootPath());
    paths.add(node1Path());
    paths.add(node11Path());
    paths.add(childOf(node11Path(), 0));
    paths.add(childOf(node11Path(), 1));
    paths.add(childOf(node1Path(), 1));
    paths.add(childOf(rootPath(), 1));
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test(groups = GUI, dataProvider = "pathIndices")
  public void shouldFindLocationOfTreePath(int pathIndex) {
    TreePath path = paths.get(pathIndex);
    pause(160);
    Point actual = location.pointAt(tree, path);
    Rectangle pathBounds = pathBoundsOf(tree, path);
    Point expected = new Point(pathBounds.x + pathBounds.width / 2, pathBounds.y + pathBounds.height / 2);
    assertThat(actual).isEqualTo(expected);
  }

  @DataProvider(name = "pathIndices")
  public Object[][] pathIndices() {
    return new Object[][] {
        { 0 }, { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 }
    };
  }

  private TreePath node11Path() {
    return childOf(node1Path(), 0);
  }

  private TreePath node1Path() {
    return childOf(rootPath(), 0);
  }

  private TreePath rootPath() {
    return new TreePath(array(treeRoot));
  }

  private TreePath childOf(TreePath parent, int index) {
    TreeNode child = childOf(parent.getLastPathComponent(), index);
    final TreePath childPath = parent.pathByAddingChild(child);
    execute(new GuiTask() {
      protected void executeInEDT() {
        tree.expandPath(childPath);
      }
    });
    robot.waitForIdle();
    return childPath;
  }

  private TreeNode childOf(Object parent, int index) {
    return ((DefaultMutableTreeNode)parent).getChildAt(index);
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    static MyWindow createNew() {
      return new MyWindow();
    }

    final JTree tree = new JTree();
    final DefaultMutableTreeNode treeRoot;

    private MyWindow() {
      super(JTreeLocationTest.class);
      treeRoot = root();
      tree.setModel(new DefaultTreeModel(treeRoot));
      tree.setPreferredSize(new Dimension(200, 200));
      add(tree);
    }

    private DefaultMutableTreeNode root() {
      return node("root",
          node("node1",
              node("node11",
                  node("node111"),
                  node("node112")
                  ),
              node("node12")
              ),
          node("node2")
          );
    }

    private DefaultMutableTreeNode node(String text, MutableTreeNode...children) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(text);
      for (MutableTreeNode child : children) node.add(child);
      return node;
    }
  }

}
