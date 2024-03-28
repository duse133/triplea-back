package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Gwangju;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GwangjuRepository extends JpaRepository<Gwangju,String> {
}
