package com.appointment.booking.appointmentBooking.service;

import com.appointment.booking.appointmentBooking.dto.AppointmentDto;
import com.appointment.booking.appointmentBooking.dto.CustomerDto;
import com.appointment.booking.appointmentBooking.dto.FacilityDto;
import com.appointment.booking.appointmentBooking.model.Appointment;
import com.appointment.booking.appointmentBooking.model.Customer;
import com.appointment.booking.appointmentBooking.model.Facility;

public abstract class CommonService {

	protected CustomerDto getDtoFromEntity(Customer entity) {
		return CustomerDto.builder().id(entity.getId()).firstName(entity.getFirstName()).lastName(entity.getLastName())
				.dob(entity.getDob()).email(entity.getEmail()).phone(entity.getPhone()).note(entity.getNote()).build();

	}

	protected FacilityDto getDtoFromEntity(Facility facility) {
		return FacilityDto.builder().id(facility.getId()).title(facility.getTitle())
				.description(facility.getDescription()).fee(facility.getFee()).duration(facility.getDuration())
				.color(facility.getColor()).build();
	}

	protected AppointmentDto getDtoFromEntity(Appointment appointment) {
		return AppointmentDto.builder().id(appointment.getId()).customer(getDtoFromEntity(appointment.getCustomer()))
				.facility(getDtoFromEntity(appointment.getFacility()))
				.appointmentDateTime(appointment.getAppointmentDateTime()).status(appointment.getStatus().getValue())
				.duration(appointment.getDuration()).color(appointment.getColor()).build();
	}

}
