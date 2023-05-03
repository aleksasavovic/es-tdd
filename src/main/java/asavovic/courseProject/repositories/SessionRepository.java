package asavovic.courseProject.repositories;

import asavovic.courseProject.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

}
