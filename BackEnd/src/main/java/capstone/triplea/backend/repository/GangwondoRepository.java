package capstone.triplea.backend.repository;


import capstone.triplea.backend.entity.Gangwondo;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GangwondoRepository extends JpaRepository<Gangwondo, String> {
    List<Gangwondo> findByWeight(int weight);
}
