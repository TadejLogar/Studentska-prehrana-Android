package com.sciget.studentmeals.client.service.data;

import org.ksoap2.serialization.SoapObject;

public class UserData extends Data {
	public int userId;
	public String email;
	public String password;
	public String firstName;
	public String lastName;
	public String pin;
	public String address;
	public String tempAddress;
	public String university;
	public String facility;
	public String length;
	public String currentYear;
	public String studyMethod;
	public String statusValidation;
	public String enrollmentNumber;
	public String phone;
	public int remainingSubsidies;

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
