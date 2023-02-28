package com.appointment.booking.appointmentBooking.service;

import com.appointment.booking.appointmentBooking.exception.SystemUserException;

public interface ISUserManagement {

	public String login(String email, String password) throws SystemUserException;

	public boolean validateToken(String token) throws SystemUserException;

	public String logout(String token);
	
	public String getUserDetails(String token);

}
