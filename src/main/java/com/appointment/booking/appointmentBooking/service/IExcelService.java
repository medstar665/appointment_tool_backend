package com.appointment.booking.appointmentBooking.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.appointment.booking.appointmentBooking.dto.AppointmentDto;

public interface IExcelService {

	public ByteArrayInputStream getExportableExcel(List<AppointmentDto> data);
	
}
