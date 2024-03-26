package org.odyssey.cms.controller;

import org.odyssey.cms.entity.CreditCardQueue;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.CreditCardQueueException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("UpdateAllStatus")
    public String updateAllStatus() throws AccountException, CreditCardQueueException, CreditCardException {
        return this.adminService.approveAllCreditCard();
    }

    @PutMapping("/{cardNumber}/putActivationStatus")
    public String updateIndividualCreditCardStatus(@PathVariable String cardNumber) throws AccountException, CreditCardQueueException, CreditCardException {
        return this.adminService.approveIndividualCreditCard(cardNumber);
    }
    @GetMapping("/all")
    public List<CreditCardQueue> getAllCreditCardQueue(){
        return this.adminService.getAllCreditCardQueue();
    }
    @PostMapping("/adminLogin")
    public String loginAdmin(@RequestParam String email, @RequestParam String password)throws UserException
    {
        return adminService.logInAdmin(email,password);
    }

}
