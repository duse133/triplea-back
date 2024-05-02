package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Seoul;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeoulRepository extends JpaRepository<Seoul, String> {
    List<Seoul> findByWeight(int weight);
}
