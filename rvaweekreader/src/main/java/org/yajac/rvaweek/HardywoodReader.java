package org.yajac.rvaweek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.jsoup.nodes.Element;
import org.yajac.rvaweek.aws.ScheduledEvent;
import org.yajac.rvaweek.cache.RVACacheWriter;
import org.yajac.rvaweek.model.Event;
import org.yajac.rvaweek.web.WebReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class HardywoodReader extends WebReader {

	private static String URL = "https://hardywood.com";
	private static String LOCATION_NAME = "Hardywood Brewery";
	private static String SELECT_CLASS = ".listed-event";
	private static String CATEGORY = "Beer";
	private static String ID_NAME = "Hardywood";

	public static void main(String[] args) {
		HardywoodReader rVAReader = new HardywoodReader();
		try {
			Set<Event> events = rVAReader.readEventsPage(URL + "/hwevents/taproom-events", SELECT_CLASS);
			RVACacheWriter.insertEvents(events);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public int handle(ScheduledEvent input, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
			Set<Event> events = readEventsPage(URL + "/hwevents/taproom-events", SELECT_CLASS);
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
			event.setLocation(LOCATION_NAME + " " + element.getElementsByClass("listed-event-location").text());
			event.setLocationURL(URL);
			event.setCategory(CATEGORY);
			event.setUrl(element.getElementsByTag("a").attr("href"));
			event.setName(element.getElementsByTag("h1").text());
			final String date = element.getElementsByTag("h3").get(0).text();
			event.setDate(formatDate(date));
			event.setTime(element.getElementsByTag("h3").get(1).text());
			event.setImage(element.getElementsByTag("img").attr("src"));
			event.setId(ID_NAME + date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return event;
	}

	private Date formatDate(String date) throws ParseException {
		SimpleDateFormat parser = new SimpleDateFormat("MMM dd, yyyy");
		return parser.parse(date);
	}

}
