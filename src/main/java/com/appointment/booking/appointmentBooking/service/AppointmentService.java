package com.appointment.booking.appointmentBooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.appointment.booking.appointmentBooking.constants.AppointmentStatus;
import com.appointment.booking.appointmentBooking.constants.ResponseMessages;
import com.appointment.booking.appointmentBooking.dto.AppointmentDto;
import com.appointment.booking.appointmentBooking.dto.PaginationResultDto;
import com.appointment.booking.appointmentBooking.exception.AppointmentException;
import com.appointment.booking.appointmentBooking.model.Appointment;
import com.appointment.booking.appointmentBooking.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService extends CommonService implements IAppointmentService {

	private final AppointmentRepository appointmentRepo;
	private final ICustomerService customerService;
	private final IFacilityService facilityService;

	@Override
	public AppointmentDto getAppointment(long id) throws AppointmentException {
		Appointment appointment = appointmentRepo.findById(id)
				.orElseThrow(() -> new AppointmentException(ResponseMessages.INVALID_ID));
		return getDtoFromEntity(appointment);
	}

	@Override
	public PaginationResultDto<AppointmentDto> getAllAppointments(Integer pageNum, Integer pageSize, String search,
			LocalDateTime startDate, LocalDateTime endDate) {
		if (startDate != null || endDate != null) {
			if (startDate == null) {
				startDate = LocalDateTime.of(2020, 1, 1, 0, 0);
			}
			if (endDate == null) {
				endDate = LocalDateTime.of(2500, 1, 1, 0, 0);
			}
		}
		Long totalAppointments = appointmentRepo.getTotalAppointment(search, startDate, endDate);
		List<Appointment> appointments = appointmentRepo.searchAppointment(pageNum, pageSize, search, startDate,
				endDate);
		return PaginationResultDto.<AppointmentDto>builder().pageNum(pageNum).pageSize(pageSize)
				.totalResult(totalAppointments)
				.result(appointments.stream().map(this::getDtoFromEntity).collect(Collectors.toList())).build();
	}

	@Override
	public long addAppointment(AppointmentDto appointmentDto) {
		Appointment appointment = new Appointment();
		appointment.setCustomer(customerService.getCustomerEntity(appointmentDto.getCustomerId()));
		appointment.setFacility(facilityService.getFacilityEntity(appointmentDto.getFacilityId()));
		appointment.setAppointmentDateTime(appointmentDto.getAppointmentDateTime());
		appointment.setDuration(appointmentDto.getDuration());
		appointment.setStatus(AppointmentStatus.BOOKED);
		appointment.setColor(appointmentDto.getColor());
		appointmentRepo.save(appointment);
		return appointment.getId();
	}

	@Override
	public void updateAppointment(AppointmentDto appointmentDto) throws AppointmentException {
		Appointment appointment = appointmentRepo.findById(appointmentDto.getId())
				.orElseThrow(() -> new AppointmentException(ResponseMessages.INVALID_ID));
		if (appointmentDto.getAppointmentDateTime() != null
				&& !appointment.getAppointmentDateTime().isEqual(appointmentDto.getAppointmentDateTime())) {
			appointment.setAppointmentDateTime(appointmentDto.getAppointmentDateTime());
		}
		if (appointmentDto.getCustomerId() != 0
				&& appointment.getCustomer().getId() != appointmentDto.getCustomerId()) {
			appointment.setCustomer(customerService.getCustomerEntity(appointmentDto.getCustomerId()));
		}
		if (appointmentDto.getFacilityId() != 0
				&& appointment.getFacility().getId() != appointmentDto.getFacilityId()) {
			appointment.setFacility(facilityService.getFacilityEntity(appointmentDto.getFacilityId()));
		}
		if (appointmentDto.getDuration() != null && appointment.getDuration() != appointmentDto.getDuration()) {
			appointment.setDuration(appointmentDto.getDuration());
		}
		if (appointmentDto.getColor() != null && !appointment.getColor().equals(appointmentDto.getColor())) {
			appointment.setColor(appointmentDto.getColor());
		}
		appointmentRepo.save(appointment);
	}

	@Override
	public void deleteAppointment(long id) throws AppointmentException {
		Appointment appointment = appointmentRepo.findById(id)
				.orElseThrow(() -> new AppointmentException(ResponseMessages.INVALID_ID));
		appointmentRepo.delete(appointment);
	}

	@Override
	public void updateApointmentStatus(long id, AppointmentStatus status) {
		Appointment appointment = appointmentRepo.findById(id)
				.orElseThrow(() -> new AppointmentException(ResponseMessages.INVALID_ID));
		boolean isValid = statusTransitionAllowed(appointment.getStatus(), status);
		if (isValid) {
			appointmentRepo.updateStatus(id, status);
			return;
		}
		throw new AppointmentException(ResponseMessages.INVALID_APTMT_STATUS_CHANGE);
	}

	private boolean statusTransitionAllowed(AppointmentStatus oldStatus, AppointmentStatus newStatus) {
		switch (oldStatus) {
		case COMPLETED:
		case CANCELLED:
			return false;
		case CHECK_IN:
			if (newStatus != AppointmentStatus.COMPLETED)
				return false;
			else
				return true;
		case NO_SHOW:
			if (newStatus == AppointmentStatus.RESCHEDULED || newStatus == AppointmentStatus.CANCELLED)
				return true;
			else
				return false;
		case RESCHEDULED:
			if (newStatus == AppointmentStatus.BOOKED)
				return false;
			else
				return true;
		case BOOKED:
			return true;
		default:
			return false;
		}
	}

}
