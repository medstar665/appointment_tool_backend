package com.appointment.booking.appointmentBooking.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.appointment.booking.appointmentBooking.model.Customer;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Long countByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName,
			String lastName, String email);

	public List<Customer> findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName,
			String lastName, String email, Pageable pageable);

	@Query("SELECT new com.appointment.booking.appointmentBooking.model.Customer(c.id, c.firstName, c.lastName, c.email) FROM Customer c")
	public List<Customer> findAllNames();
	
	@Query("SELECT new com.appointment.booking.appointmentBooking.model.Customer(c.id, c.firstName, c.lastName, c.email) FROM Customer c WHERE upper(c.firstName) LIKE upper(concat('%', :search, '%')) OR upper(c.lastName) LIKE upper(concat('%', :search, '%')) OR upper(c.email) LIKE upper(concat('%', :search, '%'))")
	public List<Customer> findAllNamesWithFilter(String search);
	
}
