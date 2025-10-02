package ru.itmo.soa;

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

        // headers
        requestContext.getHeaders().forEach((k, v) -> log.info(">>> " + k + ": " + v));

        // body (need to buffer input stream!)
        if (requestContext.hasEntity()) {
            InputStream in = requestContext.getEntityStream();
            String body = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            log.info(">>> Body: " + body);

            // reset stream so resource method can read it
            requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        log.info("<<< Response " + responseContext.getStatus());
        responseContext.getHeaders().forEach((k, v) -> log.info("<<< " + k + ": " + v));
        // Response body logging requires a WriterInterceptor (can show if you need it)
    }
}