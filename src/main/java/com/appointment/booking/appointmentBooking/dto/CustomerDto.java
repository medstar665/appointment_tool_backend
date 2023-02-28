package com.appointment.booking.appointmentBooking.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
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
public class CustomerDto {

	private int id;

	@NotBlank(message = "Error: first name is missing/blank")
	private String firstName;

	@NotBlank(message = "Error: last name is missing/blank")
	private String lastName;

	@NotNull(message = "Error: DOB is missing")
	private LocalDate dob;

	@NotBlank(message = "Error: phone no is missing/blank")
	private String phone;

	@NotBlank(message = "Error: email is missing/blank")
	private String email;

	private String note;

	private List<AppointmentDto> appointments;

}
