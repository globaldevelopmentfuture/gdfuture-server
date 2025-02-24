package org.example.gdfutureserver.offers.service;


import org.example.gdfutureserver.offers.dto.OfferRequestDTO;
import org.example.gdfutureserver.offers.dto.OfferResponseDTO;

public interface OfferCommandService {
    OfferResponseDTO createOffer(OfferRequestDTO dto);
    OfferResponseDTO updateOffer(Long id, OfferRequestDTO dto);
    void deleteOffer(Long id);
}
