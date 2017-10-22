package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class ScottsAdditionReaderTest extends ReaderTest {

    @Test
    public void testEvents() throws ParseException {
        ScottsAdditionReader reader = new ScottsAdditionReader();
        int events = reader.handleRequest(null, context);
        Assert.assertTrue(events > 0);
    }

}
