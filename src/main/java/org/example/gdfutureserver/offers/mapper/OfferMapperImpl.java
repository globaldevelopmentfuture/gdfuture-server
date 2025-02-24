package org.example.gdfutureserver.offers.mapper;


import org.example.gdfutureserver.offers.dto.OfferRequestDTO;
import org.example.gdfutureserver.offers.dto.OfferResponseDTO;
import org.example.gdfutureserver.offers.model.Offer;
import org.example.gdfutureserver.offers.model.OfferType;
import org.springframework.stereotype.Component;

@Component
public class OfferMapperImpl implements OfferMapper {

    public Offer toEntity(OfferRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Offer offer = new Offer();
        offer.setName(dto.getName());
        offer.setDescription(dto.getDescription());
        offer.setType(OfferType.valueOf(dto.getType().toUpperCase()));
        offer.setStartingPrice(dto.getStartingPrice());
        offer.setIcon(dto.getIcon());
        offer.setFeatures(dto.getFeatures());
        return offer;
    }

    public OfferResponseDTO toDTO(Offer offer) {
        if (offer == null) {
            return null;
        }
        OfferResponseDTO dto = new OfferResponseDTO();
        dto.setId(offer.getId());
        dto.setName(offer.getName());
        dto.setDescription(offer.getDescription());
        dto.setType(offer.getType());
        dto.setStartingPrice(offer.getStartingPrice());
        dto.setIcon(offer.getIcon());
        dto.setGradient(offer.getGradient());
        dto.setFeatures(offer.getFeatures());
        return dto;
    }
}
