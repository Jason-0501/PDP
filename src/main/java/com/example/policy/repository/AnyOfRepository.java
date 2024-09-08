package com.example.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.policy.model.AnyOf;

public interface AnyOfRepository extends JpaRepository<AnyOf,Long>{

}
