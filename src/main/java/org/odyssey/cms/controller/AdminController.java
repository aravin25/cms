package org.odyssey.cms.controller;

import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("admin")
    public String updateStatus()throws AccountException {
        return this.adminService.approveCreditCard();
    }

}
