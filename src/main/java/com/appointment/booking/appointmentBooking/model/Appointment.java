package com.appointment.booking.appointmentBooking.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.appointment.booking.appointmentBooking.constants.AppointmentStatus;

import lombok.Data;

@Entity(name = "Appointment")
@Table(name = "Appointments")
@Data
public class Appointment {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "facility_id", nullable = false)
	private Facility facility;

	@Column(columnDefinition = "TIMESTAMP", nullable = false)
	private LocalDateTime appointmentDateTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AppointmentStatus status;

	@Column
	private Integer duration;

	@Column(length = 10)
	private String color;

}
