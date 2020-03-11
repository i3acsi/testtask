package com.interview.testtask;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class GreetingAndLoginTests extends com.interview.testtask.TestInit {


    @Test
    public void contextLoads() {
        assertThat(loginController).isNotNull();
        assertThat(orderController).isNotNull();
    }

    @Test
    public void greetingPageLoad() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("This is a Order Tracking System.")))
                .andExpect(content().string(containsString("Please Login.")));
    }

    @Test
    public void whenTryToVisitAdminResourceWithoutAuthenticationThenRedirectToLoginPage() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void whenTryToVisitOperatorResourceWithoutAuthenticationThenRedirectToLoginPage() throws Exception {
        this.mockMvc.perform(get("/order-manage"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void whenTryToVisitCourierResourceWithoutAuthenticationThenRedirectToLoginPage() throws Exception {
        this.mockMvc.perform(get("/order"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void correctAdminLoginTest() throws Exception {
        this.mockMvc.perform(formLogin().user("admin").password("password"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrl("/success"));
    }

    @Test
    public void badCredentialsTest() throws Exception {
        this.mockMvc.perform(post("/login").param("username", "notAdmin").param("password", "password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

}
