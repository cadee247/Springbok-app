package com.springbokapp.springbokbackend.repository;

import com.springbokapp.springbokbackend.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    // You can add custom queries later if needed
}