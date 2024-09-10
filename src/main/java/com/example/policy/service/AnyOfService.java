package com.example.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.policy.model.AnyOf;
import com.example.policy.repository.AnyOfRepository;

public class AnyOfService {
	@Autowired
	private AnyOfRepository anyOfRepository;
	
	public List<AnyOf> getAllAnyOfs(){
		return anyOfRepository.findAll();
	}
	public Optional<AnyOf> getAnyOfById(Long id) {
	      return anyOfRepository.findById(id);
	  }
	public AnyOf createAnyOf(AnyOf AnyOf){
		return anyOfRepository.save(AnyOf);
	}
	
	public Optional<AnyOf> updateAnyOf(Long id, AnyOf AnyOf) {
		 return anyOfRepository.findById(id)
	                .map(existingAnyOf -> {
	                    existingAnyOf.setAllOfs(AnyOf.getAllOfs());
	                    return anyOfRepository.save(existingAnyOf);
	                });
	}
	public void deleteAnyOf(Long id) {
		 anyOfRepository.deleteById(id);
	}
}
