package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Jeonbuk;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JeonbukRepository extends JpaRepository<Jeonbuk, String> {
    List<Jeonbuk> findByPriority(int weight);
}
