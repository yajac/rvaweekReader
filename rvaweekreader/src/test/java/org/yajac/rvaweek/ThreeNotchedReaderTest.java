package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;
import org.yajac.rvaweek.model.Event;

import java.text.ParseException;
import java.util.Set;

public class ThreeNotchedReaderTest extends ReaderTest{

	@Test
	public void testRequest() throws ParseException {
		ThreeNotchedReader reader = new ThreeNotchedReader();
		int events = reader.handleRequest(null, context);
		Assert.assertTrue(events > 0);
	}

	@Test
	public void testEvents() throws ParseException {
		ThreeNotchedReader reader = new ThreeNotchedReader();
		Set<Event> events = reader.readEvents(ThreeNotchedReader.FACEBOOK_ID);
		boolean allRichmond = true;
		for (Event event : events) {
			if (!event.getEventLocation().getName().equals(ThreeNotchedReader.LOCATION_NAME)) {
				allRichmond = false;
			}
		}

		Assert.assertTrue(allRichmond);
	}

	@Test
	public void testImages() throws ParseException {
		ThreeNotchedReader reader = new ThreeNotchedReader();
		String image = reader.getPicture("1280433812085638");
		Assert.assertNotNull(image);
	}

}
