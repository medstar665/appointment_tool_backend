package com.appointment.booking.appointmentBooking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PaginationResultDto<T> {

	private Integer pageNum;
	
	private Integer pageSize;
	
	private Long totalResult;

	private List<T> result;

}
