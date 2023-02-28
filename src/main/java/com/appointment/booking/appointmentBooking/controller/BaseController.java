package com.appointment.booking.appointmentBooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.appointment.booking.appointmentBooking.constants.ResponseMessages;
import com.appointment.booking.appointmentBooking.exception.CustomException;

public abstract class BaseController {

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	protected ResponseEntity<String> handleMethodArgumentNotValid(
			final MethodArgumentNotValidException validationException, final WebRequest request) {
		String error = validationException.getFieldError().getDefaultMessage();
		return ResponseEntity.badRequest().body(error);
	}

	protected ResponseEntity<?> getExceptionResponse(CustomException e) {
		switch (e.getMessage()) {
		case ResponseMessages.INVALID_ID:
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseMessages.INVALID_ID);
		case ResponseMessages.INVALID_APTMT_STATUS_CHANGE:
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseMessages.INVALID_APTMT_STATUS_CHANGE);
		default:
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
