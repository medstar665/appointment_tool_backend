package com.appointment.booking.appointmentBooking.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.appointment.booking.appointmentBooking.dto.CustomerDto;
import com.appointment.booking.appointmentBooking.exception.CustomerException;
import com.appointment.booking.appointmentBooking.model.Customer;

public interface ICustomerService {

	Customer getCustomerEntity(int id) throws CustomerException;

	public CustomerDto getCustomer(int id) throws CustomerException;

	public List<CustomerDto> getAllCustomers(String search, boolean justName);

	@Transactional
	public int addCustomer(CustomerDto customerDto);

	@Transactional
	public void updateCustomer(CustomerDto customerDto) throws CustomerException;

	@Transactional
	public void deleteCustomer(int id) throws CustomerException;

}
