package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class RVAStrangewaysReaderTest extends ReaderTest{

	@Test
	public void testDate() throws ParseException {
		RVAStrangewaysReader reader = new RVAStrangewaysReader();
		String dateString = "2018-08-14";
		Date date = reader.formatDate(dateString);
		Assert.assertTrue(date.after(Calendar.getInstance().getTime()));
	}

	@Test
	public void testEvents() throws ParseException {
		RVAStrangewaysReader reader = new RVAStrangewaysReader();
		int events = reader.handle(null, context);
		Assert.assertTrue(events > 0 );
	}
}
