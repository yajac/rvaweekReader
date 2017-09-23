package org.yajac.rvaweek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReaderTest {

    @Mock
    Context context;

    private static LambdaLogger logger = new LambdaLogger() {
        public void log(String string) {
            System.out.print(string);
        }
    };

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Assert.assertTrue(System.getenv("redisServer") != null);
        Mockito.when(context.getLogger()).thenReturn(logger);

    }


}
