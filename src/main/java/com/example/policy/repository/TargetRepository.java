package com.example.policy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.policy.model.Resource;
import com.example.policy.model.Target;

public interface TargetRepository extends CrudRepository <Target,Long> {

	List<Target> findAll();
}
