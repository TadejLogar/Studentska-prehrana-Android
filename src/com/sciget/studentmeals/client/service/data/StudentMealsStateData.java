package com.sciget.studentmeals.client.service.data;

import org.ksoap2.serialization.SoapObject;

public class StudentMealsStateData {
	public String id;
	public String imageUrl;
	public String iframeUrl;
	public String viewState;
	public String eventValidation;
	public String stateHash;
	
	public StudentMealsStateData(SoapObject obj) {
		id = obj.getPropertyAsString("id");
		imageUrl = obj.getPropertyAsString("imageUrl");
		iframeUrl = obj.getPropertyAsString("iframeUrl");
		viewState = obj.getPropertyAsString("viewState");
		eventValidation = obj.getPropertyAsString("eventValidation");
		stateHash = obj.getPropertyAsString("stateHash");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getIframeUrl() {
		return iframeUrl;
	}
	
	public String getStateHash() {
		return stateHash;
	}

	public void setIframeUrl(String iframeUrl) {
		this.iframeUrl = iframeUrl;
	}

	public String getViewState() {
		return viewState;
	}

	public void setViewState(String viewState) {
		this.viewState = viewState;
	}

	public String getEventValidation() {
		return eventValidation;
	}

	public void setEventValidation(String eventValidation) {
		this.eventValidation = eventValidation;
	}
}