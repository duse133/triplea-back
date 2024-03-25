package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Ulsan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UlsanRepository extends JpaRepository<Ulsan ,String> {
}
