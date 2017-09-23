package org.yajac.rvaweek;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.yajac.rvaweek.aws.ScheduledEvent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class GardenGroveReader extends RVAReader {

	private static String URL = "http://gardengrovebrewing.com";
	private static String LOCATION_NAME = "Garden Grove Brewing Co.";
	private static String SELECT_CLASS = ".simcal-event-details";
	private static String CATEGORY = "Beer";
	private static String ID_NAME = "GardenGrove";

	public static void main(String[] args) {
		GardenGroveReader rVAReader = new GardenGroveReader();
		try {
			Set<Event> events = rVAReader.readWordpressEvents(URL + "/all-events/events-calendar/", SELECT_CLASS);
			RVACacheWriter.insertEvents(events);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public int handle(ScheduledEvent request, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
			Set<Event> events = readWordpressEvents(URL + "/all-events/events-calendar/", SELECT_CLASS);
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
		event = new Event();
		event.setLocation(LOCATION_NAME + " " + element.getElementsByClass("listed-event-location").text());
		event.setLocationURL(URL);
		event.setCategory(CATEGORY);
		String startTime = element.getElementsByClass("simcal-event-start-time").attr("data-event-start");
		Date date = Date.from(Instant.ofEpochSecond(Long.valueOf(startTime)));
		event.setUrl(element.getElementsByTag("a").attr("href"));
		event.setName(element.getElementsByTag("a").text());
		event.setSecondaryName(element.getElementsByClass("simcal-event-title").text());
		event.setDate(date);
		event.setTime(element.getElementsByClass("simcal-event-start-time").text());
		event.setImage(
				"http://gardengrovebrewing.com/wp-content/themes/gardengrovebrewing/images/logo-garden-grove-brewing.png");
		event.setId(ID_NAME + date);
		return event;
	}

}
