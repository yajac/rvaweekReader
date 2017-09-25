package org.yajac.rvaweek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.databind.JsonNode;
import org.yajac.rvaweek.aws.ScheduledEvent;
import org.yajac.rvaweek.cache.RVACacheWriter;
import org.yajac.rvaweek.facebook.FacebookAPIReader;
import org.yajac.rvaweek.model.Event;

import java.util.Set;

public class ThreeNotchedReader extends FacebookAPIReader {

    private static String URL = "https://graph.facebook.com/v2.10/threenotchdrva";
    private static String LOCATION_NAME = "Three Notchd RVA Collab House";
    private static String CATEGORY = "Beer";
    private static String ID_NAME = "3Notchd";


    public int handle(ScheduledEvent request, Context context) {
        LambdaLogger logger = context.getLogger();
        try {
            Set<Event> events = readEvents(URL);
            RVACacheWriter.insertEvents(events);
            logger.log("Events: " + events.size());
            return events.size();
        } catch (Exception e) {
            logger.log(e.getMessage());
        }
        return 0;
    }

    protected Event handleEvent(final JsonNode jsonNode) {
        final String eventId = jsonNode.get("id").textValue();
        final String dateString = jsonNode.get("start_time").textValue();
        final String location = jsonNode.get("place").get("location").get("city").textValue();
        Event event = new Event();
        event.setId(ID_NAME + dateString);
        event.setLocation(LOCATION_NAME);
        event.setLocationURL(URL);
        event.setCategory(CATEGORY);
        event.setName(jsonNode.get("name").textValue());
        event.setImage(getPicture(eventId));
        event.setUrl("https://www.facebook.com/events/" + eventId);
        event.setDate(formatDate(dateString));
        if (!"RICHMOND".equalsIgnoreCase(location)) {
            return null;
        }
        return event;
    }

}
