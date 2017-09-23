package org.yajac.rvaweek;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class RVAStrangewaysReaderTest {

	@Test
	public void testDate() throws ParseException {
		RVAStrangewaysReader reader = new RVAStrangewaysReader();
		String dateString = "2018-08-14";
		Date date = reader.formatDate(dateString);
		Assert.assertTrue(date.after(Calendar.getInstance().getTime()));
	}

}
