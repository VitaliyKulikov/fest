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

import static org.fest.test.ExpectedFailure.expectAssertionError;
import static org.fest.util.Strings.isEmpty;
import static org.testng.Assert.*;

import org.fest.test.CodeToTest;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link StringAssert}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 * @author Alex Ruiz
 */
public class StringAssertTest {

  private static final String EMPTY_STRING = "";

  @Test public void shouldSetDescription() {
    StringAssert assertion = new StringAssert("Anakin");
    assertNull(assertion.description());
    assertion.as("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  @Test public void shouldSetDescriptionSafelyForGroovy() {
    StringAssert assertion = new StringAssert("Anakin");
    assertNull(assertion.description());
    assertion.describedAs("A Test");
    assertEquals(assertion.description(), "A Test");
  }

  private static class UpperCaseCondition extends Condition<String> {
    @Override public boolean matches(String value) {
      if(isEmpty(value)) return false;
      return value.equals(value.toUpperCase());
    }
  }

  @Test public void shouldPassIfConditionSatisfied() {
    new StringAssert("HELLO").satisfies(new UpperCaseCondition());
  }

  @Test public void shouldFailIfConditionNotSatisfied() {
    expectAssertionError("condition failed with:<'hello'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").satisfies(new UpperCaseCondition());
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfied() {
    expectAssertionError("[Test] condition failed with:<'hello'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").as("Test").satisfies(new UpperCaseCondition());
      }
    });
  }

  @Test public void shouldFailIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("expected:<Uppercase> but was:<'hello'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").satisfies(new UpperCaseCondition().as("Uppercase"));
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfConditionNotSatisfiedShowingDescriptionOfCondition() {
    expectAssertionError("[Test] expected:<Uppercase> but was:<'hello'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("hello").as("Test").satisfies(new UpperCaseCondition().as("Uppercase"));
      }
    });
  }

  @Test public void shouldPassIfStringIsEmpty() {
    new StringAssert(EMPTY_STRING).isEmpty();
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldFailIfStringIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("expecting empty String but was:<'Luke'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").isEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfStringIsNotEmptyAndExpectingEmpty() {
    expectAssertionError("[A Test] expecting empty String but was:<'Luke'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").as("A Test").isEmpty();
      }
    });
  }

  @Test public void shouldPassIfActualIsNull() {
    new StringAssert(null).isNull();
  }

  @Test public void shouldFailIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("<''> should be null").on(new CodeToTest() {
      public void run() {
        new StringAssert(EMPTY_STRING).isNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotNullAndExpectingNull() {
    expectAssertionError("[A Test] <''> should be null").on(new CodeToTest() {
      public void run() {
        new StringAssert(EMPTY_STRING).as("A Test").isNull();
      }
    });
  }

  @Test public void shouldPassIfActualIsNotNull() {
    new StringAssert(EMPTY_STRING).isNotNull();
  }

  @Test public void shouldFailIfActualIsNullAndExpectingNotNull() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() {
        new StringAssert(null).isNotNull();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndExpectingNotNull() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() {
        new StringAssert(null).as("A Test").isNotNull();
      }
    });
  }

  @Test public void shouldPassIfStringIsNotEmpty() {
    new StringAssert("Vader").isNotEmpty();
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfNotEmpty() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfNotEmpty() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).as("A Test").isNotEmpty();
      }
    });
  }

  @Test public void shouldFailIfStringIsEmptyAndExpectingNotEmpty() {
    expectAssertionError("expecting a non-empty String, but it was empty").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(EMPTY_STRING).isNotEmpty();
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsEmptyAndExpectingNotEmpty() {
    expectAssertionError("[A Test] expecting a non-empty String, but it was empty").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(EMPTY_STRING).as("A Test").isNotEmpty();
      }
    });
  }


  @Test public void shouldPassIfStringsAreEqual() {
    new StringAssert("Anakin").isEqualTo("Anakin");
  }

  @Test public void shouldFailIfStringsAreNotEqualAndExpectingEqual() {
    expectAssertionError("expected:<'Yoda'> but was:<'Luke'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").isEqualTo("Yoda");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfStringsAreNotEqualAndExpectingEqual() {
    expectAssertionError("[A Test] expected:<'Yoda'> but was:<'Luke'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").as("A Test").isEqualTo("Yoda");
      }
    });
  }

  @Test public void shouldPassIfStringsAreNotEqual() {
    new StringAssert("Anakin").isNotEqualTo("Vader");
  }

  @Test public void shouldFailIfStringsAreEqualAndExpectingNotEqual() {
    expectAssertionError("actual value:<'Luke'> should not be equal to:<'Luke'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").isNotEqualTo("Luke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfStringsAreEqualAndExpectingNotEqual() {
    expectAssertionError("[A Test] actual value:<'Luke'> should not be equal to:<'Luke'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").as("A Test").isNotEqualTo("Luke");
      }
    });
  }

  @Test public void shouldPassIfActualContainsGivenString() {
    new StringAssert("Anakin").contains("akin");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfContainsString() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).contains("Yoda");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfContainsString() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).as("A Test").contains("Yoda");
      }
    });
  }

  @Test public void shouldFailIfActualDoesNotContainGivenStringAndExpectingToContain() {
    expectAssertionError("<'Luke'> should contain the String:<'Yoda'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").contains("Yoda");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotContainGivenStringAndExpectingToContain() {
    expectAssertionError("[A Test] <'Luke'> should contain the String:<'Yoda'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").as("A Test").contains("Yoda");
      }
    });
  }

  @Test public void shouldPassIfActualStartsWithGivenString() {
    new StringAssert("Luke").startsWith("Luk");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfStartsWith() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).startsWith("Leia");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfStartsWith() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).as("A Test").startsWith("Leia");
      }
    });
  }

  @Test public void shouldFailIfActualDoesNotStartWithGivenStringAndExpectingToStartWith() {
    expectAssertionError("<'Luke'> should start with:<'uke'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").startsWith("uke");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotStartWithGivenStringAndExpectingToStartWith() {
    expectAssertionError("[A Test] <'Luke'> should start with:<'uke'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").as("A Test").startsWith("uke");
      }
    });
  }

  @Test public void shouldPassIfActualEndsWithGivenString() {
    new StringAssert("Luke").endsWith("uke");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfEndsWith() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).endsWith("Leia");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfEndsWith() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).as("A Test").endsWith("Leia");
      }
    });
  }

  @Test public void shouldFailIfActualDoesNotEndWithGivenString() {
    expectAssertionError("<'Luke'> should end with:<'Luk'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").endsWith("Luk");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotEndWithGivenString() {
    expectAssertionError("[A Test] <'Luke'> should end with:<'Luk'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke").as("A Test").endsWith("Luk");
      }
    });
  }

  @Test public void shouldPassIfActualDoesNotContainGivenString() {
    new StringAssert("Luke").excludes("Yoda");
  }

  @Test public void shouldFailIfActualIsNullAndCheckingIfExcludes() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).excludes("Leia");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullAndCheckingIfExcludes() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).as("A Test").excludes("Leia");
      }
    });
  }

  @Test public void shouldFailIfActualContainsGivenStringAndExpectingExclude() {
    expectAssertionError("<'Anakin'> should not contain the String:<'akin'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Anakin").excludes("akin");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualContainsGivenStringAndExpectingExclude() {
    expectAssertionError("[A Test] <'Anakin'> should not contain the String:<'akin'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Anakin").as("A Test").excludes("akin");
      }
    });
  }

  @Test public void shouldPassIfActualMatchesGivenRegExp() {
    new StringAssert("Luke 001").matches("^.*\\d+$");
  }

  @Test public void shouldFailIfActualIsNullWhenCheckingIfMatching() {
    shouldFailIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).matches(EMPTY_STRING);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNullWhenCheckingIfMatching() {
    shouldFailShowingDescriptionIfActualIsNull(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert(null).as("A Test").matches(EMPTY_STRING);
      }
    });
  }

  @Test public void shouldFailIfActualDoesNotMatchGivenRegExp() {
    expectAssertionError("<'Luke 001'> should match the regular expression:<'^\\d+.*$'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke 001").matches("^\\d+.*$");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotMatchGivenRegExp() {
    expectAssertionError("[A Test] <'Luke 001'> should match the regular expression:<'^\\d+.*$'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke 001").as("A Test").matches("^\\d+.*$");
      }
    });
  }

  @Test public void shouldPassIfActualDoesNotMatchGivenRegExp() {
    new StringAssert("Luke 001").doesNotMatch("^\\d+.*$");
  }

  @Test public void shouldFailIfActualMatchesGivenRegExp() {
    expectAssertionError("<'Luke 001'> should not match the regular expression:<'^.*\\d+$'>").on(new CodeToTest() {
      public void run() throws Throwable {
        new StringAssert("Luke 001").doesNotMatch("^.*\\d+$");
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualMatchesGivenRegExp() {
    expectAssertionError("[A Test] <'Luke 001'> should not match the regular expression:<'^.*\\d+$'>").on(
        new CodeToTest() {
          public void run() throws Throwable {
            new StringAssert("Luke 001").as("A Test").doesNotMatch("^.*\\d+$");
          }
        });
  }

  @Test public void shouldPassIfActualHasExpectedSize() {
    new StringAssert("Luke").hasSize(4);
  }

  @Test public void shouldFailIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("expected size:<2> but was:<5> for String:<'Vader'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("Vader").hasSize(2);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualDoesNotHaveExpectedSize() {
    expectAssertionError("[A Test] expected size:<2> but was:<5> for String:<'Vader'>").on(new CodeToTest() {
      public void run() {
        new StringAssert("Vader").as("A Test").hasSize(2);
      }
    });
  }

  @Test public void shouldPassIfStringsAreSame() {
    String jedi = "Yoda";
    new StringAssert(jedi).isSameAs(jedi);
  }

  @Test public void shouldFailIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("expected same instance but found:<'Leia'> and:<''>").on(new CodeToTest() {
      public void run() {
        new StringAssert("Leia").isSameAs(EMPTY_STRING);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsNotSameAsExpectedAndExpectingSame() {
    expectAssertionError("[A Test] expected same instance but found:<'Leia'> and:<''>").on(new CodeToTest() {
      public void run() {
        new StringAssert("Leia").as("A Test").isSameAs(EMPTY_STRING);
      }
    });
  }

  @Test public void shouldFailIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("given objects are same:<'Yoda'>").on(new CodeToTest() {
      public void run() {
        String jedi = "Yoda";
        new StringAssert(jedi).isNotSameAs(jedi);
      }
    });
  }

  @Test public void shouldFailShowingDescriptionIfActualIsSameAsExpectedAndExpectingNotSame() {
    expectAssertionError("[A Test] given objects are same:<'Yoda'>").on(new CodeToTest() {
      public void run() {
        String jedi = "Yoda";
        new StringAssert(jedi).as("A Test").isNotSameAs(jedi);
      }
    });
  }

  @Test public void shouldPassIfStringsAreNotSame() {
    new StringAssert("Leia").isNotSameAs(EMPTY_STRING);
  }

  private void shouldFailIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("expecting a non-null object, but it was null").on(codeToTest);
  }

  private void shouldFailShowingDescriptionIfActualIsNull(CodeToTest codeToTest) {
    expectAssertionError("[A Test] expecting a non-null object, but it was null").on(codeToTest);
  }
}
