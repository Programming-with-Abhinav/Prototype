package in.sih26006.freight.controller;

import in.sih26006.freight.dto.*;
import in.sih26006.freight.entity.*;
import in.sih26006.freight.repository.*;
import in.sih26006.freight.service.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    private final PortRepository ports;
    private final VesselRepository vessels;
    private final CargoRequestRepository cargo;
    private final DecisionService decision;
    private final WeatherService weather;

    public ApiController(PortRepository p, VesselRepository v, CargoRequestRepository c, DecisionService d, WeatherService w) {
        ports = p;
        vessels = v;
        cargo = c;
        decision = d;
        weather = w;
    }

    @GetMapping("/health")
    Map<String, String> health() {
        return Map.of("status", "UP", "mode", "prototype");
    }

    @GetMapping("/ports")
    List<Port> ports() {
        return ports.findAll();
    }

    @GetMapping("/vessels")
    List<Vessel> vessels() {
        return vessels.findAll();
    }

    @GetMapping("/cargo")
    Page<CargoRequest> cargo(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return cargo.findAll(PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 50), Sort.by("id").descending()));
    }

    @PostMapping("/forecast")
    @ResponseStatus(HttpStatus.CREATED)
    ForecastResponse forecast(@Valid @RequestBody ForecastRequest request) {
        return decision.forecast(request);
    }

    @GetMapping(value = "/weather/{port}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> weather(@PathVariable String port) {
        return ResponseEntity.ok(weather.current(port));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
}
