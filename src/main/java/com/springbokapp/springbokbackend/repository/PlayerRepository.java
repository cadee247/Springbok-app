package com.springbokapp.springbokbackend.repository;

import com.springbokapp.springbokbackend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
