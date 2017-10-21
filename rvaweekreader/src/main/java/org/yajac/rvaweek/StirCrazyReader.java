package org.yajac.rvaweek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.yajac.rvaweek.aws.ScheduledEvent;
import org.yajac.rvaweek.cache.RVACacheWriter;
import org.yajac.rvaweek.facebook.FacebookAPIReaderv2;
import org.yajac.rvaweek.model.Event;
import org.yajac.rvaweek.model.Source;

import java.util.Set;

public class StirCrazyReader extends FacebookAPIReaderv2 {

    private static final String SOURCE_URL = "https://www.facebook.com/pg/stircrazyrva";
    private static final String LOCATION_NAME = "Stir Crazy Cafe";
    private static final String CATEGORY = "Coffee";
    private static final String FACEBOOK_ID = "38712615842";


    public int handle(ScheduledEvent request, Context context) {
        LambdaLogger logger = context.getLogger();
        try {
            Set<Event> events = readEvents(FACEBOOK_ID);
            RVACacheWriter.insertEvents(events);
            logger.log("Events: " + events.size());
            return events.size();
        } catch (Exception e) {
            logger.log(e.getMessage());
        }
        return 0;
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
