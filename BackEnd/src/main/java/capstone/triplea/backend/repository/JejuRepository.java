package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Jeju;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JejuRepository extends JpaRepository<Jeju,String> {
}
