package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class StirCrazyReaderTest extends ReaderTest {

    @Test
    public void testEvents() throws ParseException {
        StirCrazyReader reader = new StirCrazyReader();
        int events = reader.handleRequest(null, context);
        Assert.assertTrue(events > 0);
    }

}
