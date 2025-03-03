package com.example.greetingapp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    // GET request - returns a greeting message as JSON
    @GetMapping
    public Greeting getGreeting() {
        return new Greeting("Hello, this is a GET request!");
    }

    // POST request - returns a greeting message as JSON
    @PostMapping
    public Greeting postGreeting(@RequestBody Greeting greeting) {
        return new Greeting("Hello, this is a POST request! Received message: " + greeting.getMessage());
    }

    // PUT request - returns a greeting message as JSON
    @PutMapping
    public Greeting putGreeting(@RequestBody Greeting greeting) {
        return new Greeting("Hello, this is a PUT request! Updated message: " + greeting.getMessage());
    }

    // DELETE request - returns a greeting message as JSON
    @DeleteMapping
    public Greeting deleteGreeting() {
        return new Greeting("Hello, this is a DELETE request!");
    }

    // Inner Greeting class to hold the message in JSON format
    static class Greeting {
        private String message;

        public Greeting(String message) {
            this.message = message;
        }

        // Getter for message
        public String getMessage() {
            return message;
        }

        // Setter for message
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
