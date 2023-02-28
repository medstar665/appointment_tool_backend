package com.appointment.booking.appointmentBooking.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.appointment.booking.appointmentBooking.dto.FacilityDto;
import com.appointment.booking.appointmentBooking.exception.FacilityException;
import com.appointment.booking.appointmentBooking.model.Facility;

public interface IFacilityService {

	Facility getFacilityEntity(int id) throws FacilityException;

	public FacilityDto getFacility(int id) throws FacilityException;

	public List<FacilityDto> getAllFacility(String search, boolean justTitle);

	@Transactional
	public int addFacility(FacilityDto facilityDto);

	@Transactional
	public void updateFacility(FacilityDto facilityDto) throws FacilityException;

	@Transactional
	public void deleteFacility(int id) throws FacilityException;

}
