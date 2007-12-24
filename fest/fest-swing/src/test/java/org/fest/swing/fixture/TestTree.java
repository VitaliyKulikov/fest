/*
 * Created on Aug 26, 2007
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
package org.fest.swing.fixture;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import static org.fest.util.Strings.isEmpty;

/**
 * Understands a tree that:
 * <ul>
 * <li>requires a name</li>
 * <li>supports drag and drop</li>
 * </ul>
 *
 * @author Alex Ruiz
 */
final class TestTree extends JTree {
  private static final long serialVersionUID = 1L;

  TestTree(String name, DefaultTreeModel model) {
    if (isEmpty(name)) throw new IllegalArgumentException("'name' cannot be null");
    setDragEnabled(true);
    setModel(model);
    setName(name);
    setTransferHandler(new TreeTransferHandler());
  }
  
  private static class TreeTransferHandler extends StringTransferHandler<JTree> {
    private static final long serialVersionUID = 1L;

    protected String exportString(JTree tree) {
      rows = tree.getSelectionRows();
      StringBuilder b = new StringBuilder();
      for (int i = 0; i < rows.length; i++) {
        TreePath path = tree.getPathForRow(rows[i]);
        Object val = path.getLastPathComponent();
        b.append(val == null ? "" : val.toString());
        if (i != rows.length - 1) b.append("\n");
      }
      return b.toString();
    }

    protected void importString(JTree target, String s) {
      DefaultTreeModel model = (DefaultTreeModel) target.getModel();
      int index = target.getRowForPath(target.getSelectionPath());
      // Prevent the user from dropping data back on itself.
      if (rows != null && index >= rows[0] - 1 && index <= rows[rows.length - 1]) {
        rows = null;
        return;
      }
      int max = target.getRowCount();
      if (index < 0) index = max;
      else if (index > max) index = max;
      addIndex = index;
      String[] values = s.split("\n");
      addCount = values.length;
      for (String value : values) {
        TreePath path = target.getPathForRow(index++);
        model.insertNodeInto(new DefaultMutableTreeNode(value), (MutableTreeNode) path.getLastPathComponent(), 0);
      }
    }

    // not working perfectly right, but good enough for testing.
    protected void cleanup(JTree source, boolean remove) {
      if (remove && rows != null) {
        DefaultTreeModel model = (DefaultTreeModel) source.getModel();
        // If we are moving items around in the same table, we need to adjust the rows accordingly, since those after the 
        // insertion point have moved.
        if (addCount > 0) {
          for (int i = 0; i < rows.length; i++) 
            if (rows[i] > addIndex) rows[i] += addCount;
        }
        for (int i = rows.length - 1; i >= 0; i--) {
          TreePath path = source.getPathForRow(rows[i]);
          if (path == null) continue;
          model.removeNodeFromParent((MutableTreeNode) path.getLastPathComponent());
        }
      }
    }
  }
}