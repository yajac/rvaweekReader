package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class ThreeNotchedReaderTest extends ReaderTest{

	@Test
	public void testEvents() throws ParseException {
		ThreeNotchedReader reader = new ThreeNotchedReader();
		int events = reader.handle(null, context);
        Assert.assertTrue(events > 0 );
    }

	@Test
	public void testImages() throws ParseException {
		ThreeNotchedReader reader = new ThreeNotchedReader();
		String image = reader.getPicture("1280433812085638");
		Assert.assertNotNull(image);
	}

}
