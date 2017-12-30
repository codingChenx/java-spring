package com.skilly.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoApplication {

	@RequestMapping("/")
	public String greeting1() {
		return "Hello World222!";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet() {
		return "Login Page";
	}

	@GetMapping("login2")
	public String loginGet2()
	{
		return "Login Page2";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost() {
		return "Login Post Request";
	}

	@PostMapping(value = "/login2")
	public String loginPost2() {
		return "Login Post Request2";
	}

	@RequestMapping("/users/{username}")
	public String userProfile(@PathVariable("username") String username) {
		return String.format("user %s", username);
	}

	@RequestMapping("/posts/{id}")
	public String post(@PathVariable("id") int id) {
		return String.format("post %d", id);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
