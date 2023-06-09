package com.cydeo.client;

import com.cydeo.dto.weather.WeatherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(url = "http://api.weatherstack.com", name="WEATHER-CLIENT")
public interface WeatherApiClient {

    /**
     * This method consumes an API that asks headers.
     * To send the header use the @RequestParam(value = "")
     * annotation
     * base url => http://api.weatherstack.com
     * endpoint => /current
     * parameters => ?access_key = your access key&query = your city
     */
    @GetMapping("/current")
    WeatherDTO getCurrentWeather(@RequestParam(value = "access_key") String accessKey,
                                 @RequestParam(value ="query") String city);
}
