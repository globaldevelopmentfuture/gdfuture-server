package org.example.gdfutureserver.offers.service;

import org.example.gdfutureserver.offers.dto.OfferResponseDTO;

import java.util.List;

public interface OfferQueryService {
    OfferResponseDTO getOffer(Long id);
    List<OfferResponseDTO> getAllOffers();
}
