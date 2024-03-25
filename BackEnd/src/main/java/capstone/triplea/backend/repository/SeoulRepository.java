package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Seoul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeoulRepository extends JpaRepository<Seoul, String> {
}
