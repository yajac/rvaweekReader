package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;
import org.yajac.rvaweek.model.Event;

import java.text.ParseException;
import java.util.Set;

public class RVANationalReaderTest extends ReaderTest{

	@Test
	public void testRequest() throws ParseException {
		RVANationalReader reader = new RVANationalReader();
		int events = reader.handleRequest(null, context);
		Assert.assertTrue(events > 0);
	}

	@Test
	public void testEvents() throws ParseException {
		RVANationalReader reader = new RVANationalReader();
		Set<Event> events = reader.readEvents(RVANationalReader.FACEBOOK_ID);
		Assert.assertNotNull(events);
	}

}
