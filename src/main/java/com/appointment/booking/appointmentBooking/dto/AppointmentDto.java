package com.appointment.booking.appointmentBooking.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class AppointmentDto {

	private long id;

	@NotNull(message = "Error: customer id is missing")
	private Integer customerId;

	@NotNull(message = "Error: facility id is missing")
	private Integer facilityId;

	private CustomerDto customer;

	private FacilityDto facility;

	@NotNull(message = "Error: date time is missing")
	private LocalDateTime appointmentDateTime;

	private String status;

	private Integer duration;

	private String color;

}
