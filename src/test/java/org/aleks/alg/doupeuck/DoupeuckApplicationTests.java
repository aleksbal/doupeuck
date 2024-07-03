package org.aleks.alg.doupeuck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.aleks.alg.doupeuck.controller.Endpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DoupeuckApplicationTests {

  @LocalServerPort
  private int port;

  @Autowired
  private Endpoint controller;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void contextLoads() {
    assertNotNull(controller);
  }

  @Test
  void simplifyLine() {
    final String request = "points=1.0,2.0;3.0,4.0;5.0,6.0&lambda=0.5";
    final String expected = """
        [{"x":1.0,"y":2.0},{"x":5.0,"y":6.0}]
        """;
    assertEquals(expected.trim(),
        this.restTemplate.getForObject("http://localhost:" + port + "/points?" + request, String.class));
  }
}
