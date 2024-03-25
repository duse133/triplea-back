package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Incheon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncheonRepository extends JpaRepository<Incheon, String> {
}
