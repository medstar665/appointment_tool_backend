package com.appointment.booking.appointmentBooking.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.appointment.booking.appointmentBooking.constants.ResponseMessages;
import com.appointment.booking.appointmentBooking.dto.FacilityDto;
import com.appointment.booking.appointmentBooking.exception.FacilityException;
import com.appointment.booking.appointmentBooking.service.IFacilityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class FacilityController extends BaseController {

	private final IFacilityService facilityService;

	@GetMapping("/facility/{id}")
	public ResponseEntity<?> getFacility(@PathVariable int id) {
		try {
			FacilityDto facilityDto = facilityService.getFacility(id);
			return ResponseEntity.ok(facilityDto);
		} catch (FacilityException e) {
			return getExceptionResponse(e);
		}
	}

	@GetMapping("/facilities")
	public ResponseEntity<?> getAllFacilities(@RequestParam(required = false) String search) {
		List<FacilityDto> facilites = facilityService.getAllFacility(search, false);
		return ResponseEntity.ok(facilites);
	}

	@GetMapping("/facility/titles")
	public ResponseEntity<?> getAllFacilitieTitles(@RequestParam(required = false) String search) {
		List<FacilityDto> facilites = facilityService.getAllFacility(search, true);
		return ResponseEntity.ok(facilites);
	}

	@PostMapping("/facility")
	public ResponseEntity<?> addFacility(@Valid @RequestBody FacilityDto facility) {
		int id = facilityService.addFacility(facility);
		return ResponseEntity.ok(id);
	}

	@PutMapping("/facility")
	public ResponseEntity<?> updateFacility(@RequestBody FacilityDto facility) {
		try {
			facilityService.updateFacility(facility);
			return ResponseEntity.ok(ResponseMessages.FACILITY_SAVED);
		} catch (FacilityException e) {
			return getExceptionResponse(e);
		}
	}

	@DeleteMapping("/facility/{id}")
	public ResponseEntity<?> deleteFacility(@PathVariable int id) {
		try {
			facilityService.deleteFacility(id);
			return ResponseEntity.ok(ResponseMessages.FACILITY_DELETED);
		} catch (FacilityException e) {
			return getExceptionResponse(e);
		}
	}

}
