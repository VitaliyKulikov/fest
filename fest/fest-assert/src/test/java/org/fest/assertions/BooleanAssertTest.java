/*
 * Created on Mar 19, 2007
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
package org.fest.assertions;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link BooleanAssert}</code>.
 *
 * @author Alex Ruiz
 */
public class BooleanAssertTest {

  @Test public void shouldPassIfActualValueIsTrue() {
    new BooleanAssert(true).isTrue();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfActualValueIsFalse() {
    new BooleanAssert(true).isFalse();
  }

  @Test public void shouldPassIfActualValueIsFalse() {
    new BooleanAssert(false).isFalse();
  }

  @Test(expectedExceptions = AssertionError.class) 
  public void shouldFailIfActualValueIsTrue() {
    new BooleanAssert(false).isTrue();
  }
}
