package com.interview.testtask;



import com.interview.testtask.controller.OrderController;
import lombok.AllArgsConstructor;
import org.junit.runner.RunWith;
import com.interview.testtask.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

class TesttaskApplicationTests {
	private LoginController loginController;
	private OrderController orderController;
	private MockMvc mockMvc;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Autowired
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Autowired
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    	@Test
	public void contextLoads() {
        assertThat(loginController).isNotNull();
        assertThat(orderController).isNotNull();
	}

	@Test
    public void greetingPageLoad() throws Exception{
	    this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("This is a Order Tracking System.") ));
    }


}
