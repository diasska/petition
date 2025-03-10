package com.example.petProject.repository;

import com.example.petProject.model.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
 public interface PetitionRepository extends JpaRepository<Petition, Long> {


}
