package in.sih26006.freight.dto;

import jakarta.validation.constraints.*;

public record ForecastRequest(@NotBlank
        String origin, @NotBlank
        String destination, @NotBlank
        String cargoType, @Positive
        double quantityTonnes, @NotBlank
        String preferredVessel, @Min(1)
        @Max(24)
        int contractMonths) {

}
