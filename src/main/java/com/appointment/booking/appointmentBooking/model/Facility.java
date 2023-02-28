package com.appointment.booking.appointmentBooking.model;

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

@Entity(name = "Facility")
@Table(name = "Facilities")
@Data
@NoArgsConstructor
public class Facility {

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = true, scale = 2)
	private Double fee;

	@Column
	private Integer duration;

	@Column(length = 10)
	private String color;

	@OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Appointment> appointments;

	public Facility(int id, String title, String description, String color) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.color = color;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this != obj || this.getClass() != obj.getClass()) {
			return false;
		}
		Facility other = (Facility) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
