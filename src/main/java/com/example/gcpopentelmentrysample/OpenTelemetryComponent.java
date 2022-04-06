package com.example.gcpopentelmentrysample;

import com.google.cloud.opentelemetry.trace.TraceConfiguration;
import com.google.cloud.opentelemetry.trace.TraceExporter;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;


@Component
public class OpenTelemetryComponent {
    private OpenTelemetry opentelemetry;
    private Tracer tracer;


    @PostConstruct
    public void setup() {
        setupTraceExporter();
    }

    public void setupTraceExporter() {
        // Using default project ID and Credentials
        try {
//            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("GCP-PROJECT-SERVICE-FILE"))
//                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
//            TraceConfiguration configuration =
//                    TraceConfiguration.builder().setCredentials(credentials).setProjectId("GCP-PROJECT-ID")
//                            .setDeadline(Duration.ofMillis(30000)).build();
            TraceConfiguration configuration =
                    TraceConfiguration.builder().setProjectId("GCP-PROJECT-ID")
                            .setDeadline(Duration.ofMillis(30000)).build();
            TraceExporter traceExporter = TraceExporter.createWithConfiguration(configuration);
            // Register the TraceExporter with OpenTelemetry
            // exporter를 두개 이상 사용하고 싶을 경우  .addSpanProecssor에 추가해줍니다.
            this.opentelemetry =   OpenTelemetrySdk.builder()
                    .setTracerProvider(SdkTracerProvider.builder().addSpanProcessor(SimpleSpanProcessor.create(traceExporter)).build())
                    .buildAndRegisterGlobal();
            this.tracer = opentelemetry.getTracer("rokit.aid.regen.back", "1.1.0");
        } catch (IOException e) {
            System.out.println("e = " + e);
        }
    }

    public OpenTelemetry getOpentelemetry() {
        return opentelemetry;
    }

    public Tracer getTracer() {
        return tracer;
    }

}
