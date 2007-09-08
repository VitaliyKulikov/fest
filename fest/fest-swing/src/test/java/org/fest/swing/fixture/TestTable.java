/*
 * Created on Aug 25, 2007
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

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static org.fest.util.Strings.isEmpty;

/**
 * Understands a table that:
 * <ul>
 * <li>requires a name</li>
 * <li>supports drag and drop</li>
 * </ul>
 * Adapted from the tutorial 
 * <a href="http://java.sun.com/docs/books/tutorial/uiswing/dnd/intro.html" target="_blank">Introduction to Drag and Drop and Data Transfer</a>.
 *  
 * @author Alex Ruiz 
 * @author Yvonne Wang
 */
final class TestTable extends JTable {
  private static final long serialVersionUID = 1L;

  TestTable(String name, Object[][] rowData, Object[] columnNames) {
    if (isEmpty(name)) throw new IllegalArgumentException("'name' cannot be null");
    setDragEnabled(true);
    setModel(new DefaultTableModel(rowData, columnNames));
    setName(name);
    setSelectionMode(SINGLE_SELECTION);
    setTransferHandler(new TableTransferHandler());
  }

  private static class TableTransferHandler extends StringTransferHandler<JTable> {
    private static final long serialVersionUID = 1L;
    
    protected String exportString(JTable table) {
      rows = table.getSelectedRows();
      int colCount = table.getColumnCount();
      StringBuilder b = new StringBuilder();
      for (int i = 0; i < rows.length; i++) {
        for (int j = 0; j < colCount; j++) {
          Object val = table.getValueAt(rows[i], j);
          b.append(val == null ? "" : val.toString());
          if (j != colCount - 1) b.append(",");
        }
        if (i != rows.length - 1) b.append("\n");
      }
      return b.toString();
    }

    protected void importString(JTable target, String s) {
      DefaultTableModel model = (DefaultTableModel) target.getModel();
      int index = target.getSelectedRow();
      // Prevent the user from dropping data back on itself.
      if (rows != null && index >= rows[0] - 1 && index <= rows[rows.length - 1]) {
        rows = null;
        return;
      }
      int max = model.getRowCount();
      if (index < 0) index = max;
      else {
        index++;
        if (index > max) index = max;
      }
      addIndex = index;
      String[] values = s.split("\n");
      addCount = values.length;
      int colCount = target.getColumnCount();
      for (int i = 0; i < values.length && i < colCount; i++) 
        model.insertRow(index++, values[i].split(","));
    }

    protected void cleanup(JTable source, boolean remove) {
      if (remove && rows != null) {
        DefaultTableModel model = (DefaultTableModel) source.getModel();
        // If we are moving items around in the same table, we need to adjust the rows accordingly, since those after the 
        // insertion point have moved.
        if (addCount > 0) {
          for (int i = 0; i < rows.length; i++) 
            if (rows[i] > addIndex) rows[i] += addCount;
        }
        for (int i = rows.length - 1; i >= 0; i--) 
          model.removeRow(rows[i]);
      }
    }
  }
}