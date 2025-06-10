package cz.uhk.pro2_e.service;

import cz.uhk.pro2_e.model.Band;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BandService {

    List<Band> getAllBands();
    void saveBand(Band band);
    Band getBand(long id);
    void deleteBand(long id);
}
