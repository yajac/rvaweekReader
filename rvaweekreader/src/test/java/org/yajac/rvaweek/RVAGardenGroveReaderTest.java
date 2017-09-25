package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class RVAGardenGroveReaderTest extends ReaderTest{

	@Test
	public void testEvents() throws ParseException {
		GardenGroveReader reader = new GardenGroveReader();
		int events = reader.handle(null, context);
        Assert.assertTrue(events > 0 );
    }

}
