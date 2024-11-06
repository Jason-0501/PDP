package com.example.policy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.policy.model.Match;
import com.example.policy.model.Policy;
import com.example.policy.model.RequestContext;
import com.example.policy.model.Resource;
import com.example.policy.service.PolicyService;
import com.example.policy.service.PolicySetService;
import com.example.policy.service.ResourceService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("policies")
public class PolicyController {
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private PolicySetService policySetService; 
	
	@Autowired ResourceService resourceService;
	
	@GetMapping
	public List<Policy> getAllPolicies(){
		return policyService.getAllPolicies();
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Policy> getUserById(@PathVariable Long id) {
		Optional<Policy> PolicyOptional = policyService.getPolicyById(id);
		return PolicyOptional.map(Policy -> ResponseEntity.ok(Policy))
                .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	 public ResponseEntity<Policy> createPolicy(@RequestBody Policy Policy) {
		Policy s = policyService.createPolicy(Policy);
	    return  ResponseEntity.status(HttpStatus.CREATED).body(s);
	}
	
	
	 
	@PutMapping("/{id}")
	public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, @RequestBody Policy Policy) {
		Optional<Policy> updatedPolicy = policyService.updatePolicy(id, Policy);
		return updatedPolicy.map(updated -> ResponseEntity.ok(updated))
	            .orElseGet(() -> ResponseEntity.notFound().build());
	}
	 
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
		Optional<Policy> policy = policyService.getPolicyById(id);
		if (policy.isPresent()) {
			policyService.deletePolicy(id);
			return ResponseEntity.noContent().build();
		}else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found
	    }
	}
	 
	@PostMapping("/check")
	public boolean checkPolicies(@RequestBody RequestContext context) throws Exception {
		
		Optional<Resource> resource = resourceService.getResourceByName(context.getResource().getName());
		
		
		List<Policy> set = resource.get().getPolicySet().getPolicies();
		for(Policy s : set) {
			if(!s.evaluate(context,resource.get().isAbacEnabled())) return false;
		
		}
		return true;
		
		
		
	}
 
}