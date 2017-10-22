package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class AltriaTheaterReaderTest extends ReaderTest {

    @Test
    public void testEvents() throws ParseException {
        AltriaTheaterReader reader = new AltriaTheaterReader();
        int events = reader.handleRequest(null, context);
        Assert.assertTrue(events > 0);
    }

}
