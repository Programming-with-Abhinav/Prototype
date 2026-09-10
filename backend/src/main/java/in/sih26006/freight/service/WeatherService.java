package in.sih26006.freight.service;

import in.sih26006.freight.entity.Port;
import in.sih26006.freight.repository.PortRepository;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WeatherService {

    private final PortRepository ports;
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    private final Map<String, Cache> cache = new ConcurrentHashMap<>();

    record Cache(String body, long until) {
    }

    public WeatherService(PortRepository p) {
        ports = p;
    }

    public String current(String portName) {
        Port p = ports.findByNameIgnoreCase(portName).orElseThrow(() -> new IllegalArgumentException("Destination port is unavailable."));
        String key = p.getName();
        Cache old = cache.get(key);
        if (old != null && old.until > System.currentTimeMillis()) {
            return old.body;
        
        }
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + p.getLatitude() + "&longitude=" + p.getLongitude() + "&current=temperature_2m,wind_speed_10m,weather_code&timezone=Asia%2FKolkata";
            HttpResponse<String> response = client.send(HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(8)).GET().build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IllegalStateException("Weather provider returned HTTP " + response.statusCode());
            }
            String body = response.body();
            cache.put(key, new Cache(body, System.currentTimeMillis() + 600000));
            return body;
        } catch (Exception e) {
            return "{\"source\":\"unavailable\",\"message\":\"Live weather service is temporarily unavailable.\"}";
        }
    }
}
