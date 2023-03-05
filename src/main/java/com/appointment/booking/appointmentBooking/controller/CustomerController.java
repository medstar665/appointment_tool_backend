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
import com.appointment.booking.appointmentBooking.dto.CustomerDto;
import com.appointment.booking.appointmentBooking.dto.PaginationResultDto;
import com.appointment.booking.appointmentBooking.exception.CustomerException;
import com.appointment.booking.appointmentBooking.service.ICustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController extends BaseController {

	private final ICustomerService customerService;

	@GetMapping("/customer/{id}")
	public ResponseEntity<?> getCustomer(@PathVariable int id) {
		try {
			CustomerDto customer = customerService.getCustomer(id);
			return ResponseEntity.ok(customer);
		} catch (CustomerException e) {
			return getExceptionResponse(e);
		}
	}

	@GetMapping("/customers")
	public ResponseEntity<?> getAllCustomers(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String search) {
		PaginationResultDto<CustomerDto> customers = customerService.getAllCustomers(pageNum, pageSize, search, false);
		return ResponseEntity.ok(customers);
	}
	
	@GetMapping("/customer/names")
	public ResponseEntity<?> getCustomerNames(@RequestParam(required = false) String search) {
		PaginationResultDto<CustomerDto> customers = customerService.getAllCustomers(null, null, search, true);
		return ResponseEntity.ok(customers);
	}

	@PostMapping("/customer")
	public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerDto customer) {
		int id = customerService.addCustomer(customer);
		return ResponseEntity.ok(id);
	}

	@PutMapping("/customer")
	public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customer) {
		try {
			customerService.updateCustomer(customer);
			return ResponseEntity.ok(ResponseMessages.CUSTOMER_SAVED);
		} catch (CustomerException e) {
			return getExceptionResponse(e);
		}
	}

	@DeleteMapping("/customer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
		try {
			customerService.deleteCustomer(id);
			return ResponseEntity.ok(ResponseMessages.CUSTOMER_DELETED);
		} catch (CustomerException e) {
			return getExceptionResponse(e);
		}
	}

}
