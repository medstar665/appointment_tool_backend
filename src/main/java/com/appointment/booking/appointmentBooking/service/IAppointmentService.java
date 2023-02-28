package com.appointment.booking.appointmentBooking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.appointment.booking.appointmentBooking.constants.AppointmentStatus;
import com.appointment.booking.appointmentBooking.dto.AppointmentDto;
import com.appointment.booking.appointmentBooking.exception.AppointmentException;

public interface IAppointmentService {

	public AppointmentDto getAppointment(long id) throws AppointmentException;

	public List<AppointmentDto> getAllAppointments(String search, LocalDateTime start, LocalDateTime end);

	@Transactional
	public long addAppointment(AppointmentDto appointmentDto);

	@Transactional
	public void updateAppointment(AppointmentDto appointmentDto) throws AppointmentException;

	@Transactional
	public void deleteAppointment(long id) throws AppointmentException;

	@Transactional
	public void updateApointmentStatus(long id, AppointmentStatus status);

}
