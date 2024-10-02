package com.example.policy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.policy.model.PolicySet;

public interface PolicySetRepository extends JpaRepository<PolicySet, Long> {

	Optional<PolicySet> findByResourceType(String type);
}
