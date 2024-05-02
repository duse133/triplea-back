package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Gyongbuk;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GyongbukRepository extends JpaRepository<Gyongbuk,String> {
    List<Gyongbuk> findByWeight(int weight);
}
