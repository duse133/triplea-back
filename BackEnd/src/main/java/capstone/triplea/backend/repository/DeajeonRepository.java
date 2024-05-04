package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Deajeon;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeajeonRepository extends JpaRepository<Deajeon, String> {
    List<Deajeon> findByPriority(int weight);
}
