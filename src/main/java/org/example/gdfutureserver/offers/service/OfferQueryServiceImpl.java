package org.example.gdfutureserver.offers.service;



import org.example.gdfutureserver.offers.dto.OfferResponseDTO;
import org.example.gdfutureserver.offers.mapper.OfferMapper;
import org.example.gdfutureserver.offers.model.Offer;
import org.example.gdfutureserver.offers.repo.OfferRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferQueryServiceImpl implements OfferQueryService {

    private static final Logger logger = LoggerFactory.getLogger(OfferQueryServiceImpl.class);

    private final OfferRepo offerRepository;
    private final OfferMapper offerMapper;

    public OfferQueryServiceImpl(OfferRepo offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    @Override
    public OfferResponseDTO getOffer(Long id) {
        logger.info("Retrieving offer with id: {}", id);
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));
        return offerMapper.toDTO(offer);
    }

    @Override
    public List<OfferResponseDTO> getAllOffers() {
        logger.info("Retrieving all offers");
        return offerRepository.findAll()
                .stream()
                .map(offerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
