package capstone.triplea.backend.repository;

import capstone.triplea.backend.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer > {
    List<NoticeBoard> findByTitle(String title);
}
