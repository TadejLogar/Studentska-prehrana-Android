package com.sciget.studentmeals.client;
/*
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class AndroidWSClient {

	private static final String NAMESPACE = "http://hello_webservice/";
	private static String URL = "http://192.168.1.68:7001/HelloWebService/HelloWSService?WSDL";
	private static final String METHOD_NAME = "hello";
	private static final String SOAP_ACTION = "http://hello_webservice/hello";

	public String main() {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		PropertyInfo propInfo = new PropertyInfo();
		propInfo.name = "arg0";
		propInfo.type = PropertyInfo.STRING_CLASS;

		request.addProperty(propInfo, "John Smith");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);

			SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope
					.getResponse();

			String str = resultsRequestSOAP.toString();
			return str;

		} catch (Exception e) {

		}

		return "NAPAKA";

	}
}*/

/*private SoapObject request(String method) {
SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
soapSerializationEnvelope.dotNet = false;

SoapObject soapObject = new SoapObject(NAMESPACE, method); 
soapSerializationEnvelope.setOutputSoapObject(soapObject);

try {
    HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
    httpTransportSE.call(NAMESPACE + method, soapSerializationEnvelope);
    SoapObject objectResult = (SoapObject) soapSerializationEnvelope.getResponse();
    return objectResult;
} catch (Exception exception) {
    exception.printStackTrace();
}

return null;
}*/