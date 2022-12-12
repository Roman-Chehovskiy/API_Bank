package API.bank.controllers;

import API.bank.DTO.Dto;
import API.bank.services.impl.UserBalanceServicesImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bank")
public class BalanceController {

    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    @Autowired
    private UserBalanceServicesImpl userBalanceServices;


    @RequestMapping(value = "/getBalance/{id}")
    public String getBalance(@PathVariable int id) {
        Dto answer = userBalanceServices.getBalance(id);
        return gson.toJson(answer);
    }

    @RequestMapping(value = "/takeMoney/{id}/{money}")
    public String takeMoney(@PathVariable int id, @PathVariable double money) {
        return gson.toJson(userBalanceServices.takeMoney(id, money));
    }

    @RequestMapping(value = "/putMoney/{id}/{money}")
    public String putMoney(@PathVariable int id, @PathVariable double money) {
        return gson.toJson( userBalanceServices.putMoney(id, money));
    }

}
