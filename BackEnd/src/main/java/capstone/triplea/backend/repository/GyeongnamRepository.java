package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Gyeongnam;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GyeongnamRepository extends JpaRepository<Gyeongnam, String> {
    List<Gyeongnam> findByWeight(int weight);
}
