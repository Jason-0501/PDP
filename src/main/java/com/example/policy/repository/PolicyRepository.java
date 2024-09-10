package com.example.policy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.policy.model.Policy;
public interface PolicyRepository extends JpaRepository <Policy , Long> {

	List<Policy> findAll();
	Optional<Policy> findPolicyByName(String name);
}
