package com.tu.controller.admin.manager;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "directory")
public class ManagerDashboard {

    @GetMapping("/managerDirectory")
    public String showManager(){
        return "admin/manager/directory";
    }
}
