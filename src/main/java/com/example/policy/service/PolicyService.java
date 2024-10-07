package com.example.policy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.policy.model.AllOf;
import com.example.policy.model.AnyOf;
import com.example.policy.model.Match;
import com.example.policy.model.Policy;
import com.example.policy.model.PolicySet;
import com.example.policy.model.Target;
import com.example.policy.repository.AllOfRepository;
import com.example.policy.repository.AnyOfRepository;
import com.example.policy.repository.MatchRepository;
import com.example.policy.repository.PolicyRepository;
import com.example.policy.repository.TargetRepository;

@Service
public class PolicyService {

	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private TargetRepository targetRepository;
	@Autowired
	private AnyOfRepository anyOfRepository;
	@Autowired
	private AllOfRepository allOfRepository;
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private PolicySetService policySetService;
	public List<Policy> getAllPolicies(){
		return policyRepository.findAll();
	}
	public Optional<Policy> getPolicyById(Long id) {
	      return policyRepository.findById(id);
	  }
	
	public Policy createPolicy(Policy policy){
		AnyOf anyOf = policy.getTarget().getAnyOf();
		List<AllOf> allOfs = anyOf.getAllOfs();
		for(AllOf a : allOfs) {
			a.setAnyOf(anyOf);
			List<Match> matches = a.getMatches();
			for(Match m: matches) {
				m.setAllOf(a);
			}
		}
		
		return policyRepository.save(policy);
	}
	
	public Optional<Policy> updatePolicy(Long id, Policy policy) {
		 return policyRepository.findById(id)
	                .map(existingpolicy -> {
	                    existingpolicy.setEffect(policy.isEffect());
	                    existingpolicy.setTarget(policy.getTarget());
	                    existingpolicy.setName(policy.getName());
	                    return policyRepository.save(existingpolicy);
	                });
	}
	public void deletePolicy(Long id) {
		 Optional<Policy> policy = policyRepository.findById(id);
		 PolicySet policySet = policy.get().getPolicySet();
		 policySet.getPolicies().remove(policy.get());
		 policyRepository.delete(policy.get());
	}
}
