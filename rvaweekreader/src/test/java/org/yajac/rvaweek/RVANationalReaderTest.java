package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class RVANationalReaderTest extends ReaderTest{

	@Test
	public void testEvents() throws ParseException {
		RVANationalReader reader = new RVANationalReader();
		int events = reader.handle(null, context);
        Assert.assertTrue(events > 0 );
    }

}
