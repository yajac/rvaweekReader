package org.yajac.rvaweek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
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
            logger.log("Getting Events");
            Set<Event> events = readEvents(URL);
            logger.log("Got Events: " + events.size());
            RVACacheWriter.insertEvents(events);
            logger.log("Inserted Events: " + events.size());
            return events.size();
        } catch (Exception e) {
            logger.log("Error with Reader: " + e);
            e.printStackTrace();
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


}
