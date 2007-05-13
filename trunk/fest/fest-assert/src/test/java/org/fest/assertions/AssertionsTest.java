/*
 * Created on Jan 10, 2007
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

import java.util.ArrayList;

import org.fest.assertions.Assertions;
import org.fest.assertions.CollectionAssert;
import org.fest.assertions.ObjectArrayAssert;
import org.fest.assertions.ObjectAssert;
import org.fest.assertions.StringAssert;



import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Assertions}</code>.
 *
 * @author Yvonne Wang
 */
public class AssertionsTest {

  @Test public void shouldReturnObjectAssertIfArgumentIsObject() {
    assertEquals(Assertions.assertThat(new Object()).getClass(), ObjectAssert.class);
  }
  
  @Test public void shouldReturnStringAssertIfArgumentIsString() {
    assertEquals(Assertions.assertThat("").getClass(), StringAssert.class);
  }

  @Test public void shouldReturnObjectArrayAssertIfArgumentIsObjectArray() {
    assertEquals(Assertions.assertThat(new String[] { "One" }).getClass(), ObjectArrayAssert.class);
  }

  @Test public void shouldReturnCollectionAssertIfArgumentIsCollection() {
    assertEquals(Assertions.assertThat(new ArrayList<Object>()).getClass(), CollectionAssert.class);
  }
}
