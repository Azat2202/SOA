package ru.itmo.soa.configurations;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger log = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        String uri = requestContext.getUriInfo().getRequestUri().toString();

        log.info(">>> Request " + method + " " + uri);

        requestContext.getHeaders().forEach((k, v) -> log.info(">>> " + k + ": " + v));

        if (requestContext.hasEntity()) {
            InputStream in = requestContext.getEntityStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String body = reader.lines().collect(Collectors.joining("\n"));
                log.info(">>> Body: " + body);
                requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        log.info("<<< Response " + responseContext.getStatus());
        responseContext.getHeaders().forEach((k, v) -> log.info("<<< " + k + ": " + v));
    }
}

