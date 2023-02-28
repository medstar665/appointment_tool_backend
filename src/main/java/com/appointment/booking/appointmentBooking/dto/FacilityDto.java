package com.appointment.booking.appointmentBooking.dto;

import java.util.List;

import javax.validation.constraints.Min;
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
public class FacilityDto {

	private int id;

	@NotBlank(message = "Error: Facility is missing/blank")
	private String title;

	@NotBlank(message = "Error: Description is missing/blank")
	private String description;

//	@Min(0)
//	@NotNull(message = "Error: Fee is missing")
	private Double fee;

	private Integer duration;

	private String color;

	private List<AppointmentDto> appointments;

}
