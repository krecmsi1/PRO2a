package cz.uhk.pro2_e.repository;

import cz.uhk.pro2_e.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {

}
