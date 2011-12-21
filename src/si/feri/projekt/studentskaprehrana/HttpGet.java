package si.feri.projekt.studentskaprehrana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class HttpGet {
    public static final String SITE = "http://localhost:1500/";
    
    public static String readUrl(String url) {
        try {
            URL u = new URL(SITE + url);
            BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
            String str;
            String all = "";
            while ((str = in.readLine()) != null) {
                all += str;
            }
            in.close();
            return all;
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return null;
    }
    
    private static String providers() {
        return readUrl("providers");
    }
}
