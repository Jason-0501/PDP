package com.example.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.policy.model.AllOf;
import com.example.policy.model.AllOf;
import com.example.policy.repository.AllOfRepository;

public class AllOfService {
	@Autowired
	private AllOfRepository allOfRepository;
	
	public List<AllOf> getAllAllOfs(){
		return allOfRepository.findAll();
	}
	public Optional<AllOf> getAllOfById(Long id) {
	      return allOfRepository.findById(id);
	  }
	public AllOf createAllOf(AllOf AllOf){
		return allOfRepository.save(AllOf);
	}
	
	public Optional<AllOf> updateAllOf(Long id, AllOf AllOf) {
		 return allOfRepository.findById(id)
	                .map(existingAllOf -> {
	                    existingAllOf.setMatches(AllOf.getMatches());
	                    return allOfRepository.save(existingAllOf);
	                });
	}
	public void deleteAllOf(Long id) {
		 allOfRepository.deleteById(id);
	}
}
