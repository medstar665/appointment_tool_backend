package com.appointment.booking.appointmentBooking.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Customer")
@Table(name = "Customers")
@Data
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, columnDefinition = "DATE")
	private LocalDate dob;

	@Column(nullable = false)
	private String phone;

	@Column(nullable = false)
	private String email;

	@Column
	private String note;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Appointment> appointments;

	public Customer(int id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this != obj || this.getClass() != obj.getClass()) {
			return false;
		}
		Customer other = (Customer) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
