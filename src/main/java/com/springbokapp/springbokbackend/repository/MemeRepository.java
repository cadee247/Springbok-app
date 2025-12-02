package com.springbokapp.springbokbackend.repository; // match your backend package

import com.springbokapp.springbokbackend.model.Meme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemeRepository extends JpaRepository<Meme, Long> { }
