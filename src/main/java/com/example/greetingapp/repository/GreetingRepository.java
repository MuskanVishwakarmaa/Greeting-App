package com.example.greetingapp.repository;


import com.example.greetingapp.GreetingMessage;
import com.example.greetingapp.entity.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Repository;

//@Repository


@Repository
public interface GreetingRepository extends JpaRepository<GreetingMessage, Long> {
}