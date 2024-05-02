package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Gyeonggido;
import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GyenoggidoRepository extends JpaRepository<Gyeonggido, String> {
    List<Gyeonggido> findByWeight(int weight);
}
