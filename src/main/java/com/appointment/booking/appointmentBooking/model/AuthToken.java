package com.appointment.booking.appointmentBooking.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "AuthToken")
@Table(name = "auth_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthToken {

	@Id
	@Column
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private SystemUser user;

	@Column
	private String token;

	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;

	@Column
	@Builder.Default
	private boolean loggedOut = false;

}
