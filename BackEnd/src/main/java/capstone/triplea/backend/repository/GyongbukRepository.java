package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.Gyongbuk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GyongbukRepository extends JpaRepository<Gyongbuk,String> {
}
