/*
 * Created on Aug 31, 2008
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
package org.fest.swing.task;

import java.awt.Component;
import java.awt.event.ComponentListener;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link ComponentAddComponentListenerTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class ComponentAddComponentListenerTaskTest {

  private Component component;
  private ComponentListener listener;
  private ComponentAddComponentListenerTask task;
  
  @BeforeMethod public void setUp() {
    component = createMock(Component.class);
    listener = createMock(ComponentListener.class);
    task = new ComponentAddComponentListenerTask(component, listener);
  }
  
  public void shouldAddComponentListener() {
    new EasyMockTemplate(component) {
      protected void expectations() {
        component.addComponentListener(listener);
        expectLastCall().once();
      }

      protected void codeToTest() {
        task.executeInEDT();
      }
    }.run();
  }
}
