package com.interview.cdekTask;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.interview.cdekTask.dto.OrderDtoCourier;
import com.interview.cdekTask.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Slf4j
public class ControllersTests extends com.interview.cdekTask.TestInit {
    private ObjectMapper objectMapper;

    @Autowired
    public void setModelMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Test
    @WithUserDetails("admin")
    public void adminPageTest() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("test order")));
    }

    @Test
    @WithUserDetails("admin")
    public void loadOrdersWithDateRangeOnAdminPage() throws Exception {
        String from = LocalDateTime.of(2019, 5, 1, 12, 0, 0).toString();
        String to = LocalDateTime.of(2019, 7, 1, 12, 0, 0).toString();
        this.mockMvc.perform(get("/admin").param("from", from).param("to", to))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)))
                .andExpect(jsonPath("$[1].id", Matchers.is(6)))
                .andExpect(jsonPath("$[2].id", Matchers.is(7)))
                .andExpect(jsonPath("$[0].name", Matchers.is("test order 5")))
                .andExpect(jsonPath("$[1].name", Matchers.is("test order 6")))
                .andExpect(jsonPath("$[2].name", Matchers.is("test order 7")))
                .andExpect(jsonPath("$[0]", hasKey("created")))
                .andExpect(jsonPath("$[0].created", Matchers.is(Timestamp.valueOf(LocalDateTime.of(2019, 05, 01, 12,00,00)))))

                .andExpect(jsonPath("$[1]", hasKey("created")))
                .andExpect(jsonPath("$[1].created", Matchers.is(Timestamp.valueOf(LocalDateTime.of(2019, 06, 01, 12,00,00)))))

                .andExpect(jsonPath("$[2]", hasKey("created")))
                .andExpect(jsonPath("$[2].created", Matchers.is(Timestamp.valueOf(LocalDateTime.of(2019, 07, 01, 12,00,00)))))

                .andExpect(jsonPath("$[0].holder", hasKey("roles")))
                .andExpect(jsonPath("$[0].holder.roles", Matchers.contains("ROLE_OPERATOR")))
                .andExpect(jsonPath("$[1].holder", hasKey("roles")))
                .andExpect(jsonPath("$[1].holder.roles", Matchers.contains("ROLE_OPERATOR")))
                .andExpect(jsonPath("$[2].holder", hasKey("roles")))
                .andExpect(jsonPath("$[2].holder.roles", Matchers.contains("ROLE_OPERATOR")))
        ;
    }

    @Test
    @WithUserDetails("operator1")
    public void loadOrdersWithDateRangeOnOperatorsPage() throws Exception {
        String from = LocalDateTime.of(2019, 5, 1, 12, 0, 0).toString();
        String to = LocalDateTime.of(2019, 7, 1, 12, 0, 0).toString();
        this.mockMvc.perform(get("/order-manage").param("from", from).param("to", to))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)))
                .andExpect(jsonPath("$[1].id", Matchers.is(6)))
                .andExpect(jsonPath("$[2].id", Matchers.is(7)))
                .andExpect(jsonPath("$[0].name", Matchers.is("test order 5")))
                .andExpect(jsonPath("$[1].name", Matchers.is("test order 6")))
                .andExpect(jsonPath("$[2].name", Matchers.is("test order 7")))
                .andExpect(jsonPath("$[0]", hasKey("created")))
                .andExpect(jsonPath("$[0].created", Matchers.is(Timestamp.valueOf(LocalDateTime.of(2019, 05, 01, 12,00,00)))))
                .andExpect(jsonPath("$[1]", hasKey("created")))
                .andExpect(jsonPath("$[1].created", Matchers.is(Timestamp.valueOf(LocalDateTime.of(2019, 06, 01, 12,00,00)))))
                .andExpect(jsonPath("$[2]", hasKey("created")))
                .andExpect(jsonPath("$[2].created", Matchers.is(Timestamp.valueOf(LocalDateTime.of(2019, 07, 01, 12,00,00)))))

                .andExpect(jsonPath("$[0]", hasKey("holderName")))
                .andExpect(jsonPath("$[1]", hasKey("holderName")))
                .andExpect(jsonPath("$[2]", hasKey("holderName")))
                .andExpect(jsonPath("$[0].holderName", Matchers.is("operator1")))
                .andExpect(jsonPath("$[1].holderName", Matchers.is("operator1")))
                .andExpect(jsonPath("$[2].holderName", Matchers.is("operator1")))

        ;
    }

    @Test
    @WithUserDetails("courier1")
    public void loadOrdersWithDateRangeOnCouriersPage() throws Exception {
        String from = LocalDateTime.of(2019, 5, 1, 12, 0, 0).toString();
        String to = LocalDateTime.of(2019, 7, 1, 12, 0, 0).toString();
        this.mockMvc.perform(get("/order").param("from", from).param("to", to))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(5)))
                .andExpect(jsonPath("$[1].id", Matchers.is(6)))
                .andExpect(jsonPath("$[2].id", Matchers.is(7)))
                .andExpect(jsonPath("$[0].name", Matchers.is("test order 5")))
                .andExpect(jsonPath("$[1].name", Matchers.is("test order 6")))
                .andExpect(jsonPath("$[2].name", Matchers.is("test order 7")))
                .andExpect(jsonPath("$[0]", Matchers.not(hasKey("created"))))
                .andExpect(jsonPath("$[1]", Matchers.not(hasKey("created"))))
                .andExpect(jsonPath("$[2]", Matchers.not(hasKey("created"))))
                .andExpect(jsonPath("$[0].holderName", Matchers.is("operator1")))
                .andExpect(jsonPath("$[1].holderName", Matchers.is("operator1")))
                .andExpect(jsonPath("$[2].holderName", Matchers.is("operator1")))
        ;
    }

    /**
     * Courier accepts the order.
     * <p>
     * Before courier accepts the order, orders holder is operator.
     * After courier accepts the order,  the courier becomes the holder of the order.
     */
    @Test
    @WithUserDetails("courier1")
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void whenCourierAcceptsOrderThenOrderChanges() throws Exception {
        try {
            this.mockMvc.perform(get("/order"))
                    .andDo(print())
                    .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                    .andExpect(jsonPath("$[%d].holderId", Matchers.is(2)))
            ;

            this.mockMvc.perform(get("/order/1"))
                    .andDo(print())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasKey("id")))
                    .andExpect(jsonPath("$.id", Matchers.is(1)))
                    .andExpect(jsonPath("$", hasKey("holderId")))
                    .andExpect(jsonPath("$.holderId", Matchers.is(3)))
            ;
            throw new RuntimeException("rollback for accept method");
        } catch (RuntimeException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Courier completes the order.
     * <p>
     * Before courier completes the order, the length of list of orders that are not completed is 11.
     * After courier completes the order,  the length of list of orders that are not completed is 10.
     */
    @Test
    @WithUserDetails("courier1")
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void whenCourierCompleteOrderThenListOfOrdersGetShorterByOne() throws Exception {
        try {
            this.mockMvc.perform(get("/order"))
                    .andDo(print())
                    .andExpect(jsonPath("$", hasSize(12)));

            this.mockMvc.perform(get("/order/1"))
                    .andDo(print())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasKey("id")))
                    .andExpect(jsonPath("$.id", Matchers.is(1)))
                    .andExpect(jsonPath("$", hasKey("holderName")))
                    .andExpect(jsonPath("$.holderName", Matchers.is("courier1")))
                    .andExpect(jsonPath("$", hasKey("holderTelephone")))
                    .andExpect(jsonPath("$.holderTelephone", Matchers.is("2")));

            this.mockMvc.perform(delete("/order/1"))
                    .andDo(print());

            this.mockMvc.perform(get("/order"))
                    .andDo(print())
                    .andExpect(jsonPath("$", hasSize(11)));

            throw new RuntimeException("rollback for complete method");
        } catch (RuntimeException e) {
            log.info(e.getMessage());
        }

    }


    /**
     * Courier cancels the order.
     * <p>
     * Before courier cancels the order, the length of list of orders to call is 1;
     * After courier cancels the order,  the length of list of orders to call is 2;
     */
    @Autowired
    private UserService userService;


    @Test
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void whenCourierCancelOrderThenListOfOrdersToCallContainsThisOrder() throws Exception {
        try {
            this.mockMvc.perform(get("/order-manage/toCall")
                    .with(user(userService.loadUserByUsername("operator1"))))
                    .andDo(print())
                    .andExpect(jsonPath("$", hasSize(0)));

            this.mockMvc.perform(get("/order/1")
                    .with(user(userService.loadUserByUsername("courier1"))))
                    .andDo(print())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasKey("id")))
                    .andExpect(jsonPath("$", hasKey("holderName")))
                    .andExpect(jsonPath("$.holderName", Matchers.is("courier1")))
            ;

            this.mockMvc.perform(post("/order/1")
                    .with(user(userService.loadUserByUsername("courier1"))))
                    .andDo(print());

            this.mockMvc.perform(get("/order-manage/toCall")
                    .with(user(userService.loadUserByUsername("operator1"))))
                    .andDo(print())
                    .andExpect(jsonPath("$[0].holderName", Matchers.nullValue()))
                    .andExpect(jsonPath("$[0].holderTelephone", Matchers.nullValue()))
                    .andExpect(jsonPath("$[0].id", Matchers.is(1)));
            throw new RuntimeException("rollback for cancel method");
        } catch (RuntimeException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Operator add new order.
     * <p>
     * Before operator add the order, the length of list of orders is a.
     * After operator add the order,  the length of list of orders is a+1.
     * And list contains this order.
     */
    @Test
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public void whenOperatorAddOrderThenListOfOrdersGetLongerByOne() throws Exception {
        try {
            this.mockMvc.perform(get("/order-manage")
                    .with(user(userService.loadUserByUsername("operator1"))))
                    .andDo(print())
                    .andExpect(jsonPath("$", hasSize(12)));
            String name = "new test order";
            String desc = "new test order desc";
            String cliName ="new test order cli_name";
            String cliTel = "new test order cli_tel";

            OrderDtoCourier orderDto = new OrderDtoCourier();
            orderDto.setName(name);
            orderDto.setDescription(desc);
            orderDto.setClientName(cliName);
            orderDto.setClientTelephone(cliTel);

            ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
            String order = ow.writeValueAsString(orderDto);

            this.mockMvc.perform(post("/order-manage")
                    .contentType(APPLICATION_JSON)
                    .content(order)
                    .with(user(userService.loadUserByUsername("operator1"))))
                    .andDo(print());

            this.mockMvc.perform(get("/order-manage")
                    .with(user(userService.loadUserByUsername("operator1"))))
                    .andDo(print())
                    .andExpect(jsonPath("$", hasSize(13)))
                    .andExpect(jsonPath("$[12].name", Matchers.is(name)))
                    .andExpect(jsonPath("$[12].description", Matchers.is(desc)))
                    .andExpect(jsonPath("$[12].clientName", Matchers.is(cliName)))
                    .andExpect(jsonPath("$[12].clientTelephone", Matchers.is(cliTel)))

            ;

            throw new RuntimeException("rollback for addNewOrder method");
        } catch (RuntimeException e) {
            log.info(e.getMessage());
        }

    }

}

