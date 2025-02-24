package org.example.gdfutureserver.offers.web;


import jakarta.validation.Valid;
import org.example.gdfutureserver.offers.dto.OfferRequestDTO;
import org.example.gdfutureserver.offers.dto.OfferResponseDTO;
import org.example.gdfutureserver.offers.service.OfferCommandService;
import org.example.gdfutureserver.offers.service.OfferQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gdfuture/server/api/offers")
public class OfferController {

    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

    private final OfferCommandService offerCommandService;
    private final OfferQueryService offerQueryService;

    public OfferController(OfferCommandService offerCommandService, OfferQueryService offerQueryService) {
        this.offerCommandService = offerCommandService;
        this.offerQueryService = offerQueryService;
    }

    @PostMapping("/create")
    public ResponseEntity<OfferResponseDTO> createOffer(@Valid @RequestBody OfferRequestDTO dto) {
        try {
            OfferResponseDTO response = offerCommandService.createOffer(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error creating offer", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<OfferResponseDTO> updateOffer(@PathVariable Long offerId,
                                                        @Valid @RequestBody OfferRequestDTO dto) {
        try {
            OfferResponseDTO response = offerCommandService.updateOffer(offerId, dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error updating offer with id: {}", offerId, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long offerId) {
        try {
            offerCommandService.deleteOffer(offerId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting offer with id: {}", offerId, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<OfferResponseDTO> getOffer(@PathVariable Long offerId) {
        try {
            OfferResponseDTO response = offerQueryService.getOffer(offerId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error retrieving offer with id: {}", offerId, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OfferResponseDTO>> getAllOffers() {
        try {
            List<OfferResponseDTO> responseList = offerQueryService.getAllOffers();
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            logger.error("Error retrieving all offers", e);
            return ResponseEntity.badRequest().build();
        }
    }
}

