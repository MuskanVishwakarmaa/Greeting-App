package com.example.greetingapp.repository;


//import com.development.Greeting.Application.model.Greeting;
import com.example.greetingapp.entity.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, Long> {}