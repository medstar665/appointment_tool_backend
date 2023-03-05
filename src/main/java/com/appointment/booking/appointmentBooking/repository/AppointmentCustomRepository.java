package com.appointment.booking.appointmentBooking.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.appointment.booking.appointmentBooking.model.Appointment;

public interface AppointmentCustomRepository {

	List<Appointment> searchAppointment(Integer pageNum, Integer pageSize, String keyword, LocalDateTime startDate, LocalDateTime endDate);

	Long getTotalAppointment(String keyword, LocalDateTime startDate, LocalDateTime endDate);
	
}
