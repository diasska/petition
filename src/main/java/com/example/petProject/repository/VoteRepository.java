package com.example.petProject.repository;

import com.example.petProject.model.Petition;
import com.example.petProject.model.User;
import com.example.petProject.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository  extends JpaRepository<Vote, Integer> {

    Vote findByUserAndPetition(User user, Petition petition);


}
