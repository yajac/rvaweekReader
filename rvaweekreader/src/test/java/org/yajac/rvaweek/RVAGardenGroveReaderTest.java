package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;
import org.yajac.rvaweek.model.Event;

import java.text.ParseException;
import java.util.Set;

public class RVAGardenGroveReaderTest extends ReaderTest{

	@Test
	public void testRequest() throws ParseException {
		GardenGroveReader reader = new GardenGroveReader();
		int events = reader.handleRequest(null, context);
		Assert.assertTrue(events > 20);
	}

	@Test
	public void testEvents() throws ParseException {
		GardenGroveReader reader = new GardenGroveReader();
		Set<Event> events = reader.readEvents(GardenGroveReader.FACEBOOK_ID);
		Assert.assertNotNull(events);
	}
}
