package com.appointment.booking.appointmentBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.appointment.booking.appointmentBooking.model.Facility;

@Repository
@Transactional
public interface FacilityRepository extends JpaRepository<Facility, Integer> {

	public List<Facility> findAllByTitleContainingIgnoreCase(String title);

	@Query("SELECT new com.appointment.booking.appointmentBooking.model.Facility(f.id, f.title, f.description, f.color) FROM Facility f")
	public List<Facility> findAllTitles();

	@Query("SELECT new com.appointment.booking.appointmentBooking.model.Facility(f.id, f.title, f.description, f.color) FROM Facility f WHERE upper(f.title) LIKE upper(concat('%', :search, '%')) OR upper(f.description) LIKE upper(concat('%', :search, '%'))")
	public List<Facility> findAllTitlesWithFilter(String search);

}
