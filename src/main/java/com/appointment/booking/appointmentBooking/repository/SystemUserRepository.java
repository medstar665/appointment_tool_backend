package com.appointment.booking.appointmentBooking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appointment.booking.appointmentBooking.model.SystemUser;

@Repository
@Transactional
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

	public SystemUser findByEmail(String email);
	
}
