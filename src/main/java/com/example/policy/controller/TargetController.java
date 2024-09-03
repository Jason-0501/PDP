package com.example.policy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.policy.model.Target;
import com.example.policy.service.TargetService;

@RestController
@RequestMapping("targets")
public class TargetController {

	@Autowired
	private TargetService targetService;
	
	@GetMapping
	public List<Target> getAllTargets(){
		return targetService.getAllTargets();
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Target> getUserById(@PathVariable Long id) {
		Optional<Target> TargetOptional = targetService.getTargetById(id);
		return TargetOptional.map(Target -> ResponseEntity.ok(Target))
                .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	 @PostMapping
	 @ResponseStatus(HttpStatus.CREATED)
	 public Target createTarget(@RequestBody Target Target) {
	      return targetService.createTarget(Target);
	 }
	 
	 @PutMapping("/{id}")
	 public ResponseEntity<Target> updateTarget(@PathVariable Long id, @RequestBody Target Target) {
		 Optional<Target> updatedTarget = targetService.updateTarget(id, Target);
		 return updatedTarget.map(updated -> ResponseEntity.ok(updated))
                 .orElseGet(() -> ResponseEntity.notFound().build());
	 }
	 
	 @DeleteMapping("/{id}")
	 public void deleteTarget(@PathVariable Long id) {
		 targetService.deleteTarget(id);

	 }
	 
}