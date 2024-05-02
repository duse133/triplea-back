package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Daegu;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaeguRepository extends JpaRepository<Daegu, String>{
    List<Daegu> findByWeight(int weight);
}
