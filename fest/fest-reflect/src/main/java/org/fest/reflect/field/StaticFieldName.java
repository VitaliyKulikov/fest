/*
 * Created on Feb 5, 2008
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
 * Copyright @2006-2008 the original author or authors.
 */
package org.fest.reflect.field;


/**
 * Understands the name of a static field to access using Java Reflection.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Retrieves the value of the static field "count"
 *   int count = {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link Invoker#get() get}();
 *   
 *   // Sets the value of the static field "count" to 3
 *   {@link org.fest.reflect.core.Reflection#staticField(String) staticField}("count").{@link StaticFieldName#ofType(Class) ofType}(int.class).{@link StaticFieldType#in(Class) in}(Person.class).{@link Invoker#set(Object) set}(3);
 * </pre>
 * </p>
 *
 * @author Alex Ruiz
 */
public final class StaticFieldName extends NameTemplate {

  /**
   * Creates a new <code>{@link StaticFieldName}</code>: the starting point of the fluent interface for accessing 
   * static fields using Java Reflection.
   * @param name the name of the field to access using Java Reflection.
   * @throws IllegalArgumentException if the given name is <code>null</code> or empty.
   */
  public StaticFieldName(String name) {
    super(name);
  }

  /**
   * Sets the type of the field to access.
   * @param <T> the generic type of the field type.
   * @param type the type of the field to access.
   * @return a recipient for the field type.
   * @throws IllegalArgumentException if the given type is <code>null</code>.
   */
  public <T> StaticFieldType<T> ofType(Class<T> type) {
    return new StaticFieldType<T>(type, this);
  }
}