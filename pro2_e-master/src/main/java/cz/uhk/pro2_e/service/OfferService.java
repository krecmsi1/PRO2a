package cz.uhk.pro2_e.service;

import cz.uhk.pro2_e.model.Offer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OfferService {

    List<Offer> getAllOffers();
    void saveOffer(Offer offer);
    Offer getOffer(long id);
    void deleteOffer(long id);
}
