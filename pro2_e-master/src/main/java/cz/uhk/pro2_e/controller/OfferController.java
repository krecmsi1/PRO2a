package cz.uhk.pro2_e.controller;

import cz.uhk.pro2_e.model.Offer;
import cz.uhk.pro2_e.service.OfferService;
import cz.uhk.pro2_e.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;
    private final BandService bandService;

    @Autowired
    public OfferController(OfferService offerService, BandService bandService) {
        this.offerService = offerService;
        this.bandService = bandService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("offers", offerService.getAllOffers());
        return "offers_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("offer", offerService.getOffer(id));
        return "offers_detail";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("offer", new Offer());
        model.addAttribute("bands", bandService.getAllBands());
        return "offers_add";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("offer", offerService.getOffer(id));
        model.addAttribute("bands", bandService.getAllBands());
        return "offers_add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Offer offer) {
        offerService.saveOffer(offer);
        return "redirect:/offers/";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("offer", offerService.getOffer(id));
        return "offers_delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id) {
        offerService.deleteOffer(id);
        return "redirect:/offers/";
    }

}
