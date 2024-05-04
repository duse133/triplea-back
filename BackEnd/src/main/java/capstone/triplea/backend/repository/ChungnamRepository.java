package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Chungnam;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChungnamRepository extends JpaRepository<Chungnam, String> {
    List<Chungnam> findByPriority(int weight);
}
