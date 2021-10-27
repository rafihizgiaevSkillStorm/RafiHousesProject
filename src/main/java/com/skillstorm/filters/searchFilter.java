package com.skillstorm.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

//Our Filter that intercepts all call to our searchServlet with the url below
@WebFilter(urlPatterns = "/searchServlet")
public class searchFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// Debug print // System.out.println("searchHouse");

		HttpServletResponse resp = (HttpServletResponse) response;
		// Filter sends request to servlet
		chain.doFilter(request, resp);
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
////////////////////////////////RAFI HIZGIAEV ///////////////////////////////