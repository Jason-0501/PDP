package com.example.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.policy.model.Target;
import com.example.policy.repository.TargetRepository;

@Service
public class TargetService {

	@Autowired
	private TargetRepository targetRepository;
	
	public List<Target> getAllTargets(){
		return targetRepository.findAll();
	}
	public Optional<Target> getTargetById(Long id) {
	      return targetRepository.findById(id);
	  }
	public Target createTarget(Target target){
		return targetRepository.save(target);
	}
	
	public Optional<Target> updateTarget(Long id, Target target) {
		 return targetRepository.findById(id)
	                .map(existingtarget -> {
	                    existingtarget.setAnyOf(target.getAnyOf());
	                    return targetRepository.save(existingtarget);
	                });
	}
	public void deleteTarget(Long id) {
		 targetRepository.deleteById(id);
	}
}