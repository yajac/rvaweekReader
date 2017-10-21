package org.yajac.rvaweek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.databind.JsonNode;
import org.yajac.rvaweek.aws.ScheduledEvent;
import org.yajac.rvaweek.cache.RVACacheWriter;
import org.yajac.rvaweek.facebook.FacebookAPIReader;
import org.yajac.rvaweek.model.Event;

import java.util.Set;

public class StirCrazyReader extends FacebookAPIReader {

    private static String URL = "https://graph.facebook.com/v2.10/stircrazyrva";
    private static String LOCATION_NAME = "Stir Crazy Cafe";
    private static String CATEGORY = "Coffee";
    private static String ID_NAME = "StirCrazy";


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


    @Override
    protected Event handleEvent(JsonNode json) {
        return null;
    }
}
