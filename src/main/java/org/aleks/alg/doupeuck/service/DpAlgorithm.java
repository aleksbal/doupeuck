package org.aleks.alg.doupeuck.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import org.aleks.alg.doupeuck.model.Point;

public class DpAlgorithm {

  public static List<Point> douglasPeucker(List<Point> points, double epsilon) {
    if (points.size() <= 2) {
      return points;
    }

    // Step 2: Find the point with the maximum distance
    int farthestPointIndex =
        IntStream.range(1, points.size() - 1)
            .boxed()
            .max(
                Comparator.comparingDouble(
                    i -> perpendicularDistance(points.get(i), points.getFirst(), points.getLast())))
            .orElse(-1);

    double maxDistance =
        perpendicularDistance(points.get(farthestPointIndex), points.getFirst(), points.getLast());

    // Step 3: Simplify recursively
    if (maxDistance > epsilon) {
      var leftSubList = douglasPeucker(points.subList(0, farthestPointIndex + 1), epsilon);
      var rightSubList = douglasPeucker(points.subList(farthestPointIndex, points.size()), epsilon);

      // Step 4: Combine results
      var result = new ArrayList<>(leftSubList);
      result.addAll(rightSubList.subList(1, rightSubList.size())); // Avoid duplicate point
      return result;
    } else {
      // If no point is far enough, return end points
      return List.of(points.getFirst(), points.getLast());
    }
  }

  private static double perpendicularDistance(Point point, Point lineStart, Point lineEnd) {
    double dx = lineEnd.x() - lineStart.x();
    double dy = lineEnd.y() - lineStart.y();

    // Normal case
    if (dx != 0.0 || dy != 0.0) {
      double t =
          ((point.x() - lineStart.x()) * dx + (point.y() - lineStart.y()) * dy)
              / (dx * dx + dy * dy);

      // Projection falls on the segment
      if (t > 1) {
        return point.distance(lineEnd);
      } else if (t > 0) {
        return point.distance(new Point(lineStart.x() + t * dx, lineStart.y() + t * dy));
      } else {
        return point.distance(lineStart);
      }
    }

    // Line start and end are the same
    return point.distance(lineStart);
  }
}
