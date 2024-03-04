package org.odyssey.cms.controller;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.CreditCardQueue;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.CreditCardQueueException;
import org.odyssey.cms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("UpdateAllStatus")
    public String updateAllStatus()throws AccountException, CreditCardQueueException, CreditCardException, NotificationException{
        return this.adminService.approveAllCreditCard();
    }

    @PutMapping("updateIndividualStatus/{cardId}")
    public String updateIndividualCreditCardStatus(@PathVariable Integer cardId) throws AccountException, CreditCardQueueException, CreditCardException,NotificationException{
        return this.adminService.approveIndividualCreditCard(cardId);
    }

}
