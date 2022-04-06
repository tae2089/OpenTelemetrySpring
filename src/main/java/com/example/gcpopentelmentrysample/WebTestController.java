package com.example.gcpopentelmentrysample;



import lombok.RequiredArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebTestController {
    private final Logger logger = LoggerFactory.getLogger(WebTestController.class);
    private final OpenTelemetryService openTelemetryService;
    @GetMapping
    public String HelloWorld() throws Exception {
        logger.info("start hello world");
        openTelemetryService.OtelTest();
        logger.info("end hello world");
        return "Hello World";
    }
}
