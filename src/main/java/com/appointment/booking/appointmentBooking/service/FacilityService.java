package com.appointment.booking.appointmentBooking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appointment.booking.appointmentBooking.constants.ResponseMessages;
import com.appointment.booking.appointmentBooking.dto.FacilityDto;
import com.appointment.booking.appointmentBooking.exception.FacilityException;
import com.appointment.booking.appointmentBooking.model.Facility;
import com.appointment.booking.appointmentBooking.repository.FacilityRepository;
import com.appointment.booking.appointmentBooking.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityService extends CommonService implements IFacilityService {

	private final FacilityRepository facilityRepo;

	@Override
	public Facility getFacilityEntity(int id) throws FacilityException {
		return facilityRepo.findById(id).orElseThrow(() -> new FacilityException(ResponseMessages.INVALID_ID));
	}

	@Override
	public FacilityDto getFacility(int id) throws FacilityException {
		Facility facility = facilityRepo.findById(id)
				.orElseThrow(() -> new FacilityException(ResponseMessages.INVALID_ID));
		return getDtoFromEntity(facility);
	}

	@Override
	public List<FacilityDto> getAllFacility(String search, boolean justTitle) {
		List<Facility> facilities;
		if(justTitle) {
			if (CommonUtil.isEmptyString(search)) {
				facilities = facilityRepo.findAllTitles();
			} else {
				facilities = facilityRepo.findAllTitlesWithFilter(search);
			}
		} else {			
			if (CommonUtil.isEmptyString(search)) {
				facilities = facilityRepo.findAll();
			} else {
				facilities = facilityRepo.findAllByTitleContainingIgnoreCase(search);
			}
		}
		return facilities.stream().map(this::getDtoFromEntity).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public int addFacility(FacilityDto facilityDto) {
		Facility facility = new Facility();
		facility.setTitle(facilityDto.getTitle());
		facility.setDescription(facilityDto.getDescription());
		facility.setFee(facilityDto.getFee());
		facility.setDuration(facilityDto.getDuration());
		facility.setColor(facilityDto.getColor());
		facilityRepo.save(facility);
		return facility.getId();
	}

	@Override
	@Transactional
	public void updateFacility(FacilityDto facilityDto) throws FacilityException {
		Facility facility = facilityRepo.findById(facilityDto.getId())
				.orElseThrow(() -> new FacilityException(ResponseMessages.INVALID_ID));
		if (!CommonUtil.isEmptyString(facilityDto.getTitle()) && !facility.getTitle().equals(facilityDto.getTitle())) {
			facility.setTitle(facilityDto.getTitle());
		}
		if (!CommonUtil.isEmptyString(facilityDto.getDescription())
				&& !facility.getDescription().equals(facilityDto.getDescription())) {
			facility.setDescription(facilityDto.getDescription());
		}
		if (facilityDto.getDuration() != null && facility.getDuration() != facilityDto.getDuration()) {
			facility.setDuration(facilityDto.getDuration());
		}
		if (facilityDto.getFee() != null && facility.getFee() != facilityDto.getFee()) {
			facility.setFee(facilityDto.getFee());
		}
		if (facilityDto.getColor() != null && !facility.getColor().equals(facilityDto.getColor())) {
			facility.setColor(facilityDto.getColor());
		}
		facilityRepo.save(facility);
	}

	@Override
	@Transactional
	public void deleteFacility(int id) throws FacilityException {
		Facility facility = facilityRepo.findById(id)
				.orElseThrow(() -> new FacilityException(ResponseMessages.INVALID_ID));
		facilityRepo.delete(facility);
	}

}
