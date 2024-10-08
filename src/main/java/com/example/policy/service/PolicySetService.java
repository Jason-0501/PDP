package com.example.policy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	
	public Optional<PolicySet> getPolicySetByResourceName(String name){
		return policySetRepository.findByResourceName(name);
	}
	
	 // 創建新的 PolicySet

	public PolicySet createPolicySet(PolicySet policySet) {
	      // 確保 policies 被正確處理
		
		String policySetName = policySet.getName();
		System.out.println(policySet.getResource().getName());
		policySet.setName(policySetName);
		policySet.setPolicies(null);
		
		String resourceName = policySet.getResource().getName();
		Optional<Resource> resource = resourceRepository.findByName(resourceName);
		System.out.println(resource);
		if(resource.isPresent()) {

			policySet.setResource(resource.get());
		}
		else {
  		  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
  	    }
		
	
        // 保存 PolicySet 並返回
        return policySetRepository.save(policySet);
    }

    // 更新 PolicySet
    public PolicySet updatePolicySet(Long id, PolicySet newPolicySet) {
        return policySetRepository.findById(id).map(policySet -> {
        	
          List<Policy> policies = newPolicySet.getPolicies();
          List<Policy> outputPolicies = new ArrayList<>();
          String policySetResourceName = policySet.getResource().getName();
          boolean sameResourceName = false;
          
          
          for(Policy p : policies) {
        	  Optional<Policy> policy = policyRepository.findPolicyByName(p.getName());
        	  
  
        	 
      
        	  if(policy.isPresent()) {
        		  Policy existingPolicy = policy.get();
        		  // 使用 Stream API 來簡化 Matches 的遍歷，取得所有 attributeValue 的值
        	        List<String> attributeValue =  existingPolicy.getTarget().getAnyOf().getAllOfs().stream()  // 展開 allOfs 列表
        	                .flatMap(allOf -> allOf.getMatches().stream())  // 展開 matches 列表
        	                .map(Match::getAttributeValue)  // 取得每個 match 裡的 attributeValue
        	                .collect(Collectors.toList()) ; // 收集結果到一個 List
        	                  // 如果 target 或 anyOf 是 null，返回空列表

        	        // 印出所有的 attributeValue
        	        System.out.println(attributeValue);
        	        //檢查attributeValue跟resourceName有沒有一樣
        	        for(String av : attributeValue) {
        	        	if(av.equals(policySetResourceName)) {
        	        		sameResourceName = true;
        	        	}
        	        }
        	      if(sameResourceName == false){
        	    	  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This policy's resourcename doesn't belong to this policyset");
        	      }
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
            
            policySet.setResource(policySet.getResource());
            return policySetRepository.save(policySet);
        }).orElseGet(() -> {
            newPolicySet.setId(id);
            return policySetRepository.save(newPolicySet);
        });
    }

    // 刪除 PolicySet
    public boolean deletePolicyFromPolicySet(Long id, Long policyId) {
        Optional<PolicySet> policySetOpt = policySetRepository.findById(id);
        Optional<Policy> policyOpt = policyRepository.findById(policyId);

        if (policySetOpt.isPresent() && policyOpt.isPresent()) {
            PolicySet policySet = policySetOpt.get();
            Policy policy = policyOpt.get();

            // 從 PolicySet 的 Policies 列表中移除指定的 Policy
            boolean removed = policySet.getPolicies().remove(policy);

            if (removed) {
                // 更新並保存 PolicySet
            	policy.setPolicySet(null);
                policyRepository.save(policy);
                policySetRepository.save(policySet);
                return true;  // 成功更新
            }
        }

        return false;  // 找不到相應的 Policy 或 PolicySet
    }
    public void deletePolicySet(Long id) {
    	 Optional<PolicySet> policySet=policySetRepository.findById(id);
    	 if(policySet.isPresent()) {
    		 PolicySet existingPolicySet = policySet.get();
    	        
    	        // 解除 Resource 與 PolicySet 的關聯
    	        Resource resource = existingPolicySet.getResource();
    	        if (resource != null) {
    	            resource.setPolicySet(null);
    	            resourceRepository.save(resource); // 保存 Resource 的變更
    	        }

    	        // 解除所有 Policies 與 PolicySet 的關聯
    	        List<Policy> policies = new ArrayList<>(existingPolicySet.getPolicies());  // 創建一個新列表來遍歷
    	        for (Policy policy : policies) {
    	            existingPolicySet.getPolicies().remove(policy); // 從 PolicySet 的 Policies 列表中移除
    	            policy.setPolicySet(null);  // 將每個 Policy 的 PolicySet 設為 null
    	            policyRepository.save(policy);  // 保存變更
    	        }
    	        
    	        // 刪除 PolicySet 本身
    	        policySetRepository.delete(existingPolicySet);
}}}
