package org.yajac.rvaweek;

import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.jsoup.nodes.Element;

public class RVACamelReader extends RVAReader {

	private static String URL = "http://www.thecamel.org";
	private static String LOCATION_NAME = "The Camel";
	private static String SELECT_CLASS = ".list-view-item";
	private static String CATEGORY = "Music";


	public static void main(String[] args) {
		RVACamelReader rVAReader = new RVACamelReader();
		try {
			Set<Event> events = rVAReader.readWordpressEvents(URL, SELECT_CLASS);
			rVAReader.insertEvents(events);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	protected Event handleEvent(Element element) {
		Event event = null;
		try {
			event = new Event();
			event.setUrl(URL + element.getElementsByTag("a").attr("href"));
			event.setImage("http:" + element.getElementsByTag("img").attr("src"));
			event.setPrice(element.getElementsByClass("price-range").text());
			event.setTime(element.getElementsByClass("dtstart").text());
			event.setName(element.getElementsByClass("headliners").text());
			event.setSecondaryName(element.getElementsByClass("supports").text());
			String date = element.getElementsByClass("value-title").attr("title");
			event.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(date).toGregorianCalendar().getTime());
			event.setLocation(LOCATION_NAME);
			event.setLocationURL(URL);
			event.setCategory(CATEGORY);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return event;
	}

}
