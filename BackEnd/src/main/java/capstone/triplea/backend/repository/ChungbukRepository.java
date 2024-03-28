package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Chungbuk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChungbukRepository extends JpaRepository<Chungbuk, String> {
}
