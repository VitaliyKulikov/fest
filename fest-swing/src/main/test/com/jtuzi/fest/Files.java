/*
 * Created on Sep 28, 2006
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
 * Copyright @2006 the original author or authors.
 */
package com.jtuzi.fest;

import java.io.File;

import static java.io.File.separator;

/**
 * Understands file-related utility methods to be used for testing.
 *
 * @author Alex Ruiz
 */
public final class Files {

  public static String absolutePath(String dirName, String fileName) {
    return new File(dirName + separator + fileName).getAbsolutePath();
  }
  
  private Files() {}

}
