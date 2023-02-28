package com.appointment.booking.appointmentBooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.booking.appointmentBooking.exception.SystemUserException;
import com.appointment.booking.appointmentBooking.service.ISUserManagement;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class SUserManagementController extends BaseController {

	private final ISUserManagement userManagement;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
		try {
			return ResponseEntity.ok(userManagement.login(email, password));
		} catch (SystemUserException e) {
			return getExceptionResponse(e);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader(name = "AuthToken") String token) {
		return ResponseEntity.ok(userManagement.logout(token));
	}

	@GetMapping("/user-info")
	public ResponseEntity<String> getUserInfo(@RequestHeader(name = "AuthToken") String token) {
		return ResponseEntity.ok(userManagement.getUserDetails(token));
	}

}
