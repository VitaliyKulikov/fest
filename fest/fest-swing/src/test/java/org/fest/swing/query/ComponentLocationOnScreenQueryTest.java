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
package org.fest.swing.query;

import java.awt.Component;
import java.awt.Point;

import org.easymock.classextension.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link ComponentLocationOnScreenQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class ComponentLocationOnScreenQueryTest {

  private Component component;
  private Point location;
  private ComponentLocationOnScreenQuery query;
  
  @BeforeMethod public void setUp() {
    component = EasyMock.createMock(Component.class);
    location = new Point(6, 8);
    query = new ComponentLocationOnScreenQuery(component);
  }

  public void shouldReturnComponentLocationOnScreen() {
    new EasyMockTemplate(component) {
      protected void expectations() {
        expect(component.getLocationOnScreen()).andReturn(location);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isSameAs(location);
      }
    }.run();
  }
}