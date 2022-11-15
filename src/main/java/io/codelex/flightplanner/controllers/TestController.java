package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.test.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestController {

    TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/clear")
    public void clear() {
        testService.clear();
    }
}
