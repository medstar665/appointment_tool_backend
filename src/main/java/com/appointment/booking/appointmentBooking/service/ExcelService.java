package com.appointment.booking.appointmentBooking.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.appointment.booking.appointmentBooking.dto.AppointmentDto;

@Service
public class ExcelService implements IExcelService {

	@Override
	public ByteArrayInputStream getExportableExcel(List<AppointmentDto> data) {
		try {
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet();
			int rowIndex = 0;
			Row header = sheet.createRow(rowIndex++);
			createHeader(workbook, header);
			Row emptyRow = sheet.createRow(rowIndex++);
			for (AppointmentDto appointment : data) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(appointment.getId());
				row.createCell(1).setCellValue(
						appointment.getCustomer().getFirstName() + appointment.getCustomer().getLastName());
				row.createCell(2).setCellValue(appointment.getCustomer().getEmail());
				row.createCell(3).setCellValue(appointment.getFacility().getTitle());
				row.createCell(4).setCellValue(appointment.getAppointmentDateTime().toString().split("T")[0]);
				row.createCell(5).setCellValue(appointment.getAppointmentDateTime().toString().split("T")[1]);
				row.createCell(6).setCellValue(appointment.getStatus());
				row.createCell(7)
						.setCellValue(appointment.getDuration() == null ? "" : appointment.getDuration().toString());
				row.createCell(8).setCellValue(appointment.getColor() == null ? "" : appointment.getColor());
			}
			for (int i = 0; i < 9; i++) {
				sheet.autoSizeColumn(i);
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			return new ByteArrayInputStream(new byte[0]);
		}
	}

	private void createHeader(Workbook workbook, Row header) {
		header.createCell(0).setCellValue("#");
		header.createCell(1).setCellValue("Customer Name");
		header.createCell(2).setCellValue("Customer Email");
		header.createCell(3).setCellValue("Service Name");
		header.createCell(4).setCellValue("Appointment Date");
		header.createCell(5).setCellValue("Appointment Time");
		header.createCell(6).setCellValue("Status");
		header.createCell(7).setCellValue("Duration");
		header.createCell(8).setCellValue("Color");
	}

}
