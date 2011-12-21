package com.sciget.studentmeals.client.service.data;

import org.ksoap2.serialization.SoapObject;

public class UserData extends Data {
	private int userId;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String pin;
	private String address;
	private String tempAddress;
	private String university;
	private String facility;
	private String length;
	private String currentYear;
	private String studyMethod;
	private String statusValidation;
	private String enrollmentNumber;
	private String phone;
	private int remainingSubsidies;

	public UserData(SoapObject obj) {
		super(obj);
		
		this.userId = getInt("userId");
		this.email = get("email");
		this.password = get("password");
		this.firstName = get("firstName");
		this.lastName = get("lastName");
		this.pin = get("pin");
		this.address = get("address");
		this.tempAddress = get("tempAddress");
		this.university = get("university");
		this.facility = get("facility");
		this.length = get("length");
		this.currentYear = get("currentYear");
		this.studyMethod = get("studyMethod");
		this.statusValidation = get("statusValidation");
		this.enrollmentNumber = get("enrollmentNumber");
		this.phone = get("phone");
		this.remainingSubsidies = getInt("remainingSubsidies");
	}

	public int getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPin() {
		return pin;
	}

	public String getAddress() {
		return address;
	}

	public String getTempAddress() {
		return tempAddress;
	}

	public String getUniversity() {
		return university;
	}

	public String getFacility() {
		return facility;
	}

	public String getLength() {
		return length;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public String getStudyMethod() {
		return studyMethod;
	}

	public String getStatusValidation() {
		return statusValidation;
	}

	public String getEnrollmentNumber() {
		return enrollmentNumber;
	}

	public String getPhone() {
		return phone;
	}
	
	public int getRemainingSubsidies() {
		return remainingSubsidies;
	}
}
