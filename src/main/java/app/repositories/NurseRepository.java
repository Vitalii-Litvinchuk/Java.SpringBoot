package app.repositories;

import app.entities.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer> {
    List<Nurse> findByPhone(String phone);
}

