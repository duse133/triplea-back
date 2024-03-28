package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Daegu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaeguRepository extends JpaRepository<Daegu, String>{

}
