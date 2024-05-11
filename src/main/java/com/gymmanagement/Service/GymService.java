package com.gymmanagement.Service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;


public interface GymService {

    Object createMember(Map<String,Object> map, HttpServletRequest request);
    Object memberList(Map<String,Object> map, HttpServletRequest request);

}
