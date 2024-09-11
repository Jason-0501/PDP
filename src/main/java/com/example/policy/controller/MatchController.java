package com.example.policy.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.policy.model.AllOf;
import com.example.policy.model.Match;
import com.example.policy.service.AllOfService;
import com.example.policy.service.MatchService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("matches")
public class MatchController {
	
	@Autowired
	private MatchService matchService;
	
	@PostMapping
	public ResponseEntity<Match> createMatch(@RequestBody Map<String,Object> requestMap){
		try {
            Match match = matchService.createMatch(requestMap);
            return ResponseEntity.status(HttpStatus.CREATED).body(match);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
	}
	@PutMapping("/{id}")
	public ResponseEntity<Match> updateMatch(@PathVariable Long id,@RequestBody Map<String,Object> requestMap){
		Optional<Match> match = matchService.updateMatch(id,requestMap);
		if(match.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(match.get());
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMatch(@PathVariable Long id){
		Optional<Match> match = matchService.getMatchById(id);
		System.out.println("matchID"+match.get().getId());
		if (match.isPresent()) {
			matchService.deleteMatch(id);
			return ResponseEntity.noContent().build();
		}else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found
	    }
		
	}
	
}
