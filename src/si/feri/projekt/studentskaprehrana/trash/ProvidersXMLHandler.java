package si.feri.projekt.studentskaprehrana.trash;

//Parsanje xmlja je zamenjano s spletno storitvijo.


import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import si.feri.projekt.studentskaprehrana.Provider;

@Deprecated
public class ProvidersXMLHandler extends DefaultHandler {
    private boolean currentElement;
    private String currentValue;
    public Vector<Provider> list = new Vector<Provider>();
    private Provider provider;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
    	try {
	        currentElement = true;
	        if (localName.equals("food.Provider")) {
	            provider = new Provider();
	        }
    	} catch (Exception ex) {
    		System.out.println("aaaa " + ex);
    	}
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) {
    	try {
	        currentElement = false;
	        if (localName.equals("hash")) {
	            provider.setHash(currentValue);
	        } else if (localName.equals("name")) {
	            provider.setName(currentValue);
	        } else if (localName.equals("address")) {
	            provider.setAddress(currentValue);
	        } else if (localName.equals("post")) {
	            provider.setPost(currentValue);
	        } else if (localName.equals("country")) {
	            provider.setCountry(currentValue);
	        } else if (localName.equals("price")) {
	            provider.setPrice(currentValue);
	        } else if (localName.equals("openWorkdayFrom")) {
	            provider.setOpenWorkdayFrom(currentValue);
	        } else if (localName.equals("openWorkdayTo")) {
	            provider.setOpenWorkdayTo(currentValue);
	        } else if (localName.equals("openSaturdayFrom")) {
	            provider.setOpenSaturdayFrom(currentValue);
	        } else if (localName.equals("openSaturdayTo")) {
	            provider.setOpenSaturdayTo(currentValue);
	        } else if (localName.equals("openSundayFrom")) {
	            provider.setOpenSundayFrom(currentValue);
	        } else if (localName.equals("openSundayTo")) {
	            provider.setOpenSundayTo(currentValue);
	        } else if (localName.equals("locationLatitude")) {
	            provider.setLocationLatitude(currentValue);
	        } else if (localName.equals("locationLongitude")) {
	            provider.setLocationLongitude(currentValue);
	        } else if (localName.equals("food.Provider")) {
	        	if (provider != null) list.add(provider);
	        }
    	} catch (Exception ex) {
    		System.out.println("bbbbb " + ex);
    	}
    }
    
    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentElement) {
            currentValue = new String(ch, start, length);
            currentElement = false;
        }
    }

}
