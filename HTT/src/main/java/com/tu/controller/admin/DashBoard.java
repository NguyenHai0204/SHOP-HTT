package com.tu.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "dashboard")
public class DashBoard {

   @GetMapping("")
    public String index(){
       return "admin/dashboard";
   }
}
