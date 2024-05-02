package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Gwangju;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GwangjuRepository extends JpaRepository<Gwangju,String> {
    List<Gwangju> findByWeight(int weight);
}
