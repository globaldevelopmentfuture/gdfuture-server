package org.example.gdfutureserver.offers.dto;

import lombok.Data;
import org.example.gdfutureserver.offers.model.OfferType;

import java.util.List;

@Data
public class OfferResponseDTO {
    private Long id;
    private String name;
    private String description;
    private OfferType type;
    private Double startingPrice;
    private String gradient;
    private String icon;
    private List<String> features;
}

