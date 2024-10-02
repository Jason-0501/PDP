package com.example.policy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.policy.model.AllOf;
import com.example.policy.model.AnyOf;
import com.example.policy.model.Match;
import com.example.policy.model.Policy;
import com.example.policy.model.PolicySet;
import com.example.policy.model.Resource;
import com.example.policy.repository.AllOfRepository;
import com.example.policy.repository.AnyOfRepository;
import com.example.policy.repository.MatchRepository;
import com.example.policy.repository.PolicyRepository;
import com.example.policy.repository.PolicySetRepository;
import com.example.policy.repository.ResourceRepository;
import com.example.policy.repository.TargetRepository;

@Service
public class PolicySetService {

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
	private PolicySetRepository policySetRepository;
	@Autowired
	private ResourceRepository resourceRepository;
	
	public List<PolicySet> getAllPolicySets(){
		
		return policySetRepository.findAll();
	}
	public Optional<PolicySet> getPolicySetById(Long id) {
	      return policySetRepository.findById(id);
	  }
	
	public Optional<PolicySet> getPolicySetByResourceType(String type){
		return policySetRepository.findByResourceType(type);
	}
	
	 // 創建新的 PolicySet

	public PolicySet createPolicySet(PolicySet policySet) {
	      // 確保 policies 被正確處理
		
		String policies = policySet.getName();
		policySet.setName(policies);
		policySet.setPolicies(null);
		policySet.setResource(null);
		
//        List<Policy> policies = policySet.getPolicies();
//        List<Policy> outputPolicies = new ArrayList<>();
//        for(Policy p : policies) {
//        	Optional<Policy> policy = policyRepository.findPolicyByName(p.getName());
//        	if(policy.isPresent()) {
//        		Policy existingPolicy = policy.get();
//        		existingPolicy.setPolicySet(policySet);
//        		outputPolicies.add(existingPolicy);
//        	}
//        	
//        }
//        
//
//        policySet.setPolicies(outputPolicies);
        // 保存 PolicySet 並返回
        return policySetRepository.save(policySet);
    }

    // 更新 PolicySet
    public PolicySet updatePolicySet(Long id, PolicySet newPolicySet) {
        return policySetRepository.findById(id).map(policySet -> {
            // 檢查更新後 resource 的 type 是否與現有的 PolicySet 資料庫中的相符
          List<Policy> policies = newPolicySet.getPolicies();
          List<Policy> outputPolicies = new ArrayList<>();
          for(Policy p : policies) {
        	  Optional<Policy> policy = policyRepository.findPolicyByName(p.getName());

      
        	  if(policy.isPresent()) {
        		  Policy existingPolicy = policy.get();
        		  if (existingPolicy.getPolicySet() == null || !existingPolicy.getPolicySet().equals(policySet)) {
                      existingPolicy.setPolicySet(policySet);
                      policyRepository.save(existingPolicy);
            		  outputPolicies.add(existingPolicy);
            		  
                  }
        		 
        	  }else {
        		  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Policy not found");
        	  }
          }
            policySet.setPolicies(outputPolicies);
            policySet.setResource(newPolicySet.getResource());
            return policySetRepository.save(policySet);
        }).orElseGet(() -> {
            newPolicySet.setId(id);
            return policySetRepository.save(newPolicySet);
        });
    }

    // 刪除 PolicySet
    public void deletePolicySet(Long id) {
        policySetRepository.deleteById(id);
    }
}
