package com.example.policy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.policy.model.Resource;
import com.example.policy.model.Subject;

public interface SubjectRepository extends CrudRepository <Subject,Long> {

	List<Subject> findAll();
}
