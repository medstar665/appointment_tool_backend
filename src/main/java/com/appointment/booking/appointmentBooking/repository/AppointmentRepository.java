package com.appointment.booking.appointmentBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.appointment.booking.appointmentBooking.constants.AppointmentStatus;
import com.appointment.booking.appointmentBooking.model.Appointment;

@Repository
@Transactional
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, AppointmentCustomRepository {

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE Appointment apt SET apt.status = :status WHERE apt.id = :id")
	public int updateStatus(long id, AppointmentStatus status);
	
}
