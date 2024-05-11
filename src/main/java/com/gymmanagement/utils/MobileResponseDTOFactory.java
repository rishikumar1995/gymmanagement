package com.gymmanagement.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MobileResponseDTOFactory {

//    public Connection getConnectionFromEntityManager(EntityManager entityManager) {
//        entityManager = entityManager.getEntityManagerFactory().createEntityManager();
//        Connection conn = entityManager.unwrap(SessionImpl.class).connection();
//        try {
//            conn.setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }

    public ResponseEntity<MobileResponseDTO> successMessage(String message) {
        MobileResponseDTO responseDTO = new MobileResponseDTO();
        responseDTO.setCode(HttpStatus.OK.value());
        responseDTO.setMessage(message);
        responseDTO.setSuccess(true);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    public ResponseEntity<MobileResponseDTO> failedMessage(String message) {
        MobileResponseDTO responseDTO = new MobileResponseDTO();
        responseDTO.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        responseDTO.setMessage(message);
        return new ResponseEntity<>(responseDTO, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public void setValidationErrorMsg(List<Map<String, Object>> errorlist, String field, String msg, int code) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("field", field);
        map.put("message", msg);
        errorlist.add(map);
    }

    public ResponseEntity<MobileResponseDTO> reportInternalServerError(Throwable e) {
        MobileResponseDTO mobileResponseDTO = new MobileResponseDTO();
        mobileResponseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        mobileResponseDTO.setMessage("Some unknown error occured.");
        System.out.println("The cause of exception : " + e.getCause());
        System.err.println("The cause of exception : " + e.getCause());
        e.printStackTrace();
        return new ResponseEntity<MobileResponseDTO>(mobileResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public String getCurrentUserName(HttpServletRequest request) {
        String username = DataTypeUtility.stringValue(request.getAttribute("__username__"));
        return username;
    }

    public Long getCurrentUserId(HttpServletRequest request) {
        Long userid = DataTypeUtility.longValue(request.getAttribute("__userid__"));
        return userid;
    }

}
