package com.example.gcpopentelmentrysample;

import io.opentelemetry.api.trace.Span;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class OpenTelemetryInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(OpenTelemetryInterceptor.class);
    private  Span span;
    private final OpenTelemetryComponent openTelemetryComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) { // HandlerMethod 는 후에 실행할 컨트롤러의 메소드 입니다. // 메소드명, 인스턴스, 타입, 사용된 Annotation 들을 확인할 수 있습니다.
            HandlerMethod method = (HandlerMethod) handler;
            this.span = openTelemetryComponent.getTracer().spanBuilder(method.getMethod().getName()).startSpan();
            MDC.put("TraceID", span.getSpanContext().getTraceId());
            MDC.put("span_id", span.getSpanContext().getSpanId());
            logger.info("intercepter test");
        }
            return true;
        }
        @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        this.span.end();
        MDC.clear();
    }
}
