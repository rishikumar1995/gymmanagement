package com.gymmanagement.RestController;


import com.gymmanagement.Modal.Users;
import com.gymmanagement.Service.GymService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rest/gym/")
public class GymRestController {

    @Autowired
    private GymService gymService;

    @PostMapping("/create_member")
    public Object create_member(@RequestBody Map<String,Object> map, HttpServletRequest request){
        try {
            return gymService.createMember(map,request);
        } catch (Exception e){
            return e.getMessage();
        }
    }

    @GetMapping("/member_list")
    public Object member_list(@RequestParam Map<String,Object> map, HttpServletRequest request){
        try {
            return gymService.memberList(map,request);
        } catch (Exception e){
            return e.getMessage();
        }
    }

}
