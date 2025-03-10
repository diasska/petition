package com.example.petProject.service;

import com.example.petProject.model.Petition;
import com.example.petProject.model.User;
import com.example.petProject.model.Vote;
import com.example.petProject.repository.PetitionRepository;
import com.example.petProject.repository.UserRepository;
import com.example.petProject.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetitionRepository petitionRepository;


    public void addVote(User user, Long petitionId){
        Petition petition = petitionRepository.findById(petitionId).orElseThrow(() -> new UsernameNotFoundException("Petition not found"));

        Vote existingVote =new Vote();
        existingVote.setUser(user);
        existingVote.setPetition(petition);
        voteRepository.save(existingVote);
        petition.setVotesCount(petition.getVotesCount()+1);
        petitionRepository.save(petition);
    }

    public void removeVote(User user, Long petitionId) {
        Petition petition = petitionRepository.findById(petitionId)
                .orElseThrow(() -> new UsernameNotFoundException("Petition not found"));

        Vote existingVote = voteRepository.findByUserAndPetition(user, petition);
        if (existingVote == null) {
            throw new RuntimeException("User has not voted for this petition");
        }
        voteRepository.delete(existingVote);
        petition.setVotesCount(petition.getVotesCount() - 1);
        petitionRepository.save(petition);
    }
}
