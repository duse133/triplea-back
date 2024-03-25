package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Busan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusanRepository extends JpaRepository<Busan, String>{
}
