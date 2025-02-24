package org.example.gdfutureserver.offers.service;

import org.example.gdfutureserver.offers.dto.OfferRequestDTO;
import org.example.gdfutureserver.offers.dto.OfferResponseDTO;
import org.example.gdfutureserver.offers.mapper.OfferMapper;
import org.example.gdfutureserver.offers.model.Offer;
import org.example.gdfutureserver.offers.model.OfferType;
import org.example.gdfutureserver.offers.repo.OfferRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OfferCommandServiceImpl implements OfferCommandService {

    private static final Logger logger = LoggerFactory.getLogger(OfferCommandServiceImpl.class);

    private final OfferRepo offerRepository;
    private final OfferMapper offerMapper;

    public OfferCommandServiceImpl(OfferRepo offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    @Override
    @Transactional
    public OfferResponseDTO createOffer(OfferRequestDTO dto) {
        logger.info("Creating offer with name: {}", dto.getName());
        Offer offer = offerMapper.toEntity(dto);
        offer.setGradient(assignGradient());
        Offer savedOffer = offerRepository.save(offer);
        logger.info("Offer created with id: {}", savedOffer.getId());
        return offerMapper.toDTO(savedOffer);
    }

    @Override
    @Transactional
    public OfferResponseDTO updateOffer(Long id, OfferRequestDTO dto) {
        logger.info("Updating offer with id: {}", id);
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));
        offer.setName(dto.getName());
        offer.setDescription(dto.getDescription());
        offer.setType(OfferType.valueOf(dto.getType().toUpperCase()));
        offer.setStartingPrice(dto.getStartingPrice());
        offer.setFeatures(dto.getFeatures());
        offer.setIcon(dto.getIcon());
        offer.setGradient(assignGradient());
        Offer updatedOffer = offerRepository.save(offer);
        logger.info("Offer updated with id: {}", updatedOffer.getId());
        return offerMapper.toDTO(updatedOffer);
    }

    @Override
    @Transactional
    public void deleteOffer(Long id) {
        logger.info("Deleting offer with id: {}", id);
        offerRepository.deleteById(id);
        logger.info("Offer deleted with id: {}", id);
    }

    private static final List<String> GRADIENTS = List.of(
            "from-blue-500 to-blue-600",
            "from-emerald-500 to-emerald-600",
            "from-purple-500 to-purple-600",
            "from-red-500 to-red-600",
            "from-yellow-500 to-yellow-600",
            "from-indigo-500 to-indigo-600",
            "from-pink-500 to-pink-600",
            "from-teal-500 to-teal-600",
            "from-cyan-500 to-cyan-600",
            "from-orange-500 to-orange-600",
            "from-green-500 to-green-600",
            "from-rose-500 to-rose-600",
            "from-fuchsia-500 to-fuchsia-600",
            "from-sky-500 to-sky-600",
            "from-lime-500 to-lime-600",
            "from-amber-500 to-amber-600",
            "from-gray-500 to-gray-600",
            "from-slate-500 to-slate-600",
            "from-violet-500 to-violet-600"
    );

    private static int lastIndex = -1;

    private synchronized String assignGradient() {
        if (GRADIENTS.size() == 1) {
            return GRADIENTS.get(0);
        }
        int index = ThreadLocalRandom.current().nextInt(GRADIENTS.size());
        if (index == lastIndex) {
            index = (index + 1) % GRADIENTS.size();
        }
        lastIndex = index;
        return GRADIENTS.get(index);
    }
}
