package com.example.policy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.policy.model.Subject;

public interface SubjectRepository extends JpaRepository <Subject,Long> {

	List<Subject> findAll();
}
