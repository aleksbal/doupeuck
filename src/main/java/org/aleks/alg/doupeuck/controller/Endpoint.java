package org.aleks.alg.doupeuck.controller;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.aleks.alg.doupeuck.model.Point;
import org.aleks.alg.doupeuck.service.CurvePointsReducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.List;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class Endpoint {
  @Autowired
  CurvePointsReducer service;
  @Bean
  public RouterFunction<ServerResponse> getPointsRoute() {
    return route(GET("/points"), this::getPointsHandler);
  }

  public Mono<ServerResponse> getPointsHandler(ServerRequest request) {
    Double lambda = request.queryParam("lambda")
        .map(Double::parseDouble).orElse(1d);
    List<Point> points = request.queryParam("points")
        .map(this::parsePoints)
        .map(pts -> service.douglasPeucker(pts,lambda))
        .orElse(List.of());

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(Flux.fromIterable(points), Point.class);
  }

  private List<Point> parsePoints(String pointsStr) {
    return Arrays.stream(pointsStr.split(";"))
        .map(this::parsePoint)
        .collect(Collectors.toList());
  }

  private Point parsePoint(String pointStr) {
    String[] coords = pointStr.split(",");
    double x = Double.parseDouble(coords[0]);
    double y = Double.parseDouble(coords[1]);
    return new Point(x, y);
  }
}
