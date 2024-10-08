package com.example.policy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.policy.model.PolicySet;
import com.example.policy.service.PolicySetService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/policysets")
public class PolicySetController {

	@Autowired
    private PolicySetService policySetService;

    // 獲取所有 PolicySet
    @GetMapping
    public List<PolicySet> getAllPolicySets() {
        return policySetService.getAllPolicySets();
    }

    // 根據 ID 獲取單個 PolicySet
    @GetMapping("/id/{id}")
    public ResponseEntity<Map<String, Object>> getPolicySetById(@PathVariable Long id) {
        Optional<PolicySet> policySet = policySetService.getPolicySetById(id);
        return policySet.map(ps ->{
        	// 創建一個 Map 並將 resourceType 和 policies 放入其中
            Map<String, Object> response = new HashMap<>();
            response.put("PolicySetName", ps.getName());
            response.put("resourceName", ps.getResource());  // 取得 Resource 的 type
            response.put("Policies", ps.getPolicies());  // 取得 policies 列表

            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/resourceName/{name}")
    public ResponseEntity<Map<String, Object>> getPolicySetByType(@PathVariable String name) {
        Optional<PolicySet> policySet = policySetService.getPolicySetByResourceName(name);
       
        return policySet.map(ps ->{
        	// 創建一個 Map 並將 resourceType 和 policies 放入其中
            Map<String, Object> response = new HashMap<>();
            response.put("resourceName", ps.getResource());  // 取得 Resource 的 type
            response.put("policies", ps.getPolicies());  // 取得 policies 列表

            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
        
  
    }
    // 創建新的 PolicySet
    @PostMapping
    public ResponseEntity<PolicySet> createPolicySet(@RequestBody PolicySet policySet) {
        PolicySet createdPolicySet = policySetService.createPolicySet(policySet);
        return ResponseEntity.ok(createdPolicySet);
    }

    // 更新 PolicySet
    @PutMapping("/{id}")
    public ResponseEntity<PolicySet> updatePolicySet(@PathVariable Long id, @RequestBody PolicySet policySet) {
        try {
            PolicySet updatedPolicySet = policySetService.updatePolicySet(id, policySet);
            return ResponseEntity.ok(updatedPolicySet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    // 更新 PolicySet
    @PutMapping("/{id}/policies/{policyId}")
    public ResponseEntity<Boolean> deletePolicyFromPolicySet(@PathVariable Long id, @PathVariable Long policyId) {
        try {
            boolean updatedPolicySet = policySetService.deletePolicyFromPolicySet(id, policyId);
            return ResponseEntity.ok(updatedPolicySet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    // 刪除 PolicySet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicySet(@PathVariable Long id) {
        policySetService.deletePolicySet(id);
        return ResponseEntity.noContent().build();
    }
}
