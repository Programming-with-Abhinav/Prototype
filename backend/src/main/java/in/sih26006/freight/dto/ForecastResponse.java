package in.sih26006.freight.dto;

import java.util.List;

public record ForecastResponse(double currentFreight, double predictedFreight, String trend, String confidence, String marketEntryWindow, Compatibility compatibility, Risk risk, String recommendation, List<Double> history, List<Double> forecast) {

    public record Compatibility(String overall, boolean draftPass, boolean loaPass, boolean beamPass, boolean capacityPass, String reason) {
    }

    public record Risk(int score, String level, List<String> factors) {
    }
}
