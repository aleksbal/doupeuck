package org.aleks.alg.doupeuck;

import static org.aleks.alg.doupeuck.service.DpAlgorithm.douglasPeucker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import org.aleks.alg.doupeuck.model.Point;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class DoupeuckAlgorithmTests {

  @BeforeAll
  static void initAll() {
  }

  @AfterAll
  static void tearDownAll() {
  }

  @BeforeEach
  void init() {
  }

  @Test
  @Disabled("for demonstration purposes")
  void failingTest() {
    fail("a failing test");
  }

  @Test
  @Disabled("for demonstration purposes")
  void skippedTest() {
    // not executed
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  public void test() {

    List<Point> points = List.of(
        new Point(0, 0),
        new Point(1, 1),
        new Point(2, 2),
        new Point(3, 3),
        new Point(4, 4),
        new Point(5, 0) // This point makes the line not straight
    );

    double epsilon = 1.0;
    var simplified = douglasPeucker(points, epsilon);
    assertEquals(3, simplified.size(),
        "Reduction didn't produce expected number of points.");
  }
}
