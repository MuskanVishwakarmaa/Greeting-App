package com.example.greetingapp.controller;

import com.example.greetingapp.entity.Greeting;
import com.example.greetingapp.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @GetMapping("/{id}")
    public ResponseEntity<Greeting> getGreetingById(@PathVariable Long id) {
        Optional<Greeting> greeting = greetingService.getGreetingById(id);
        return greeting.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST a new greeting
    @PostMapping
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
        return ResponseEntity.ok(greetingService.createGreeting(greeting));
    }

    // 3️⃣ POST - Create multiple greetings (Bulk Create)
    @PostMapping("/batch")
    public ResponseEntity<List<Greeting>> createMultipleGreetings(@RequestBody List<Greeting> greetings) {
        List<Greeting> savedGreetings = greetingService.saveMultipleGreetings(greetings);
        return ResponseEntity.ok(savedGreetings);
    }


    // PUT to update (replace) a greeting
    @PutMapping("/{id}")
    public ResponseEntity<Greeting> updateGreeting(@PathVariable Long id, @RequestBody Greeting updatedGreeting) {
        Optional<Greeting> updated = greetingService.updateGreeting(id, updatedGreeting);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH to partially update a greeting
    @PatchMapping("/{id}")
    public ResponseEntity<Greeting> patchGreeting(@PathVariable Long id, @RequestBody Greeting patchData) {
        Optional<Greeting> patched = greetingService.patchGreeting(id, patchData);
        return patched.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE a greeting by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGreeting(@PathVariable Long id) {
        if (greetingService.deleteGreeting(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}