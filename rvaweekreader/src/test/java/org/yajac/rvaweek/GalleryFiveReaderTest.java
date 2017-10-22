package org.yajac.rvaweek;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;

public class GalleryFiveReaderTest extends ReaderTest {

    @Test
    public void testEvents() throws ParseException {
        GalleryFiveReader reader = new GalleryFiveReader();
        int events = reader.handleRequest(null, context);
        Assert.assertTrue(events > 0);
    }

}
