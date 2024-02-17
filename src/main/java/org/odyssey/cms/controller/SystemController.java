package org.odyssey.cms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {
	@GetMapping("/")
	public String hi(){
		return "hi";
	}
}
