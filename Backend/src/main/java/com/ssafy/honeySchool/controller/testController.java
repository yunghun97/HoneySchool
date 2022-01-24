package com.ssafy.honeySchool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class testController {
	
	@GetMapping("/test")
	public String test() {		
		return "테스트 컨트롤러 접속";
	}
}
