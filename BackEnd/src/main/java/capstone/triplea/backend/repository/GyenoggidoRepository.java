package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Gyeonggido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GyenoggidoRepository extends JpaRepository<Gyeonggido, String> {
}
