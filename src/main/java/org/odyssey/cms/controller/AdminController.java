package org.odyssey.cms.controller;

import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("admin/UpdateAllStatus")
    public String updateAllStatus()throws AccountException, NotificationException {
        return this.adminService.approveAllCreditCard();
    }

    @PutMapping("/admin/{cardNumber}/putActivationStatus")
    public String updateIndividualCreditCardStatus(@PathVariable String cardNumber) throws AccountException,NotificationException{
        return this.adminService.approveIndividualCreditCard(cardNumber);
    }

}
