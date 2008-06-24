/*
 * Created on May 23, 2008
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
package org.fest.swing.launcher;

import org.fest.reflect.exception.ReflectionError;

import static java.util.Arrays.copyOf;

import static org.fest.reflect.core.Reflection.staticMethod;
import static org.fest.util.Strings.*;

/**
 * Understands execution of a Java application from a class that has a "main" method.
 * <p>
 * The following example shows how to start an application without any arguments:
 *
 * <pre>
 * ApplicationLauncher.application(JavaApp.class).start();
 *
 * // or
 *
 * ApplicationLauncher.application(&quot;org.fest.swing.application.JavaApp&quot;).start();
 * </pre>
 *
 * </p>
 * <p>
 * The following example shows how to start an application with arguments:
 *
 * <pre>
 * ApplicationLauncher.application(JavaApp.class).withArgs(&quot;arg1&quot;, &quot;arg2&quot;).start();
 *
 * // or
 *
 * ApplicationLauncher.application(&quot;org.fest.swing.application.JavaApp&quot;).withArgs(&quot;arg1&quot;, &quot;arg2&quot;).start();
 * </pre>
 *
 * </p>
 *
 * @author Yvonne Wang
 */
public class ApplicationLauncher {

  /**
   * Starting point of the fluent interface.
   * @param applicationTypeName the fully qualified name of the class containing the "main" method.
   * @return the created <code>ApplicationStarter</code>.
   * @throws ReflectionError if the class specified in the given name cannot be loaded.
   */
  public static ApplicationLauncher application(String applicationTypeName) {
    try {
      Class<?> applicationType = ApplicationLauncher.class.getClassLoader().loadClass(applicationTypeName);
      return application(applicationType);
    } catch (ClassNotFoundException e) {
      throw new ReflectionError(concat("Unable to load class ", quote(applicationTypeName)), e);
    }
  }

  /**
   * Starting point of the fluent interface.
   * @param applicationType the class containing the "main" method.
   * @return the created <code>ApplicationStarter</code>.
   */
  public static ApplicationLauncher application(Class<?> applicationType) {
    return new ApplicationLauncher(applicationType);
  }

  private final Class<?> applicationType;
  private String[] args = new String[0];

  private ApplicationLauncher(Class<?> applicationType) {
    this.applicationType = applicationType;
  }

  /**
   * Specifies the arguments to pass to the "main" method.
   * @param newArgs the arguments to pass to the "main" method.
   * @return this <code>ApplicationStarter</code>.
   * @throws IllegalArgumentException if <code>newArgs</code> is <code>null</code>.
   */
  public ApplicationLauncher withArgs(String...newArgs) {
    if (newArgs == null) throw new IllegalArgumentException("The array of arguments should not be null");
    args = copyOf(newArgs, newArgs.length);
    return this;
  }

  /**
   * Starts the application.
   * @throws ReflectionError if the "main" method cannot be invoked.
   */
  public void start() {
    staticMethod("main").withParameterTypes(String[].class).in(applicationType).invoke(new Object[] { args });
  }
}
