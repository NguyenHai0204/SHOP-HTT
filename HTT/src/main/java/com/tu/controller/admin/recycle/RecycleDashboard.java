package com.tu.controller.admin.recycle;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "directory")
public class RecycleDashboard {

    @GetMapping("/recycleDirectory")
    public String recycle(){
        return "admin/recycle/directory";
    }
}
