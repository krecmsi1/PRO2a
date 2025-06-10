package cz.uhk.pro2_e.controller;

import cz.uhk.pro2_e.model.Band;
import cz.uhk.pro2_e.service.BandService;
import cz.uhk.pro2_e.model.User;
import cz.uhk.pro2_e.service.UserService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bands")
public class BandController {

    private BandService bandService;
    private UserService userService;

    @Autowired
    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("bands", bandService.getAllBands());
        return "bands_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable long id) {
        model.addAttribute("band", bandService.getBand(id));
        return "bands_detail";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("band", new Band());
        return "bands_add";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("band", bandService.getBand(id));
        return "bands_add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Band band) {
        bandService.saveBand(band);
        return "redirect:/bands/";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable long id) {
        model.addAttribute("band", bandService.getBand(id));
        return "bands_delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable long id) {
        bandService.deleteBand(id);
        return "redirect:/bands/";
    }

}
