package org.odyssey.cms.controller;

import org.odyssey.cms.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("system")
@RestController
public class SystemController {
	@Autowired
	private SystemService systemService;

	@PostMapping("/batch-process")
	public void batchProcess() {
		this.systemService.batchProcess();
	}
}
