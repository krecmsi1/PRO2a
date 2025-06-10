package cz.uhk.pro2_e.service;

import cz.uhk.pro2_e.model.Band;
import cz.uhk.pro2_e.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import cz.uhk.pro2_e.model.User;

import java.util.List;

@Service
public class BandServiceImpl implements BandService {

    private final BandRepository bandRepository;
    private final UserService userService;

    @Autowired
    public BandServiceImpl(BandRepository bandRepository, UserService userService) {
        this.bandRepository = bandRepository;
        this.userService = userService;
    }

    @Override
    public List<Band> getAllBands() {
        return bandRepository.findAll();
    }

    @Override
    public void saveBand(Band band) {
        User thisUser = getCurrentUser();
        if(thisUser != null){
            band.setCreator(thisUser);
        }
        bandRepository.save(band);
    }

    @Override
    public Band getBand(long id) {
        return bandRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBand(long id) {
        bandRepository.deleteById(id);
    }


    public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
        return null;
    }
    String username = authentication.getName();
    return userService.findByUsername(username);  // userService má metodu findByUsername()
}
}
