package org.aleks.alg.doupeuck.model;

public record Point(double x, double y) {
  public double distance(Point other) {
    double dx = x - other.x;
    double dy = y - other.y;
    return Math.sqrt(dx * dx + dy * dy);
  }
}
