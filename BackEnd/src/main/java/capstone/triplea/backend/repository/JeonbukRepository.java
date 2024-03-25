package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Jeonbuk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JeonbukRepository extends JpaRepository<Jeonbuk, String> {
}
