package com.example.policy.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.policy.model.AllOf;
import com.example.policy.model.Match;
import com.example.policy.service.AllOfService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("allof")
public class AllOfController {
	
	@Autowired	
	private AllOfService allOfService;
	
	@PostMapping
	public ResponseEntity<AllOf> createAllOf(@RequestBody Map<String,Integer> requestMap){
		AllOf allOf = allOfService.createAllOf(requestMap);
		return (ResponseEntity<AllOf>) ResponseEntity.status(HttpStatus.CREATED).body(allOf);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAllOf(@PathVariable Long id){
		Optional<AllOf> allof = allOfService.getAllOfById(id);
		if (allof.isPresent()) {
//			if(allof.get().getMatches())
			allOfService.deleteAllOf(id);
			return ResponseEntity.noContent().build();
		}else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found
	    }
		
	}
	
}
