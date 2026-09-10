package in.sih26006.freight.service;

import in.sih26006.freight.dto.*;
import in.sih26006.freight.entity.*;
import in.sih26006.freight.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class DecisionService {

    private final PortRepository ports;
    private final VesselRepository vessels;
    private final CargoRequestRepository cargo;

    public DecisionService(PortRepository p, VesselRepository v, CargoRequestRepository c) {
        ports = p;
        vessels = v;
        cargo = c;
    }

    @Transactional
    public ForecastResponse forecast(ForecastRequest r) {
        Port port = ports.findByNameIgnoreCase(normalizePort(r.destination())).orElseThrow(() -> new IllegalArgumentException("Destination port is unavailable."));
        Vessel vessel = vessels.findByVesselTypeIgnoreCase(r.preferredVessel()).orElseThrow(() -> new IllegalArgumentException("Vessel information unavailable."));
        cargo.save(new CargoRequest(r.origin(), r.destination(), r.cargoType(), r.quantityTonnes(), r.preferredVessel(), r.contractMonths()));
        boolean cap = vessel.getCapacityTonnes() >= r.quantityTonnes(), draft = vessel.getDraft() <= port.getMaxDraft(), loa = vessel.getLoa() <= port.getMaxLoa(), beam = vessel.getBeam() <= port.getMaxBeam();
        boolean suitable = cap && draft && loa && beam;
        String reason = !cap ? "Cargo quantity exceeds vessel sample capacity." : !draft ? "Vessel draft exceeds the port's sample limit." : !loa ? "Vessel LOA exceeds the port's sample limit." : !beam ? "Vessel beam exceeds the port's sample limit." : "Cargo capacity and all port-dimension checks pass.";
        int risk = 25 + ("Moderate".equalsIgnoreCase(port.getCongestionStatus()) ? 12 : 5) + (suitable ? 0 : 28) + (r.contractMonths() >= 6 ? 7 : 0);
        String level = risk <= 30 ? "LOW" : risk <= 60 ? "MEDIUM" : "HIGH";
        double current = 24800;
        double predicted = Math.round((current * (1 + .032 * r.contractMonths()) + (suitable ? -250 : 550)) * 100.0) / 100.0;
        List<String> factors = new ArrayList<>(List.of("Freight volatility: Medium", "Port congestion: " + port.getCongestionStatus(), "Demand uncertainty: Medium", "Vessel compatibility: " + (suitable ? "Low" : "High")));
        String recommendation = suitable ? "Evaluate a short-term charter during the next 2–3 weeks, while monitoring freight volatility." : "Review vessel selection or port constraints before entering the charter market.";
        String trend = predicted > current ? "Increasing" : "Stable";
        String marketEntryWindow = suitable ? "Next 2–3 weeks" : "After vessel/port review";
        ForecastResponse.Compatibility compatibility = new ForecastResponse.Compatibility(
                suitable ? "SUITABLE" : "NOT SUITABLE", draft, loa, beam, cap, reason);
        ForecastResponse.Risk riskAssessment = new ForecastResponse.Risk(risk, level, factors);
        List<Double> history = List.of(21800d, 22400d, 22100d, 22900d, 23500d, 24800d);
        List<Double> forecast = List.of(
                current,
                Math.round(current * 1.032) * 1.0,
                Math.round(current * 1.064) * 1.0,
                predicted);

        return new ForecastResponse(
                current, predicted, trend, "Prototype indicator — not an accuracy guarantee",
                marketEntryWindow, compatibility, riskAssessment, recommendation, history, forecast);
    }

    private String normalizePort(String name) {
        return name.equalsIgnoreCase("Visakhapatnam") || name.equalsIgnoreCase("Vizag") ? "Visakhapatnam" : name;
    }
}
