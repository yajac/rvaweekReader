package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class CarytownReaderTest extends ReaderTest {

    @Test
    public void testEvents() throws ParseException {
        CarytownReader reader = new CarytownReader();
        int events = reader.handleRequest(null, context);
        Assert.assertTrue(events > 0);
    }

}
