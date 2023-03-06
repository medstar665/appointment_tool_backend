package com.appointment.booking.appointmentBooking.service;

import org.springframework.transaction.annotation.Transactional;

import com.appointment.booking.appointmentBooking.dto.FacilityDto;
import com.appointment.booking.appointmentBooking.dto.PaginationResultDto;
import com.appointment.booking.appointmentBooking.exception.FacilityException;
import com.appointment.booking.appointmentBooking.model.Facility;

public interface IFacilityService {

	Facility getFacilityEntity(int id) throws FacilityException;

	public FacilityDto getFacility(int id) throws FacilityException;

	public PaginationResultDto<FacilityDto> getAllFacility(Integer pageNum, Integer pageSize, String search);

	@Transactional
	public int addFacility(FacilityDto facilityDto);

	@Transactional
	public void updateFacility(FacilityDto facilityDto) throws FacilityException;

	@Transactional
	public void deleteFacility(int id) throws FacilityException;

}
