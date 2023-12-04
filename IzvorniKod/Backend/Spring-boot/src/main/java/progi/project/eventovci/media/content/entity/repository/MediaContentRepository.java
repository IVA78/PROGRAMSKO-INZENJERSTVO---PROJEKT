package progi.project.eventovci.media.content.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import progi.project.eventovci.media.content.entity.MediaContent;

import java.util.List;
@Repository
public interface MediaContentRepository extends JpaRepository<MediaContent, Long> {

    @Query("SELECT m FROM MediaContent m WHERE m.eventid = :id")
    List<MediaContent> first(@Param("id") Long id);

    MediaContent getFirstByEventid(Long eventid);

}
