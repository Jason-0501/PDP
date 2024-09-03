package com.example.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.policy.model.Subject;
import com.example.policy.repository.SubjectRepository;


@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;
	
	public List<Subject> getAllSubjects(){
		return subjectRepository.findAll();
	}
	public Optional<Subject> getSubjectById(Long id) {
	      return subjectRepository.findById(id);
	  }
	public Subject createSubject(Subject subject){
		return subjectRepository.save(subject);
	}
	
	public Optional<Subject> updateSubject(Long id, Subject subject) {
		 return subjectRepository.findById(id)
	                .map(existingSubject -> {
	                    existingSubject.setRole(subject.getRole());
	                    existingSubject.setLast_action_time(subject.getLast_action_time());
	                    existingSubject.setLast_action_type(subject.getLast_action_type());
	                    return subjectRepository.save(existingSubject);
	                });
	}
	public void deleteSubject(Long id) {
		 subjectRepository.deleteById(id);
	}
}
