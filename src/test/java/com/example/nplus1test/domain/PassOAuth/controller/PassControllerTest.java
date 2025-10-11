package com.example.nplus1test.domain.PassOAuth.controller;

import com.example.nplus1test.domain.PassOAuth.DTO.PhoneAuthResultDto;
import com.example.nplus1test.domain.PassOAuth.service.PassService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PassController.class)
class PassControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PassService passService;

    @Test
    @DisplayName("PASS 성공 시 200 OK 반환")
    void success() throws Exception {
        var dto = PhoneAuthResultDto.builder().sRequestNumber("REQ-1").build();
        when(passService.parseAndValidate(anyString(), anyString())).thenReturn(dto);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("REQ_SEQ", "REQ-1");

        mockMvc.perform(get("/pass/success")
                        .session(session)
                        .param("EncodeData", "ENC"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PASS 실패 시 500 반환")
    void failure() throws Exception {
        when(passService.parseAndValidate(anyString(), anyString()))
                .thenThrow(new IllegalStateException("error"));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("REQ_SEQ", "REQ-1");

        mockMvc.perform(get("/pass/success")
                        .session(session)
                        .param("EncodeData", "ENC"))
                .andExpect(status().isInternalServerError());
    }
}