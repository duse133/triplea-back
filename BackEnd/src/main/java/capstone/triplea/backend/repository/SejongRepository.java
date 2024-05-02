package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Sejong;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SejongRepository extends JpaRepository<Sejong,String> {
    List<Sejong> findByWeight(int weight);
}
