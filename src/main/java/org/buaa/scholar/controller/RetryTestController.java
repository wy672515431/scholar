package org.buaa.scholar.controller;

import org.buaa.scholar.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retry")
public class RetryTestController {
    @GetMapping("/test")
    @Retryable(maxAttempts = 10)
    public void testRetry() {
        int i = 1 / 0;
    }
}
