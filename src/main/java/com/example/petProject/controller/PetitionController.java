package com.example.petProject.controller;
import com.example.petProject.model.User;
import com.example.petProject.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/petitions")
public class PetitionController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/{petitionId}/vote")
    public ResponseEntity<?> addVote(@PathVariable Long petitionId, @AuthenticationPrincipal User user) {
        voteService.addVote(user, petitionId);
        return ResponseEntity.ok("Vote added");
    }

    @DeleteMapping("/{petitionId}/vote")
    public ResponseEntity<?> removeVote(@PathVariable Long petitionId, @AuthenticationPrincipal User user) {
        voteService.removeVote(user, petitionId);
        return ResponseEntity.ok("Vote removed");
    }
}