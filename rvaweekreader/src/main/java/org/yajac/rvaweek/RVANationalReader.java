package org.yajac.rvaweek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.yajac.rvaweek.aws.ScheduledEvent;
import org.yajac.rvaweek.cache.RVACacheWriter;
import org.yajac.rvaweek.facebook.FacebookAPIReader;
import org.yajac.rvaweek.model.Event;
import org.yajac.rvaweek.model.Source;

import java.util.Set;

public class RVANationalReader extends FacebookAPIReader implements RequestHandler<ScheduledEvent, Integer> {

	private static String SOURCE_URL = "http://www.thenationalva.com";
	private static String LOCATION_NAME = "The National";
	private static String CATEGORY = "Music";

	public static String FACEBOOK_ID = "40197037867";


	public Integer handleRequest(ScheduledEvent request, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
			logger.log("Getting Events");
			Set<Event> events = readEvents(FACEBOOK_ID);
			logger.log("Got Events: " + events.size());
			RVACacheWriter.insertEvents(events);
			logger.log("Inserted Events: " + events.size());
			return events.size();
		} catch (Exception e) {
			logger.log("Error with Reader" + e);
			e.printStackTrace();
		}
		return 0;
	}

	protected Event filterEvent(Event event) {
		return event;
	}

	@Override
	protected Source getSource() {
		Source source = new Source();
		source.setName(LOCATION_NAME);
		source.setCategory(CATEGORY);
		source.setUrl(SOURCE_URL);
		return source;
	}

}
