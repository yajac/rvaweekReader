package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;
import org.yajac.rvaweek.model.Event;

import java.text.ParseException;
import java.util.Set;

public class VassenReaderTest extends ReaderTest {

    @Test
    public void testRequest() throws ParseException {
        VassenReader reader = new VassenReader();
        int events = reader.handleRequest(null, context);
        Assert.assertTrue(events > 0);
    }

    @Test
    public void testEvents() throws ParseException {
        VassenReader reader = new VassenReader();
        Set<Event> events = reader.readEvents(VassenReader.FACEBOOK_ID);
        Assert.assertNotNull(events);
    }

}
