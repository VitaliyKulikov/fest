/*
 * Created on Aug 28, 2008
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
package org.fest.swing.factory;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import static org.fest.util.Arrays.isEmpty;

/**
 * Understands creation of <code>{@link JPopupMenu}</code>s.
 *
 * @author Alex Ruiz
 */
public final class JPopupMenus {

  private JPopupMenus() {}

  public static JPopupMenuFactory popupMenu() {
    return new JPopupMenuFactory();
  }
  
  public static class JPopupMenuFactory {
    String label;
    JMenuItem[] menuItems;
    String name;
    
    public JPopupMenuFactory withLabel(String newLabel) {
      label = newLabel;
      return this;
    }

    public JPopupMenuFactory withMenuItems(JMenuItem... newMenuItems) {
      menuItems = newMenuItems;
      return this;
    }
    
    public JPopupMenuFactory withName(String newName) {
      name = newName;
      return this;
    }
    
    public JPopupMenu createNew() {
      JPopupMenu popupMenu = new JPopupMenu();
      popupMenu.setLabel(label);
      popupMenu.setName(name);
      if (!isEmpty(menuItems))
        for (JMenuItem menuItem : menuItems) popupMenu.add(menuItem);
      return popupMenu;
    }
  }
}