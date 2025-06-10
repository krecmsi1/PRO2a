package cz.uhk.pro2_e.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bands")
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // FK sloupec v DB
    private User creator;

    private String name;
    private String genre;
    private String description;
    private String lookFor;

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getGenre() {
        return genre;
    }
    public String getDescription() {
        return description;
    }
    public String getLookFor() {
        return lookFor;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setLookFor(String lookFor) {
        this.lookFor = lookFor;
    }
}
