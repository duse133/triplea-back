package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Jeju;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JejuRepository extends JpaRepository<Jeju,String> {
    List<Jeju> findByWeight(int weight);
}
