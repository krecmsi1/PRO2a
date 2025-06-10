package cz.uhk.pro2_e.service;

import cz.uhk.pro2_e.model.Offer;
import cz.uhk.pro2_e.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public void saveOffer(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public Offer getOffer(long id) {
        return offerRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOffer(long id) {
        offerRepository.deleteById(id);
    }
}
