/*
 * Created on Jun 18, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.PrimitiveFail.failIfEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotEqual;
import static org.fest.assertions.PrimitiveFail.failIfNotGreaterThan;
import static org.fest.assertions.PrimitiveFail.failIfNotLessThan;

/**
 * Understands assertion methods for <code>byte</code>s.
 * 
 * @author Yvonne Wang
 */
public final class ByteAssert {

  private final byte actual;

  private static final byte ZERO = (byte)0;

  ByteAssert(byte actual) {
    this.actual = actual;
  }

  public ByteAssert isEqualTo(byte expected) {
    failIfNotEqual(actual, expected);
    return this;
  }

  public ByteAssert isNotEqualTo(byte other) {
    failIfEqual(actual, other);
    return this;
  }

  public ByteAssert isGreaterThan(byte smaller) {
    failIfNotGreaterThan(actual, smaller);
    return this;
  }

  public ByteAssert isLessThan(byte bigger) {
    failIfNotLessThan(actual, bigger);
    return this;
  }

  public ByteAssert isPositive() { return isGreaterThan(ZERO); }

  public ByteAssert isNegative() { return isLessThan(ZERO); }

  public ByteAssert isZero() { return isEqualTo(ZERO); }
}
