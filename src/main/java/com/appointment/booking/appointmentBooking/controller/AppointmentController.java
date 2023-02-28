package com.appointment.booking.appointmentBooking.controller;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appointment.booking.appointmentBooking.constants.AppointmentStatus;
import com.appointment.booking.appointmentBooking.constants.ResponseMessages;
import com.appointment.booking.appointmentBooking.dto.AppointmentDto;
import com.appointment.booking.appointmentBooking.exception.AppointmentException;
import com.appointment.booking.appointmentBooking.exception.CustomException;
import com.appointment.booking.appointmentBooking.service.IAppointmentService;
import com.appointment.booking.appointmentBooking.service.IExcelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AppointmentController extends BaseController {

	private final IAppointmentService appointmentService;
	private final IExcelService excelService;

	@GetMapping("/appointment/{id}")
	public ResponseEntity<?> getAppointment(@PathVariable long id) {
		try {
			AppointmentDto appointment = appointmentService.getAppointment(id);
			return ResponseEntity.ok(appointment);
		} catch (AppointmentException e) {
			return getExceptionResponse(e);
		}
	}

	@GetMapping("/appointments")
	public ResponseEntity<?> getAllAppointments(@RequestParam(required = false) String search, @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endDate) {
		List<AppointmentDto> appointments = appointmentService.getAllAppointments(search, startDate, endDate);
		return ResponseEntity.ok(appointments);
	}

	@GetMapping(value = "/appointments-download")
	public ResponseEntity<?> downloadAppointments(@RequestParam(required = false) String search, @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endDate) {
		List<AppointmentDto> appointments = appointmentService.getAllAppointments(search, startDate, endDate);
		ByteArrayInputStream file = excelService.getExportableExcel(appointments);
		InputStreamResource resource = new InputStreamResource(file);
		String filename = "Appointments.xlsx";
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
				.body(resource);
	}

	@PostMapping("/appointment")
	public ResponseEntity<?> addAppointment(@Valid @RequestBody AppointmentDto appointment) {
		try {
			long id = appointmentService.addAppointment(appointment);
			return ResponseEntity.ok(id);
		} catch (CustomException e) {
			return getExceptionResponse(e);
		}
	}

	@PutMapping("/appointment")
	public ResponseEntity<?> updateAppointment(@RequestBody AppointmentDto appointment) {
		try {
			appointmentService.updateAppointment(appointment);
			return ResponseEntity.ok(ResponseMessages.APTMT_SAVED);
		} catch (CustomException e) {
			return getExceptionResponse(e);
		}
	}

	@PutMapping("/appointment/{id}/cancel")
	public ResponseEntity<?> cancelAppointment(@PathVariable long id) {
		try {
			appointmentService.updateApointmentStatus(id, AppointmentStatus.CANCELLED);
			return ResponseEntity.ok(ResponseMessages.APTMT_STATUS_CHANGED);
		} catch (AppointmentException e) {
			return getExceptionResponse(e);
		}
	}

	@PutMapping("/appointment/{id}/check-in")
	public ResponseEntity<?> checkInAppointment(@PathVariable long id) {
		try {
			appointmentService.updateApointmentStatus(id, AppointmentStatus.CHECK_IN);
			return ResponseEntity.ok(ResponseMessages.APTMT_STATUS_CHANGED);
		} catch (AppointmentException e) {
			return getExceptionResponse(e);
		}
	}

	@PutMapping("/appointment/{id}/no-show")
	public ResponseEntity<?> noShowAppointment(@PathVariable long id) {
		try {
			appointmentService.updateApointmentStatus(id, AppointmentStatus.NO_SHOW);
			return ResponseEntity.ok(ResponseMessages.APTMT_STATUS_CHANGED);
		} catch (AppointmentException e) {
			return getExceptionResponse(e);
		}
	}

//	@PutMapping("/appointment/{id}/reschedule")
	public ResponseEntity<?> rescheduleAppointment(@PathVariable long id) {
		try {
			appointmentService.updateApointmentStatus(id, AppointmentStatus.RESCHEDULED);
			return ResponseEntity.ok(ResponseMessages.APTMT_STATUS_CHANGED);
		} catch (AppointmentException e) {
			return getExceptionResponse(e);
		}
	}

	@PutMapping("/appointment/{id}/complete")
	public ResponseEntity<?> completeAppointment(@PathVariable long id) {
		try {
			appointmentService.updateApointmentStatus(id, AppointmentStatus.COMPLETED);
			return ResponseEntity.ok(ResponseMessages.APTMT_STATUS_CHANGED);
		} catch (AppointmentException e) {
			return getExceptionResponse(e);
		}
	}

	@DeleteMapping("/appointment/{id}")
	public ResponseEntity<?> deleteAppointment(@PathVariable long id) {
		try {
			appointmentService.deleteAppointment(id);
			return ResponseEntity.ok(ResponseMessages.APTMT_DELETED);
		} catch (AppointmentException e) {
			return getExceptionResponse(e);
		}
	}

}
