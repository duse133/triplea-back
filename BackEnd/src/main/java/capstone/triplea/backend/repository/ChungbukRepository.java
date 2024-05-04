package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Chungbuk;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChungbukRepository extends JpaRepository<Chungbuk, String> {
    List<Chungbuk> findByPriority(int weight);
}
