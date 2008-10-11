package org.fest.swing.driver;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether the node (in a
 * <code>{@link JTree}</code>) identified by the given path is expanded or not.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JTreeExpandedPathQuery {

  static boolean isExpanded(final JTree tree, final TreePath path) {
    return execute(new GuiQuery<Boolean>() {
      protected Boolean executeInEDT() {
        return tree.isExpanded(path);
      }
    });
  }

  private JTreeExpandedPathQuery() {}
}