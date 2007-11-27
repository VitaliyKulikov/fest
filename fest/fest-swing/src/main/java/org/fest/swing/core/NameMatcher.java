/*
 * Created on Jun 18, 2007
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
package org.fest.swing.core;

import java.awt.*;

/**
 * Understands <code>{@link java.awt.Component}</code> matching by name.
 *
 * @author Alex Ruiz
 */
public final class NameMatcher extends abbot.finder.matchers.NameMatcher implements ComponentMatcher {

  /**
   * Creates a new <code>{@link NameMatcher}</code>.
   * @param name the name of the component we are looking for.
   */
  public NameMatcher(String name) {
    super(name);
  }

  /** 
   * Verifies that the name of the given <code>{@link Component}</code> matches the one specified in this matcher.
   * @param c the <code>Component</code> to verify.
   * @return <code>true</code> if the name of the given <code>Component</code> matches the one specified in this 
   * matcher, otherwise <code>false</code>. 
   */
  @Override public boolean matches(Component c) {
    return super.matches(c);
  }
}