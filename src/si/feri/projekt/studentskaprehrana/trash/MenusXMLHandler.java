package si.feri.projekt.studentskaprehrana.trash;

//Parsanje xmlja je zamenjano s spletno storitvijo.

import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import si.feri.projekt.studentskaprehrana.Menu;

@Deprecated
public class MenusXMLHandler extends DefaultHandler {
    private boolean currentElement;
    private String currentValue;
    public Vector<Menu> list = new Vector<Menu>();
    private Menu menu;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
    	try {
	        currentElement = true;
	        if (localName.equals("food.Menu")) {
	            menu = new Menu();
	        }
    	} catch (Exception ex) {
    		System.out.println("aaaa2 " + ex);
    	}
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) {
    	try {
	        currentElement = false;
	        if (localName.equals("restaurantHash")) {
	            menu.setRestaurantHash(currentValue);
	        } else if (localName.equals("contents")) {
	            menu.setContents(currentValue);
	        } else if (localName.equals("vegetarian")) {
	            menu.setVegetarian(currentValue);
	        } else if (localName.equals("food.Menu")) {
	        	if (menu != null) list.add(menu);
	        }
    	} catch (Exception ex) {
    		System.out.println("bbbbb2 " + ex);
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
