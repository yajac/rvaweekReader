package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;
import org.yajac.rvaweek.model.Event;

import java.text.ParseException;
import java.util.Set;

public class FinalGravityReaderTest extends ReaderTest {

    @Test
    public void testRequest() throws ParseException {
        FinalGravityReader reader = new FinalGravityReader();
        int events = reader.handleRequest(null, context);
        Assert.assertTrue(events > 0);
    }

    @Test
    public void testEvents() throws ParseException {
        FinalGravityReader reader = new FinalGravityReader();
        Set<Event> events = reader.readEvents(FinalGravityReader.FACEBOOK_ID);
        Assert.assertNotNull(events);
    }
}
