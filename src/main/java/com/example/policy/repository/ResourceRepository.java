package com.example.policy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.policy.model.Resource;

public interface ResourceRepository extends JpaRepository <Resource,Long> {

	List<Resource> findAll();
	 Optional<Resource> findByName(String name);
}
