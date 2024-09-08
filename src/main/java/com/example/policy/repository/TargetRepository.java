package com.example.policy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.policy.model.Target;

public interface TargetRepository extends JpaRepository <Target,Long> {

	List<Target> findAll();
}
