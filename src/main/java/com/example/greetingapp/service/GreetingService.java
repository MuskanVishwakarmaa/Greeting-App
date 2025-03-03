package com.example.greetingapp.service;

import com.example.greetingapp.entity.Greeting;
import com.example.greetingapp.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


@Service
public class GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }

    public Optional<Greeting> getGreetingById(Long id) {
        return greetingRepository.findById(id);
    }

    public Greeting createGreeting(Greeting greeting) {
        return greetingRepository.save(greeting);
    }

    public List<Greeting> saveMultipleGreetings(List<Greeting> greetings) {
        return greetingRepository.saveAll(greetings);  // Saves a list of greetings
    }

    public Optional<Greeting> updateGreeting(Long id, Greeting updatedGreeting) {
        return greetingRepository.findById(id).map(existingGreeting -> {
            existingGreeting.setMessage(updatedGreeting.getMessage());
            return greetingRepository.save(existingGreeting);
        });
    }

    public Optional<Greeting> patchGreeting(Long id, Greeting patchData) {
        return greetingRepository.findById(id).map(existingGreeting -> {
            if (patchData.getMessage() != null) {
                existingGreeting.setMessage(patchData.getMessage());
            }
            return greetingRepository.save(existingGreeting);
        });
    }

    public boolean deleteGreeting(Long id) {
        if (greetingRepository.existsById(id)) {
            greetingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}