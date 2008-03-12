/*
 * Created on May 21, 2007
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
import javax.swing.tree.TreePath;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JTreeDriver;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;


/**
 * Tests for <code>{@link JTreeFixture}</code>.
 * 
 * @author Keith Coughtrey
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTreeFixtureTest extends JPopupMenuInvokerFixtureTestCase<JTree> {

  private JTreeDriver driver;
  private JTree target;
  private JTreeFixture fixture;
  private TreePath path;
  
  void onSetUp() {
    driver = createMock(JTreeDriver.class);
    target = new JTree();
    fixture = new JTreeFixture(robot(), target);
    fixture.updateDriver(driver);
    path = new TreePath(new Object[] { "Hello" });
  }

  @Test public void shouldDragAtRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag(8));
      }
    }.run();
  }
  
  @Test public void shouldDragAtTreePath() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drag(target, path);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.drag(path));
      }
    }.run();
  }

  @Test public void shouldDropAtRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop(8));
      }
    }.run();
  }
  
  @Test public void shouldDropAtTreePath() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.drop(target, path);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.drop(path));
      }
    }.run();
  }

  @Test public void shouldSelectRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectRow(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectRow(8));
      }
    }.run();
  }
  
  @Test public void shouldSelectTreePath() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectPath(target, path);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectPath(path));
      }
    }.run();
  }

  @Test public void shouldToggleRow() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.toggleRow(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.toggleRow(8));
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JTree target() { return target; }
  JPopupMenuInvokerFixture<JTree> fixture() { return fixture; }
}