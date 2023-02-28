package com.appointment.booking.appointmentBooking.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.appointment.booking.appointmentBooking.exception.SystemUserException;
import com.appointment.booking.appointmentBooking.service.ISUserManagement;

@Component
public class AuthTokenReqFilter extends OncePerRequestFilter {

	@Autowired
	private ISUserManagement userManagement;

	private final List<String> whilteListedPath = List.of("/login", "/appointments-download");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		System.out.println(request.getContextPath());
//		System.out.println(request.getPathInfo());
//		System.out.println(request.getRequestURI());
//		System.out.println(request.getRequestURL().toString());
		if (!whilteListedPath.contains(request.getRequestURI())) {
			String authToken = request.getHeader("authorization");
			try {
				userManagement.validateToken(authToken);
			} catch (SystemUserException e) {
				response.getWriter().append(e.getMessage());
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

}
