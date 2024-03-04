package org.odyssey.cms.controller;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.CreditCardQueue;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("UpdateAllStatus")
    public String updateAllStatus()throws AccountException {
        return this.adminService.approveAllCreditCard();
    }

    @PutMapping("updateIndividual/{queueNumber}")
    public String updateIndividualCreditCardStatus(@PathVariable Integer cardId) throws AccountException{
        return this.adminService.approveIndividualCreditCard(cardId);
    }

}
