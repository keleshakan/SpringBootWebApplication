package com.example.MSSQLConnection.resource;

import com.example.MSSQLConnection.persistence.User;
import com.example.MSSQLConnection.service.GitHubLookupService;
import com.example.MSSQLConnection.service.PersonService;
import com.example.MSSQLConnection.util.ExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class PersonResource {

    PersonService personService;

    GitHubLookupService lookupService;

    public PersonResource(PersonService personService, GitHubLookupService lookupService){
        this.personService = personService;
        this.lookupService = lookupService;
    }

    @GetMapping("/person")
    public String getPerson(){
        return this.personService.getPersonData();
    }

    @GetMapping("/createExcelFile")
    public String createPersonExcel() throws IOException {
        ExcelUtil.createExcelFile(this.personService.getPersonData());
        return "Excel file is created!";
    }

    @GetMapping("/findUser")
    public List<User> findUser() throws InterruptedException, ExecutionException {

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = lookupService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = lookupService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = lookupService.findUser("Spring-Projects");

        CompletableFuture.allOf(page1,page2,page3).join();

        List<User> userList = new ArrayList<>();
        userList.add(page1.get());
        userList.add(page2.get());
        userList.add(page3.get());

        return userList;
    }
}
