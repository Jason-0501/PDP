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


import com.example.policy.model.Subject;
import com.example.policy.service.SubjectService;






@RestController
@RequestMapping("subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@GetMapping
	public List<Subject> getAllSubjects(){
		return subjectService.getAllSubjects();
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Subject> getUserById(@PathVariable Long id) {
		Optional<Subject> subjectOptional = subjectService.getSubjectById(id);
		return subjectOptional.map(subject -> ResponseEntity.ok(subject))
                .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	 @PostMapping
	 @ResponseStatus(HttpStatus.CREATED)
	 public Subject createSubject(@RequestBody Subject subject) {
	      return subjectService.createSubject(subject);
	 }
	 
	 @PutMapping("/{id}")
	 public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
		 Optional<Subject> updatedSubject = subjectService.updateSubject(id, subject);
		 return updatedSubject.map(updated -> ResponseEntity.ok(updated))
                 .orElseGet(() -> ResponseEntity.notFound().build());
	 }
	 
	 @DeleteMapping("/{id}")
	 public void deleteSubject(@PathVariable Long id) {
		 subjectService.deleteSubject(id);

	 }
	 
	
}
