package com.example.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.policy.model.Policy;
import com.example.policy.repository.PolicyRepository;

@Service
public class PolicyService {

	@Autowired
	private PolicyRepository policyRepository;
	
	public List<Policy> getAllPolicies(){
		return policyRepository.findAll();
	}
	public Optional<Policy> getPolicyById(Long id) {
	      return policyRepository.findById(id);
	  }
	public Policy createPolicy(Policy policy){
		return policyRepository.save(policy);
	}
	
	public Optional<Policy> updatePolicy(Long id, Policy policy) {
		 return policyRepository.findById(id)
	                .map(existingpolicy -> {
	                    existingpolicy.setEffect(policy.isEffect());
	                    existingpolicy.setTarget(policy.getTarget());
	                    return policyRepository.save(existingpolicy);
	                });
	}
	public void deletePolicy(Long id) {
		 policyRepository.deleteById(id);
	}
}
