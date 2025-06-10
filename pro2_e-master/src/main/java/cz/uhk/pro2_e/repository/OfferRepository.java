package cz.uhk.pro2_e.repository;

import cz.uhk.pro2_e.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

}
