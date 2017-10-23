package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class CamelReaderTest extends ReaderTest {

    @Test
    public void testRequest() throws ParseException {
        RVACamelReader reader = new RVACamelReader();
        int events = reader.handle(null, context);
        Assert.assertTrue(events > 0);
    }

}
