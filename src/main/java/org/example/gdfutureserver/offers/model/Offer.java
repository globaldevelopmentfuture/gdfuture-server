package org.example.gdfutureserver.offers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer{

    @Id
    @SequenceGenerator(name = "offer_sequence", sequenceName = "offer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_sequence")

    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private OfferType type;

    @Column(nullable = false)
    private Double startingPrice;

    @Column(length = 100)
    private String gradient;

    @Column(length = 50)
    private String icon;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "offers_features", joinColumns = @JoinColumn(name = "offer_id"))
    @Column(name = "feature")
    private List<String> features;
}
