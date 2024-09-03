package com.example.policy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.example.policy.model.Policy;
import com.example.policy.model.Target;
public interface PolicyRepository extends CrudRepository <Policy , Long> {

	List<Policy> findAll();
}
