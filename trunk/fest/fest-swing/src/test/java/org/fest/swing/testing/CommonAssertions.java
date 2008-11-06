/*
 * Created on Nov 6, 2008
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
package org.fest.swing.testing;

import org.fest.swing.exception.ActionFailedException;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * Understands common assertions using in the test suite.
 *
 * @author Alex Ruiz 
 */
public final class CommonAssertions {

  public static void assertActionFailureDueToDisableComponent(ActionFailedException e) {
    assertThat(e).message().contains("Expecting component").contains("to be enabled");
  }
  
  public static void assertActionFailureDueToNotResizableContainer(ActionFailedException e) {
    assertThat(e).message().contains("Expecting container").contains("to be resizable by the user");
  }
  
  public static void failWhenExpectingException() {
    fail("Expecting exception");
  }
  
  private CommonAssertions() {}
}