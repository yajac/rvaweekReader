package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class HardywoodReaderTest extends ReaderTest{

	@Test
	public void testEvents() throws ParseException {
		HardywoodReader reader = new HardywoodReader();
		int events = reader.handle(null, context);
        Assert.assertTrue(events > 0 );
    }

}
