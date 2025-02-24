package org.example.gdfutureserver.offers.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OfferRequestDTO {

    @NotBlank(message = "Offer name is required")
    private String name;

    private String description;

    @NotBlank(message = "Offer type is required")
    private String type;

    @NotNull(message = "Starting price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Starting price must be non-negative")
    private Double startingPrice;

    @NotBlank(message = "Icon is required")
    private String icon;
    private List<String> features;
}
