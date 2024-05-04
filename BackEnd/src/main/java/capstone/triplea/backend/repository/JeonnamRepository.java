package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Jeonnam;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JeonnamRepository extends JpaRepository<Jeonnam, String> {
    List<Jeonnam> findByPriority(int weight);
}
