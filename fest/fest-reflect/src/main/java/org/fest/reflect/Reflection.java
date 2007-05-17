/*
 * Created on Oct 31, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.reflect;

import org.fest.reflect.Constructor.TargetType;
import org.fest.reflect.Field.FieldName;
import org.fest.reflect.Method.MethodName;

/**
 * Understands Java reflection.
 * 
 * @author Alex Ruiz
 */
public final class Reflection {

  public static FieldName field(String name) {
    return new FieldName(name);
  }

  public static MethodName method(String name) {
    return new MethodName(name);
  }

  public static TargetType constructor() {
    return new TargetType();
  }

  private Reflection() {}

}
