package org.example.gdfutureserver.offers.repo;


import org.example.gdfutureserver.offers.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepo extends JpaRepository<Offer, Long> {
}

