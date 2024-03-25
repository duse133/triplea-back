package capstone.triplea.backend.repository;


import capstone.triplea.backend.entity.Gangwondo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GangwondoRepository extends JpaRepository<Gangwondo, String> {
}
