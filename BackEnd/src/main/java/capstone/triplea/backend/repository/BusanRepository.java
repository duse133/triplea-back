package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Busan;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusanRepository extends JpaRepository<Busan, String>{
    List<Busan> findByWeight(int weight);
}
