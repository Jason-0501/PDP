package com.example.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.policy.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

}
