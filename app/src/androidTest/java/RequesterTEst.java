import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ilearning.tasks.ilearningweatherapp.util.WeatherRequester;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class RequesterTEst {
        @Test
        public void testGetCityInfo() throws InterruptedException, TimeoutException, ExecutionException, JSONException {
            WeatherRequester r = new WeatherRequester();
            Assert.assertNotNull(r.getWeather("28580"));
        }



}
