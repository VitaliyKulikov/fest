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

import javax.swing.JTable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.JTableCell.cell;

/**
 * Tests for <code>{@link IsJTableCellEditableTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class IsJTableCellEditableTaskTest {

  @Test(dataProvider = "booleans")
  public void shouldIndicateWhetherCellIsEditableOrNot(final boolean editable) {
    final JTable table = createMock(JTable.class);
    final JTableCell cell = cell(6, 8);
    new EasyMockTemplate(table) {
      protected void expectations() {
        expect(table.isCellEditable(6, 8)).andReturn(editable);
      }

      protected void codeToTest() {
        assertThat(IsJTableCellEditableTask.isCellEditable(table, cell)).isEqualTo(editable);
      }
    }.run();
  }
  
  @DataProvider(name = "booleans") public Object[][] booleans() {
    return new Object[][] { { true }, { false } };
  }
}
