package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class FinalGravityReaderTest extends ReaderTest {

    @Test
    public void testEvents() throws ParseException {
        FinalGravityReader reader = new FinalGravityReader();
        int events = reader.handle(null, context);
        Assert.assertTrue(events > 0);
    }

}
