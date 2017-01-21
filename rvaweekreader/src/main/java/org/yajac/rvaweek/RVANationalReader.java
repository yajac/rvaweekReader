package org.yajac.rvaweek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.jsoup.nodes.Element;

public class RVANationalReader extends RVAReader {

	private static String URL = "http://www.thenationalva.com";
	private static String LOCATION_NAME = "The National";
	private static String SELECT_CLASS = ".entry";
	private static String CATEGORY = "Music";

	public static void main(String[] args) {
		RVANationalReader rVAReader = new RVANationalReader();
		try {
			Set<Event> events = rVAReader.readWordpressEvents(URL + "/events/all", SELECT_CLASS);
			rVAReader.insertEvents(events);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}


	protected Event handleEvent(Element element) {
		Event event = null;
		try {
			event = new Event();
			event.setUrl(element.getElementsByTag("a").attr("href"));
			event.setName(element.getElementsByTag("h3").get(0).getElementsByTag("a").text());
			event.setSecondaryName(element.getElementsByTag("h4").text());
			String date = element.getElementsByClass("date").text();
			event.setTime(element.getElementsByClass("time").text());
			event.setImage(element.getElementsByTag("img").attr("src"));
			SimpleDateFormat parser = new SimpleDateFormat("EEE, MMM dd, yyyy");
			Date fullDate = parser.parse(date);
			event.setDate(fullDate);
			event.setLocation(LOCATION_NAME);
			event.setLocationURL(URL);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return event;
	}

}
