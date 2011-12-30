package com.sciget.studentmeals.client.service.data;

import org.ksoap2.serialization.SoapObject;

public class FileData extends Data {
	public class FileType {
		public static final int IMAGE = 1;
		public static final int AUDIO = 2;
		public static final int VIDEO = 3;
	}
	
	public class Size {
		public static final int SMALL = 1;
		public static final int ORIGINAL = 2;
	}
	
	public int id;
	public int restaurantId;
	public int userId;
	public int type;
	public String smallHash;
	public String hash;
	public boolean smallDone;
	public boolean done;
	public String fileKey;
	
	
	
	public FileData(int id, int restaurantId, int userId, int type,
			String smallHash, String hash, boolean smallDone, boolean done,
			String fileKey) {
		super();
		this.id = id;
		this.restaurantId = restaurantId;
		this.userId = userId;
		this.type = type;
		this.smallHash = smallHash;
		this.hash = hash;
		this.smallDone = smallDone;
		this.done = done;
		this.fileKey = fileKey;
	}
	
	public FileData(SoapObject obj) {
        super(obj);
        
        this.id = getInt("id");
        this.restaurantId = getInt("restaurantId");
        this.userId = getInt("userId");
        this.type = getInt("type");
        this.smallHash = get("smallHash");
        this.hash = get("hash");
        this.smallDone = getBoolean("smallHash");
        this.done = getBoolean("done");
        this.fileKey = get("fileKey");
    }

    public int getId() {
		return id;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public int getUserId() {
		return userId;
	}
	public int getType() {
		return type;
	}
	public String getSmallHash() {
		return smallHash;
	}
	public String getHash() {
		return hash;
	}
	public boolean isSmallDone() {
		return smallDone;
	}
	public boolean isDone() {
		return done;
	}
	public String getFileKey() {
		return fileKey;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setSmallHash(String smallHash) {
		this.smallHash = smallHash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public void setSmallDone(boolean smallDone) {
		this.smallDone = smallDone;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (done ? 1231 : 1237);
		result = prime * result + ((fileKey == null) ? 0 : fileKey.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + id;
		result = prime * result + restaurantId;
		result = prime * result + (smallDone ? 1231 : 1237);
		result = prime * result
				+ ((smallHash == null) ? 0 : smallHash.hashCode());
		result = prime * result + type;
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileData other = (FileData) obj;
		if (done != other.done)
			return false;
		if (fileKey == null) {
			if (other.fileKey != null)
				return false;
		} else if (!fileKey.equals(other.fileKey))
			return false;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (id != other.id)
			return false;
		if (restaurantId != other.restaurantId)
			return false;
		if (smallDone != other.smallDone)
			return false;
		if (smallHash == null) {
			if (other.smallHash != null)
				return false;
		} else if (!smallHash.equals(other.smallHash))
			return false;
		if (type != other.type)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
	
	
}
