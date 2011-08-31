import com.naver.WebSearch;
import java.io.InputStream;
import java.net.URL;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/*
 * Main.java
 *
 * Created on June 13, 2006, 4:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author superwtk
 */
public class Main {
	
	/** Creates a new instance of Main */
	public Main() {
	}
	
	public static void main(String[] args) {
		try {
			WebSearch parser = new WebSearch("c2c4edb8c7e2db5192141d5743a45fb8", "webkr", "web", 15, 1);
			
			parser.parse();
			WebSearch.Record[] list = parser.getRecords();
			int count = parser.getRecordCount();
			
			System.out.println(parser.channel.getDescription());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
