/*package com.sciget.studentmeals.client;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

public class AndroidClientService {
	private static final String URL = "http://192.168.1.101:8080/StudentMealsWebService/services/StudentMealsMain?wsdl";
	private static final String METHOD = "captchaImageUrl";
	private static final String NAMESPACE = "http://studentmeals.sciget.com";

	public String main() {
		SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		soapSerializationEnvelope.dotNet = false;

		SoapObject soapObject = new SoapObject(NAMESPACE, METHOD); 
		soapSerializationEnvelope.setOutputSoapObject(soapObject);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setType(PropertyInfo.STRING_CLASS);
		propertyInfo.setName("arg0");
		propertyInfo.setValue("Roderick L. Barnes, Sr.");
		soapObject.addProperty(propertyInfo);
		 
        try {
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
            httpTransportSE.debug = true;
            httpTransportSE.call(NAMESPACE + METHOD, soapSerializationEnvelope);
            SoapObject objectResult = (SoapObject) soapSerializationEnvelope.getResponse();
            return (String) objectResult.getProperty("imageUrl").toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
        return "NAPAKA";
	}
	
}*/