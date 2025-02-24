package org.example.gdfutureserver.offers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OfferType {
    DIGITAL_MARKETING, IT_SOLUTIONS;

    @JsonCreator
    public static OfferType fromString(String value) {
        for (OfferType type : OfferType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid OfferType: " + value);
    }

    @JsonValue
    public String toJson() {
        return this.name();
    }
}
