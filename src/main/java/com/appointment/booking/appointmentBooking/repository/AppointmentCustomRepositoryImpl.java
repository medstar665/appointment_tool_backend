package com.appointment.booking.appointmentBooking.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.appointment.booking.appointmentBooking.model.Appointment;
import com.appointment.booking.appointmentBooking.util.CommonUtil;

public class AppointmentCustomRepositoryImpl implements AppointmentCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Appointment> searchAppointment(String keyword, LocalDateTime startDate, LocalDateTime endDate) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Appointment> criteriaQuery = cb.createQuery(Appointment.class);
		Root<Appointment> root = criteriaQuery.from(Appointment.class);
		List<Predicate> predicates = new ArrayList<>();
		if(!CommonUtil.isEmptyString(keyword)) {			
			Predicate prd01 = cb.like(cb.upper(root.get("customer").get("firstName")), "%" + keyword.toUpperCase() + "%");
			Predicate prd02 = cb.like(cb.upper(root.get("customer").get("lastName")), "%" + keyword.toUpperCase() + "%");
			Predicate prd03 = cb.like(cb.upper(root.get("facility").get("title")), "%" + keyword.toUpperCase() + "%");
			predicates.add(cb.or(prd01, prd02, prd03));
		}
		if(startDate != null) {
			predicates.add(cb.between(root.get("appointmentDateTime"), startDate, endDate));			
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
