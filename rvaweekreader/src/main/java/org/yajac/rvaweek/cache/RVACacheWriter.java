package org.yajac.rvaweek.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.yajac.rvaweek.model.Event;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

public class RVACacheWriter extends CacheManager {


	public static void insertEvents(Set<Event> events) throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		for (Event event : events) {
			try {
				String date = getDateString(event.getDate());
				String eventId = event.getId();
				String eventJson = mapper.writeValueAsString(event);
				System.out.println("Event: " + eventId + " : " + date);
				setCache(date, eventId, eventJson);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getDateString(Date date) {
		SimpleDateFormat parser = new SimpleDateFormat("MMddYYYY");
		parser.setTimeZone(TimeZone.getTimeZone("EST"));
		return parser.format(date);
	}

}
