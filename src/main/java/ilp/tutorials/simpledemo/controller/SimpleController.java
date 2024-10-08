package ilp.tutorials.simpledemo.controller;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import ilp.tutorials.simpledemo.data.LngLatPairRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class SimpleController {

    @GetMapping({"/isAlive", "/isalive"})
    public boolean isAlive() {
        return true;
    }

    @GetMapping("/studentId/{name}")
    public String studentId(@PathVariable String name) {
        if (name.equalsIgnoreCase("Cameron")) {
            return "Cameron";
        }
        else {
            return "We do not have your name in our register, sorry!";
        }
    }

    @PostMapping("/sample")
    public String sample(@RequestBody LngLatPairRequest req, HttpServletResponse response) {
        if (req == null || req.getPosition1() == null || req.getPosition2() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Invalid parameters passed in";
        }
        return "Got: " +
                "Lng1: " + req.getPosition1().getLng() + " " +
                "Lat1: " + req.getPosition1().getLat() + " " +
                "Lng2: " + req.getPosition2().getLng() + " " +
                "Lat2: " + req.getPosition2().getLat();
    }

    @PostMapping("/sample2")
    public String sample2(@RequestHeader Map<String, String> headers, @RequestBody String body, HttpServletResponse response) throws IOException {
        if (body == null || body.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Invalid parameters passed in";
        }

        LngLatPairRequest req = new ObjectMapper().readValue(body, LngLatPairRequest.class);
        if (req == null || req.getPosition1() == null || req.getPosition2() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Invalid parameters passed in";
        }

        return "Got: " +
                "Lng1: " + req.getPosition1().getLng() + " " +
                "Lat1: " + req.getPosition1().getLat() + " " +
                "Lng2: " + req.getPosition2().getLng() + " " +
                "Lat2: " + req.getPosition2().getLat();
    }
    
    @PostMapping("/dumprequest")
    public String dumprequest(@RequestHeader Map<String, String> headers, @RequestBody String body) {
        StringBuilder result = new StringBuilder();
        headers.forEach((k, v) -> result.append(String.format("%s=%s\n", k, v)));
        result.append("\n");
        result.append(body);
        return result.toString();
    }
}
