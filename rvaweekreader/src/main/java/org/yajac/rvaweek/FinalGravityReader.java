package org.yajac.rvaweek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.databind.JsonNode;
import org.yajac.rvaweek.aws.ScheduledEvent;
import org.yajac.rvaweek.cache.RVACacheWriter;
import org.yajac.rvaweek.facebook.FacebookAPIReader;
import org.yajac.rvaweek.model.Event;
import org.yajac.rvaweek.model.EventLocation;

import java.util.Set;

public class FinalGravityReader extends FacebookAPIReader {

    private static String URL = "https://graph.facebook.com/v2.10/FinalGravityBrewingCo";
    private static String LOCATION_NAME = "Final Gravity Brewing Co";
    private static String CATEGORY = "Beer";
    private static String ID_NAME = "FinalGravity";


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

    protected Event getBaseEvent(final String idBase) {
        Event event = new Event();
        event.setId(ID_NAME + idBase);
        event.setLocation(LOCATION_NAME);
        event.setLocationURL(URL);
        event.setCategory(CATEGORY);
        return event;
    }

    private EventLocation getEventLocation(JsonNode locationNode) {
        EventLocation eventLocation = new EventLocation();
        // event
        return eventLocation;
    }

}
