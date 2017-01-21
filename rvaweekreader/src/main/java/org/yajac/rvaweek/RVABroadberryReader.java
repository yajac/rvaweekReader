package org.yajac.rvaweek;

import java.util.Set;

import javax.xml.datatype.DatatypeFactory;

import org.jsoup.nodes.Element;

public class RVABroadberryReader extends RVAReader {

	private static final String CLOSED_EVENT = "Closed for a Private Event";
	private static String URL = "http://www.thebroadberry.com";
	private static String LOCATION_NAME = "The BroadBerry";
	private static String SELECT_CLASS = ".list-view-item";
	private static String CATEGORY = "Music";

	public static void main(String[] args) {
		RVABroadberryReader rVAReader = new RVABroadberryReader();
		try {
			Set<Event> events = rVAReader.readWordpressEvents(URL + "/calendar", SELECT_CLASS);
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
			if (event.getName().equals(CLOSED_EVENT)) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}

}
