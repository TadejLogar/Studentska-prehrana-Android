package si.feri.projekt.studentskaprehrana.trash;

// Parsanje xmlja je zamenjano s spletno storitvijo.


import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import si.feri.projekt.studentskaprehrana.Settings;
import si.feri.projekt.studentskaprehrana.db.MenusDB;
import si.feri.projekt.studentskaprehrana.db.ProvidersDB;

@Deprecated
public class Testing {
	public static void insertProviders(ProvidersDB db) {
		db.open();
		if (!db.dataExists()) {
			if (!Settings.startLoadingProviders) {
				Settings.startLoadingProviders = true;
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
		
					ProvidersXMLHandler myXMLHandler = new ProvidersXMLHandler();
					xr.setContentHandler(myXMLHandler);
					try {
						xr.parse("http://hizd03gybg.no-ip.info/providers.xml");
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SAXException e) {
						e.printStackTrace();
					}
					
					db.insertProviders(myXMLHandler.list);
				} catch (Exception e) {
					System.out.println("XML Pasing5 Excpetion = " + e);
				}
			}
		}
		
		db.close();
	}
	
	public static void insertMenus(MenusDB db) {
		db.open();
		if (!db.dataExists()) {
			if (!Settings.startLoadingMenus) {
				Settings.startLoadingMenus = true;
				try {
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					XMLReader xr = sp.getXMLReader();
		
					MenusXMLHandler myXMLHandler = new MenusXMLHandler();
					xr.setContentHandler(myXMLHandler);
					
					try {
						xr.parse("http://hizd03gybg.no-ip.info/menu.xml");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					db.insertMenus(myXMLHandler.list);
				} catch (Exception e) {
					System.out.println("XML Pasing5 Excpetion = " + e);
				}
			}
		}
		db.close();
	}
}
