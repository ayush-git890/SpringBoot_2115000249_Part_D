package com.example.greetingapp.repository;

import com.example.greetingapp.controller.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, Long> {}
