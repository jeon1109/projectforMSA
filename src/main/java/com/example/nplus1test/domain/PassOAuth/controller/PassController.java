package com.example.nplus1test.domain.PassOAuth.controller;

import com.example.nplus1test.domain.PassOAuth.DTO.PhoneAuthResultDto;
import com.example.nplus1test.domain.PassOAuth.service.PassService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PassController {

    private final PassService passService;

    public PassController(PassService passService) {
        this.passService = passService;
    }

    @RequestMapping(value = "/pass/success", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> passSuccess(HttpServletRequest request, ModelMap modelMap) {
        String encodeData = request.getParameter("EncodeData");
        String sessionReqSeq = (String) request.getSession().getAttribute("REQ_SEQ");

        String sMessage = "";
        try {
            PhoneAuthResultDto dto = passService.parseAndValidate(encodeData, sessionReqSeq);
            modelMap.addAttribute("dto", dto);
            modelMap.addAttribute("sMessage", "success");
        } catch (Exception e) {
            sMessage = e.getMessage();
            modelMap.addAttribute("sMessage", sMessage);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }

}
