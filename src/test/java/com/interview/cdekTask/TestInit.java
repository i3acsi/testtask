package com.interview.cdekTask;


import com.interview.cdekTask.controller.LoginController;
import com.interview.cdekTask.controller.OrderController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public abstract class TestInit {
    protected MockMvc mockMvc;
    protected LoginController loginController;
    protected OrderController orderController;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc =
                MockMvcBuilders
                        .webAppContextSetup(context)
                        .apply(springSecurity())
                        .build();
    }

    @Autowired
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @Autowired
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }
}

