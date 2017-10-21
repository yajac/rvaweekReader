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

public class RVAStrangewaysReader extends WebReader {

	private static String URL = "http://strangewaysbrewing.com";
	private static String LOCATION_NAME = "Strangeways Brewery";
	private static String SELECT_CLASS = "article";
	private static String CATEGORY = "Beer";
	private static String ID_NAME = "Strangeways";

	public int handle(ScheduledEvent request, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
			Set<Event> events = readEventsPage(URL + "/happening-type/rva-happenings/", SELECT_CLASS);
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
