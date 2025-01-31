package com.example.myapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/test") 
	public void hello() {
		System.out.println("안녕");
	}
}
