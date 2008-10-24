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

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.JavaApp.ArgumentObserver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.query.ComponentShowingQuery.isShowing;
import static org.fest.swing.query.FrameTitleQuery.titleOf;
import static org.fest.util.Collections.list;

/**
 * Tests for <code>{@link ApplicationLauncher}</code>.
 *
 * @author Yvonne Wang
 */
@Test
public class ApplicationLauncherTest {

  private Robot robot;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldThrowErrorIfApplicationClassNameIsInvalid() {
    try {
      ApplicationLauncher.application("Hello").start();
      fail();
    } catch (UnexpectedException e) {
      assertThat(e).message().contains("Unable to load class 'Hello'");
    }
  }

  public void shouldLaunchApplicationWithoutArguments() {
    ApplicationLauncher.application(JavaApp.class).start();
    assertFrameIsShowing();
  }

  public void shouldLaunchApplicationWithoutArgumentsUsingFQCN() {
    ApplicationLauncher.application(JavaApp.class.getName()).start();
    assertFrameIsShowing();
  }

  public void shouldLaunchApplicationUsingArguments() {
    final List<String> arguments = new ArrayList<String>();
    ArgumentObserver observer = new ArgumentObserver() {
      public void arguments(String[] args) {
        arguments.addAll(list(args));
      }
    };
    JavaApp.add(observer);
    ApplicationLauncher.application(JavaApp.class).withArgs("arg1", "arg2").start();
    assertFrameIsShowing();
    assertThat(arguments).containsOnly("arg1", "arg2");
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfArgumentArrayIsNull() {
    String[] args = null;
    ApplicationLauncher.application(JavaApp.class).withArgs(args).start();
  }

  private void assertFrameIsShowing() {
    FrameFixture frameFixture = WindowFinder.findFrame(new GenericTypeMatcher<Frame>() {
      protected boolean isMatching(Frame frame) {
        return "Java Application".equals(titleOf(frame)) && isShowing(frame);
      }
    }).using(robot);
    assertThat(frameFixture).isNotNull();
  }
}
