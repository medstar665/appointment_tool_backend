package com.appointment.booking.appointmentBooking.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.appointment.booking.appointmentBooking.constants.ResponseMessages;
import com.appointment.booking.appointmentBooking.exception.SystemUserException;
import com.appointment.booking.appointmentBooking.model.AuthToken;
import com.appointment.booking.appointmentBooking.model.SystemUser;
import com.appointment.booking.appointmentBooking.repository.AuthTokenRepository;
import com.appointment.booking.appointmentBooking.repository.SystemUserRepository;
import com.fasterxml.uuid.Generators;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SUserManagement implements ISUserManagement {

	private final SystemUserRepository suserRepo;
	private final AuthTokenRepository authTokenRepo;

	@Value("${custom.session.expiry:60}")
	private int sessionExpiry;

	@Transactional
	public String login(String email, String password) throws SystemUserException {
		SystemUser user = suserRepo.findByEmail(email);
		if (user == null) {
			throw new SystemUserException(ResponseMessages.INVALID_LOGIN_CRED);
		}
		if (!password.equals(user.getPassword())) {
			throw new SystemUserException(ResponseMessages.INVALID_LOGIN_CRED);
		}
		UUID uuidToken = Generators.timeBasedGenerator().generate();
		AuthToken token = AuthToken.builder().user(user).token(uuidToken.toString()).createdAt(LocalDateTime.now())
				.build();
		authTokenRepo.save(token);
		return uuidToken.toString() + "," + String.valueOf(sessionExpiry) + "," + user.getName();
	}

	public boolean validateToken(String token) throws SystemUserException {
		AuthToken authToken = authTokenRepo.findByToken(token);
		if (authToken == null) {
			throw new SystemUserException(ResponseMessages.SESSION_INVALID);
		}
		if (authToken.isLoggedOut()) {
			throw new SystemUserException(ResponseMessages.SESSION_LOGGED_OUT);
		}
		if (authToken.getCreatedAt().plusMinutes(sessionExpiry).isBefore(LocalDateTime.now())) {
			throw new SystemUserException(ResponseMessages.SESSION_EXPIRED);
		}
		return true;
	}

	@Transactional
	public String logout(String token) {
		authTokenRepo.logoutByAuthToken(token.toUpperCase());
		return ResponseMessages.LOGOUT_SUCCESS;
	}

	@Override
	public String getUserDetails(String stringToken) {
		AuthToken token = authTokenRepo.findByToken(stringToken);
		SystemUser user = token.getUser();
		return user.getName();
	}

}
