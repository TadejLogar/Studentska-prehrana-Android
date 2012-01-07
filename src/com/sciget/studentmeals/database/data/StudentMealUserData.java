package com.sciget.studentmeals.database.data;

import android.database.sqlite.SQLiteDatabase;

import com.sciget.mvc.MVC;
import com.sciget.studentmeals.database.Database;

public class StudentMealUserData extends Data {
    public static final String NAME = "students_meals_users";
    
	public static class Address {
		public String street; // ulica in hišna številka
		public String post;
		public String country;
		
		public Address(String street, String post, String country) {
			this.street = street;
			this.post = post;
			this.country = country;
		}

		@Override
		public String toString() {
			return street + ", " + post + ", " + country;
		}
		
	}

	public int id;
	public int userId;
	public String email;
	public String password;
	public String firstName;
	public String lastName;
	public String pin;
	public Address address;
	public Address tempAddress;
	public String university;
	public String facility;
	public String length;
	public String currentYear;
	public String studyMethod;
	public String statusValidation;
	public String enrollmentNumber;
	public String phone;
	public String key;
	public String cookies;
	public int remainingSubsidies;
	
	public StudentMealUserData() {}
	
	public StudentMealUserData(int userId, String email, String password, String cookies) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.address = new Address(null, null, null);
		this.tempAddress = new Address(null, null, null);
		this.key = MVC.random(20);
		this.cookies = cookies;
	}

	public StudentMealUserData(int id, int userId, String email, String password, String firstName, String lastName, String pin, String addressStreet, String addressPost, String addressCountry,
			String tempAddressStreet, String tempAddressPost, String tempAddressCountry, String university, String facility, String length, String currentYear, String studyMethod,
			String statusValidation, String enrollmentNumber, String phone, String key, String cookies, int remainingSubsidies) {
		this.id = id;
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pin = pin;
		this.address = new Address(addressStreet, addressPost, addressCountry);
		this.tempAddress = new Address(tempAddressStreet, tempAddressPost, tempAddressCountry);
		this.university = university;
		this.facility = facility;
		this.length = length;
		this.currentYear = currentYear;
		this.studyMethod = studyMethod;
		this.statusValidation = statusValidation;
		this.enrollmentNumber = enrollmentNumber;
		this.phone = phone;
		this.key = key;
		this.cookies = cookies;
		this.remainingSubsidies = remainingSubsidies;
	}
	
	public StudentMealUserData(int userId, String firstName, String lastName, String pin, String addressStreet, String addressPost, String addressCountry, String tempAddressStreet, String tempAddressPost, String tempAddressCountry, String university, String facility, String length, String currentYear, String studyMethod, String statusValidation, String enrollmentNumber, String phone, String key, String cookies, int remainingSubsidies) {
		this(0, userId, null, null, firstName, lastName, pin, addressStreet, addressPost, addressCountry, tempAddressStreet, tempAddressPost, tempAddressCountry, university, facility, length, currentYear, studyMethod, statusValidation, enrollmentNumber, phone, key, cookies, remainingSubsidies);
	}

	public int add(Database db) {
		int id = db.getValue("SELECT id FROM " + NAME + " WHERE userId = " + userId);
		if (id == -1) {
			return db.update("INSERT INTO " + NAME + " (userId, email, password, firstName, lastName, pin, addressStreet, addressPost, addressCountry, tempAddressStreet, tempAddressPost, tempAddressCountry, university, facility, length, currentYear, studyMethod, statusValidation, enrollmentNumber, phone, `key`, cookies, remainingSubsidies) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { userId, email, password, firstName, lastName, pin, address.street, address.post, address.country, tempAddress.street, tempAddress.post, tempAddress.country, university, facility, length, currentYear, studyMethod, statusValidation, enrollmentNumber, phone, key, cookies, remainingSubsidies });
		} else {
			//return db.update("UPDATE " + NAME + " SET userId = ?, email = ?, password = ?, firstName = ?, lastName = ?, pin = ?, addressStreet = ?, addressPost = ?, addressCountry = ?, tempAddressStreet = ?, tempAddressPost = ?, tempAddressCountry = ?, university = ?, facility = ?, length = ?, currentYear = ?, studyMethod = ?, statusValidation = ?, enrollmentNumber = ?, phone = ?, cookies = ? WHERE id = " + id, new Object[] { userId, email, password, firstName, lastName, pin, address.street, address.post, address.country, tempAddress.street, tempAddress.post, tempAddress.country, university, facility, length, currentYear, studyMethod, statusValidation, enrollmentNumber, phone, cookies });
			return db.update("UPDATE " + NAME + " SET firstName = ?, lastName = ?, pin = ?, addressStreet = ?, addressPost = ?, addressCountry = ?, tempAddressStreet = ?, tempAddressPost = ?, tempAddressCountry = ?, university = ?, facility = ?, length = ?, currentYear = ?, studyMethod = ?, statusValidation = ?, enrollmentNumber = ?, phone = ?, cookies = ?, remainingSubsidies = ? WHERE id = " + id, new Object[] { firstName, lastName, pin, address.street, address.post, address.country, tempAddress.street, tempAddress.post, tempAddress.country, university, facility, length, currentYear, studyMethod, statusValidation, enrollmentNumber, phone, cookies, remainingSubsidies });
		}
	}
	
	public void create(SQLiteDatabase db) {
	    //if (Database.tableExists(db, NAME)) return;
	    db.execSQL("DROP TABLE IF EXISTS " + NAME);
	    String sql = new StringBuilder().
            append("CREATE TABLE `students_meals_users` (\n").
            append("  `id` INTEGER PRIMARY KEY AUTOINCREMENT,\n").
            append("  `userId` integer,\n").
            append("  `email` varchar(250),\n").
            append("  `password` varchar(250),\n").
            append("  `firstName` varchar(250),\n").
            append("  `lastName` varchar(250),\n").
            append("  `pin` varchar(250),\n").
            append("  `addressStreet` varchar(250),\n").
            append("  `addressPost` varchar(250),\n").
            append("  `addressCountry` varchar(250),\n").
            append("  `tempAddressStreet` varchar(250),\n").
            append("  `tempAddressPost` varchar(250),\n").
            append("  `tempAddressCountry` varchar(250),\n").
            append("  `university` varchar(250),\n").
            append("  `facility` varchar(250),\n").
            append("  `length` varchar(250),\n").
            append("  `currentYear` varchar(250),\n").
            append("  `studyMethod` varchar(250),\n").
            append("  `statusValidation` varchar(250),\n").
            append("  `enrollmentNumber` varchar(250),\n").
            append("  `phone` varchar(250),\n").
            append("  `key` varchar(100),\n").
            append("  `cookies` text,\n").
            append("  `remainingSubsidies` integer\n").
            append(")").
        toString();
	    db.execSQL(sql);
	}
	
	

}