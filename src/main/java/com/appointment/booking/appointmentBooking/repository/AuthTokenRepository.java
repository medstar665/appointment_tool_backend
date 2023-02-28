package com.appointment.booking.appointmentBooking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.appointment.booking.appointmentBooking.model.AuthToken;

@Repository
@Transactional
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

	AuthToken findByToken(String token);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE AuthToken at SET at.loggedOut=true WHERE UPPER(at.token) = :token")
	int logoutByAuthToken(String token);

}
