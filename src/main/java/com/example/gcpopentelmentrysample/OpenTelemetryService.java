package com.example.gcpopentelmentrysample;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class OpenTelemetryService {

    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(OpenTelemetryService.class);

    public void OtelTest() throws Exception {
        logger.info("test");
        Thread.sleep(100 + random.nextInt(10) * 100);
        logger.info("test2");
    }
}
