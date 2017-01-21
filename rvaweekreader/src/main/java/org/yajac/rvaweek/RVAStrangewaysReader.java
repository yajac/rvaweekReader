package org.yajac.rvaweek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.jsoup.nodes.Element;

public class RVAStrangewaysReader extends RVAReader {

	private static String URL = "http://strangewaysbrewing.com";
	private static String LOCATION_NAME = "Strangeways Brewery";
	private static String SELECT_CLASS = "article";
	private static String CATEGORY = "Beer";

	public static void main(String[] args) {
		RVAStrangewaysReader rVAReader = new RVAStrangewaysReader();
		try {
			Set<Event> events = rVAReader.readWordpressEvents(URL + "/happenings", SELECT_CLASS);
			rVAReader.insertEvents(events);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	protected Event handleEvent(Element element) {
		Event event = null;
		try {
			event = new Event();
			event.setLocation(LOCATION_NAME);
			event.setLocationURL(URL);
			event.setCategory(CATEGORY);
			event.setUrl(element.getElementsByTag("a").attr("href"));
			event.setName(element.getElementsByTag("a").text());
			event.setDate(formatDate(element.getElementsByTag("time").attr("datetime")));
			event.setImage(element.getElementsByTag("img").attr("src"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return event;
	}

	private Date formatDate(String date) throws ParseException {
		SimpleDateFormat parser = new SimpleDateFormat("YYYY-MM-dd");
		return parser.parse(date);
	}

}
