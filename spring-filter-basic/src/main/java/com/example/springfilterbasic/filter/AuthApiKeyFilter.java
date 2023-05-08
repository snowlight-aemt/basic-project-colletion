package com.example.springfilterbasic.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1000)
@Component
public class AuthApiKeyFilter implements Filter {
    public static final List<String> apiKeys = new ArrayList<>();

    static {
        apiKeys.add("$2a$12$/svyjufiN7YtZNbnHmdEB.V3Udk1ueTK/2bSG/AD8Na5YrYqrHWEi");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        String apiKey = httpServletRequest.getHeader("api_key");

        if (!StringUtils.hasText(apiKey)) {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "api_key is invalid");
            return;
        }

        if (!apiKeys.contains(apiKey)) {
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "api_key does not match");
            return;
        }

        chain.doFilter(httpServletRequest, httpServletResponse);
        httpServletResponse.copyBodyToResponse();
    }
}