/*
 * Created on Jul 28, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.query;

import javax.swing.JTable;

import org.fest.swing.core.GuiQuery;

/**
 * Understands a task that returns the number of rows in a <code>{@link JTable}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class GetJTableRowCountTask extends GuiQuery<Integer> {
  private final JTable table;

  /**
   * Returns the number of rows in the given <code>{@link JTable}</code>. This action is executed in the event dispatch
   * thread.
   * @param table the given <code>JTable</code>.
   * @return the number of rows in the given <code>JTable</code>.
   */
  public static int rowCountOf(JTable table) {
    return new GetJTableRowCountTask(table).run();
  }

  private GetJTableRowCountTask(JTable table) {
    this.table = table;
  }

  /**
   * Returns the number of rows in this task's <code>{@link JTable}</code>. This action is executed in the event
   * dispatch thread.
   * @return the number of rows in this task's <code>JTable</code>.
   */
  protected Integer executeInEDT() {
    return table.getRowCount();
  }
}