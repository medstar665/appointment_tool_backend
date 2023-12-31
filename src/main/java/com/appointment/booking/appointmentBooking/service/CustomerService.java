package com.appointment.booking.appointmentBooking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appointment.booking.appointmentBooking.constants.ResponseMessages;
import com.appointment.booking.appointmentBooking.dto.CustomerDto;
import com.appointment.booking.appointmentBooking.dto.PaginationResultDto;
import com.appointment.booking.appointmentBooking.exception.CustomerException;
import com.appointment.booking.appointmentBooking.model.Customer;
import com.appointment.booking.appointmentBooking.repository.CustomerRepository;
import com.appointment.booking.appointmentBooking.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService extends CommonService implements ICustomerService {

	private final CustomerRepository customerRepo;

	@Override
	public Customer getCustomerEntity(int id) throws CustomerException {
		return customerRepo.findById(id).orElseThrow(() -> new CustomerException(ResponseMessages.INVALID_ID));
	}

	@Override
	public CustomerDto getCustomer(int id) throws CustomerException {
		Customer entity = customerRepo.findById(id)
				.orElseThrow(() -> new CustomerException(ResponseMessages.INVALID_ID));
		return getDtoFromEntity(entity);
	}

	@Override
	public PaginationResultDto<CustomerDto> getAllCustomers(Integer pageNum, Integer pageSize, String search,
			boolean justName) {
		List<Customer> customers;
		Long totalCustomers = null;
		if (justName) {
			if (CommonUtil.isEmptyString(search)) {
				customers = customerRepo.findAllNames();
			} else {
				customers = customerRepo.findAllNamesWithFilter(search);
			}
		} else {
			if (CommonUtil.isEmptyString(search)) {
				Page<Customer> pageResult = customerRepo.findAll(getPageable(pageNum, pageSize));
				totalCustomers = pageResult.getTotalElements();
				customers = pageResult.getContent();
			} else {
				totalCustomers = customerRepo
						.countByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
								search, search, search);
				customers = customerRepo
						.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
								search, search, search, getPageable(pageNum, pageSize));
			}
		}
		return PaginationResultDto.<CustomerDto>builder().pageNum(pageNum).pageSize(pageSize)
				.totalResult(totalCustomers)
				.result(customers.stream().map(this::getDtoFromEntity).collect(Collectors.toList())).build();
	}

	@Override
	@Transactional
	public int addCustomer(CustomerDto customerDto) {
		Customer entity = new Customer();
		entity.setFirstName(customerDto.getFirstName());
		entity.setLastName(customerDto.getLastName());
		entity.setEmail(customerDto.getEmail());
		entity.setPhone(customerDto.getPhone());
		entity.setNote(customerDto.getNote());
		entity.setDob(customerDto.getDob());
		entity = customerRepo.save(entity);
		return entity.getId();
	}

	@Override
	@Transactional
	public void updateCustomer(CustomerDto customerDto) throws CustomerException {
		Customer oldEntity = customerRepo.findById(customerDto.getId())
				.orElseThrow(() -> new CustomerException(ResponseMessages.INVALID_ID));
		if (!CommonUtil.isEmptyString(customerDto.getFirstName())
				&& !oldEntity.getFirstName().equals(customerDto.getFirstName())) {
			oldEntity.setFirstName(customerDto.getFirstName());
		}
		if (!CommonUtil.isEmptyString(customerDto.getLastName())
				&& !oldEntity.getLastName().equals(customerDto.getLastName())) {
			oldEntity.setLastName(customerDto.getLastName());
		}
		if (!CommonUtil.isEmptyString(customerDto.getEmail()) && !oldEntity.getEmail().equals(customerDto.getEmail())) {
			oldEntity.setEmail(customerDto.getEmail());
		}
		if (!CommonUtil.isEmptyString(customerDto.getPhone()) && !oldEntity.getPhone().equals(customerDto.getPhone())) {
			oldEntity.setPhone(customerDto.getPhone());
		}
		oldEntity.setNote(customerDto.getNote());
		if (customerDto.getDob() != null && !oldEntity.getDob().isEqual(customerDto.getDob())) {
			oldEntity.setDob(customerDto.getDob());
		}
		customerRepo.save(oldEntity);
	}

	@Override
	@Transactional
	public void deleteCustomer(int id) throws CustomerException {
		Customer customer = customerRepo.findById(id)
				.orElseThrow(() -> new CustomerException(ResponseMessages.INVALID_ID));
		customerRepo.delete(customer);
	}

}
