package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class BroadberryReaderTest extends ReaderTest {

    @Test
    public void testRequest() throws ParseException {
        RVABroadberryReader reader = new RVABroadberryReader();
        int events = reader.handle(null, context);
        Assert.assertTrue(events > 0);
    }


}
