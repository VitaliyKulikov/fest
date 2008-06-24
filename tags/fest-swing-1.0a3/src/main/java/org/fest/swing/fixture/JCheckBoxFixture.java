/*
 * Created on Jun 10, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JCheckBox;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Timeout;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.exception.ComponentLookupException;

/**
 * Understands simulation of user events on a <code>{@link JCheckBox}</code> and verification of the state of such 
 * <code>{@link JCheckBox}</code>.
 * 
 * @author Alex Ruiz
 */
public class JCheckBoxFixture extends TwoStateButtonFixture<JCheckBox> {

  private AbstractButtonDriver driver;
  
  /**
   * Creates a new <code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JCheckBox</code>.
   * @param target the <code>JCheckBox</code> to be managed by this fixture.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   * @throws IllegalArgumentException if <code>target</code> is <code>null</code>.
   */
  public JCheckBoxFixture(Robot robot, JCheckBox target) {
    super(robot, target);
    createDriver();
  }

  /**
   * Creates a new <code>{@link JCheckBoxFixture}</code>.
   * @param robot performs simulation of user events on a <code>JCheckBox</code>.
   * @param checkBoxName the name of the <code>JCheckBox</code> to find using the given <code>Robot</code>.
   * @throws IllegalArgumentException if <code>robot</code> is <code>null</code>.
   * @throws ComponentLookupException if a matching <code>JCheckBox</code> could not be found.
   * @throws ComponentLookupException if more than one matching <code>JCheckBox</code> is found.
   */
  public JCheckBoxFixture(Robot robot, String checkBoxName) {
    super(robot, checkBoxName, JCheckBox.class);
    createDriver();
  }

  private void createDriver() {
    updateDriver(new AbstractButtonDriver(robot));
  }
  
  final void updateDriver(AbstractButtonDriver newDriver) {
    driver = newDriver;
  }
  
  /** {@inheritDoc} **/
  protected ComponentDriver driver() {
    return driver;
  }

  /**
   * Checks (or selects) this fixture's <code>{@link JCheckBox}</code> only it is not already checked.
   * @return this fixture.
   */
  public JCheckBoxFixture check() {
    driver.select(target);
    return this;
  }

  /**
   * Unchecks this fixture's <code>{@link JCheckBox}</code> only if it is checked.
   * @return this fixture.
   */
  public JCheckBoxFixture uncheck() {
    driver.unselect(target);
    return this;
  }
  
  /**
   * Simulates a user clicking this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   */
  public JCheckBoxFixture click() {
    driver.click(target);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JCheckBox}</code>.
   * @param button the button to click.
   * @return this fixture.
   */
  public JCheckBoxFixture click(MouseButton button) {
    driver.click(target, button);
    return this;
  }

  /**
   * Simulates a user clicking this fixture's <code>{@link JCheckBox}</code>.
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @throws IllegalArgumentException if the given <code>MouseClickInfo</code> is <code>null</code>.
   * @return this fixture.
   */
  public JCheckBoxFixture click(MouseClickInfo mouseClickInfo) {
    doClick(mouseClickInfo);
    return this;
  }

  /**
   * Simulates a user double-clicking this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   */
  public JCheckBoxFixture doubleClick() {
    driver.doubleClick(target);
    return this;
  }

  /**
   * Simulates a user right-clicking this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   */
  public JCheckBoxFixture rightClick() {
    driver.rightClick(target);
    return this;
  }

  /**
   * Gives input focus to this fixture's <code>{@link JCheckBox}</code>.
   * @return this fixture.
   */
  public JCheckBoxFixture focus() {
    driver.focus(target);
    return this;
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's <code>{@link JCheckBox}</code>.
   * Modifiers is a mask from the available <code>{@link java.awt.event.InputEvent}</code> masks.
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws IllegalArgumentException if the given <code>KeyPressInfo</code> is <code>null</code>.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @see KeyPressInfo
   */
  public JCheckBoxFixture pressAndReleaseKey(KeyPressInfo keyPressInfo) {
    doPressAndReleaseKey(keyPressInfo);
    return this;
  }

  /**
   * Simulates a user pressing and releasing the given keys on the <code>{@link JCheckBox}</code> managed by this
   * fixture.
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JCheckBoxFixture pressAndReleaseKeys(int... keyCodes) {
    driver.pressAndReleaseKeys(target, keyCodes);
    return this;
  }

  /**
   * Simulates a user pressing the given key on this fixture's <code>{@link JCheckBox}</code>.
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JCheckBoxFixture pressKey(int keyCode) {
    driver.pressKey(target, keyCode);
    return this;
  }
  
  /**
   * Simulates a user releasing the given key on this fixture's <code>{@link JCheckBox}</code>.
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @see java.awt.event.KeyEvent
   */
  public JCheckBoxFixture releaseKey(int keyCode) {
    driver.releaseKey(target, keyCode);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is disabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is enabled.
   */
  public JCheckBoxFixture requireDisabled() {
    driver.requireDisabled(target);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is enabled.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is disabled.
   */
  public JCheckBoxFixture requireEnabled() {
    driver.requireEnabled(target);
    return this;
  }
  
  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is enabled.
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws org.fest.swing.exception.WaitTimedOutError if this fixture's <code>JCheckBox</code> is never enabled.
   */
  public JCheckBoxFixture requireEnabled(Timeout timeout) {
    driver.requireEnabled(target, timeout);
    return this;
  }

  /**
   * Verifies that this fixture's <code>{@link JCheckBox}</code> is not selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JCheckBox</code> managed by this fixture is selected.
   */
  public JCheckBoxFixture requireNotSelected() {
    driver.requireNotSelected(target);
    return this;
  }

  /**
   * Verifies that this fixture's <code>{@link JCheckBox}</code> is selected.
   * @return this fixture.
   * @throws AssertionError if the <code>JCheckBox</code> managed by this fixture is not selected.
   */
  public JCheckBoxFixture requireSelected() {
    driver.requireSelected(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is not visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is visible.
   */
  public JCheckBoxFixture requireNotVisible() {
    driver.requireNotVisible(target);
    return this;
  }

  /**
   * Asserts that this fixture's <code>{@link JCheckBox}</code> is visible.
   * @return this fixture.
   * @throws AssertionError if this fixture's <code>JCheckBox</code> is not visible.
   */
  public JCheckBoxFixture requireVisible() {
    driver.requireVisible(target);
    return this;
  }

  /**
   * Asserts that the text of this fixture's <code>{@link JCheckBox}</code> is equal to the specified 
   * <code>String</code>. 
   * @param expected the text to match.
   * @return this fixture.
   * @throws AssertionError if the text of the target JCheckBox is not equal to the given one.
   */
  public JCheckBoxFixture requireText(String expected) {
    driver.requireText(target, expected);
    return this;
  }
  
  /**
   * Returns the text of this fixture's <code>{@link JCheckBox}</code>. 
   * @return the text of this fixture's <code>JCheckBox</code>. 
   */
  public String text() {
    return target.getText();
  }
}
