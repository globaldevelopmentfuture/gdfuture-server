package org.example.gdfutureserver.offers.mapper;

import org.example.gdfutureserver.offers.dto.OfferRequestDTO;
import org.example.gdfutureserver.offers.dto.OfferResponseDTO;
import org.example.gdfutureserver.offers.model.Offer;

public interface OfferMapper {
    Offer toEntity(OfferRequestDTO dto);
    OfferResponseDTO toDTO(Offer entity);
}
