package com.appointment.booking.appointmentBooking.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.appointment.booking.appointmentBooking.model.Appointment;

public interface AppointmentCustomRepository {

	List<Appointment> searchAppointment(String keyword, LocalDateTime startDate, LocalDateTime endDate);
	
}
