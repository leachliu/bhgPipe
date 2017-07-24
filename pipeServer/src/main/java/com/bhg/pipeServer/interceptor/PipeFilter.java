package com.bhg.pipeServer.interceptor;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.gson.Gson;

public final class PipeFilter implements Filter {

    private FilterConfig filterConfig = null;

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        if (filterConfig == null)
            return;
        Gson gson = new Gson();
        
        Enumeration names = request.getParameterNames();
        StringBuffer output = new StringBuffer();
        output.append(gson.toJson(request.getParameterMap()));
//        while (names.hasMoreElements()) {
//            String name = (String) names.nextElement();
//            output.append(name + "=");
//            String values[] = request.getParameterValues(name);
//            for (int i = 0; i < values.length; i++) {
//                if (i > 0)
//                    output.append("' ");
//                output.append(values[i]);
//            }
//            if (names.hasMoreElements())
//                output.append("&");
//        }
        request.setAttribute("post", output);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
}