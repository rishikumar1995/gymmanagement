package com.gymmanagement.Service;

import com.gymmanagement.Modal.Role;
import com.gymmanagement.Modal.Users;
import com.gymmanagement.Repository.UserRepository;
import com.gymmanagement.utils.MobileResponseDTOFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class GymServiceImpl implements GymService {

    @Autowired
    private MobileResponseDTOFactory mobileResponseDTOFactory;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object createMember(Map<String, Object> map, HttpServletRequest request) {
        Object memberName = map.get("memberName");
        Object mobileNo = map.get("mobileNo");
        Object email = map.get("email");
        Object trainerName = map.get("trainerName");

        if(memberName==null || memberName.equals("")){
            return mobileResponseDTOFactory.failedMessage("Member Name is Blank");
        }

        Users users = new Users();
        users.setUsername(memberName.toString());
        users.setPassword("123456789@");
        users.setEmailid(email.toString());
        users.setMobileno(mobileNo.toString());
        users.setRole(Role.USER);
        userRepository.save(users);

        return mobileResponseDTOFactory.successMessage("Member Save Successfully");
    }

    @Override
    public Object memberList(Map<String, Object> map, HttpServletRequest request) {
        List<Users> allByRole = userRepository.findAllByRole(Role.USER);
        return allByRole;
    }


}
