package com.appointment.booking.appointmentBooking.constants;

public enum AppointmentStatus {

	BOOKED("Booked"), NO_SHOW("No-Show"), CHECK_IN("Check-In"), COMPLETED("Completed"), CANCELLED("Cancelled"), RESCHEDULED("Rescheduled");

	private final String value;

	private AppointmentStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
