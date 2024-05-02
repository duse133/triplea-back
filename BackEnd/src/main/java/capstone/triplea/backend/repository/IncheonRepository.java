package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Incheon;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncheonRepository extends JpaRepository<Incheon, String> {
    List<Incheon> findByWeight(int weight);
}
