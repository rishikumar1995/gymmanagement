package com.gymmanagement.MVCController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/gym")
public class GymMVCController {

    @RequestMapping(value = "/homepage",method = {RequestMethod.GET,RequestMethod.POST})
    public String homepage(){
        return "homepage";
    }

    @RequestMapping(value = "/create_member",method = {RequestMethod.GET,RequestMethod.POST})
    public String createMember(){
        return "create_member";
    }

}
