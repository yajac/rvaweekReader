package org.yajac.rvaweek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.yajac.rvaweek.aws.ScheduledEvent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class RVAStrangewaysReader extends RVAReader {

	private static String URL = "http://strangewaysbrewing.com";
	private static String LOCATION_NAME = "Strangeways Brewery";
	private static String SELECT_CLASS = "article";
	private static String CATEGORY = "Beer";
	private static String ID_NAME = "Strangeways";

	public static void main(String[] args) {
		RVAStrangewaysReader rVAReader = new RVAStrangewaysReader();
		try {
			Set<Event> events = rVAReader.readWordpressEvents(URL + "/happenings", SELECT_CLASS);
			System.out.println("Size: " + events.size());
			RVACacheWriter.insertEvents(events);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public int handle(ScheduledEvent request, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
			Set<Event> events = readWordpressEvents(URL + "/happenings", SELECT_CLASS);
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
			event.setLocation(LOCATION_NAME);
			event.setLocationURL(URL);
			event.setCategory(CATEGORY);
			event.setUrl(element.getElementsByTag("a").attr("href"));
			event.setName(element.getElementsByTag("a").text());
			final String dateString = element.getElementsByTag("time").attr("datetime");
			final Date date = formatDate(dateString);
			event.setDate(date);
			event.setImage(element.getElementsByTag("img").attr("src"));
			event.setId(ID_NAME + dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return event;
	}

	public Date formatDate(String date) throws ParseException {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		return parser.parse(date);
	}

}