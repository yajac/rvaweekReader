package org.yajac.rvaweek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.yajac.rvaweek.aws.ScheduledEvent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class RVANationalReader extends RVAReader {

	private static String URL = "http://www.thenationalva.com";
	private static String LOCATION_NAME = "The National";
	private static String SELECT_CLASS = ".entry";
	private static String CATEGORY = "Music";
	private static String ID_NAME = "National";

	public static void main(String[] args) {
		RVANationalReader rVAReader = new RVANationalReader();
		try {
			Set<Event> events = rVAReader.readWordpressEvents(URL + "/events/all", SELECT_CLASS);
			RVACacheWriter.insertEvents(events);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public int handle(ScheduledEvent request, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
			Set<Event> events = readWordpressEvents(URL + "/events/all", SELECT_CLASS);
			RVACacheWriter.insertEvents(events);
			logger.log("Events: " + events.size());
			return events.size();
		} catch (Exception e) {
			logger.log(e.getMessage());
		}
		return 0;
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
			event.setCategory(CATEGORY);
			event.setId(ID_NAME + date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return event;
	}

}