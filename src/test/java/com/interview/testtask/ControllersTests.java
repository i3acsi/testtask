package com.interview.testtask;


import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Slf4j
public class ControllersTests extends com.interview.testtask.TestInit {

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
        String from = LocalDate.of(2019, 5, 1).toString();
        String to = LocalDate.of(2019, 7, 1).toString();
        log.info("FROM: " + from);
        log.info("TO: " + to);
        this.mockMvc.perform(get("/admin").param("from", from).param("to", to))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(6)))
                .andExpect(jsonPath("$[1].id", Matchers.is(7)))
                .andExpect(jsonPath("$[2].id", Matchers.is(8)))
                .andExpect(jsonPath("$[0].name", Matchers.is("test order 5")))
                .andExpect(jsonPath("$[1].name", Matchers.is("test order 6")))
                .andExpect(jsonPath("$[2].name", Matchers.is("test order 7")))
                .andExpect(jsonPath("$[0]", hasKey("createdDate")))
                .andExpect(jsonPath("$[0].createdDate", Matchers.is("2019-05-01")))
                .andExpect(jsonPath("$[1]", hasKey("createdDate")))
                .andExpect(jsonPath("$[1].createdDate", Matchers.is("2019-06-01")))
                .andExpect(jsonPath("$[2]", hasKey("createdDate")))
                .andExpect(jsonPath("$[2].createdDate", Matchers.is("2019-07-01")))
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
        String from = LocalDate.of(2019, 5, 1).toString();
        String to = LocalDate.of(2019, 7, 1).toString();
        log.info("FROM: " + from);
        log.info("TO: " + to);
        this.mockMvc.perform(get("/order-manage").param("from", from).param("to", to))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(6)))
                .andExpect(jsonPath("$[1].id", Matchers.is(7)))
                .andExpect(jsonPath("$[2].id", Matchers.is(8)))
                .andExpect(jsonPath("$[0].name", Matchers.is("test order 5")))
                .andExpect(jsonPath("$[1].name", Matchers.is("test order 6")))
                .andExpect(jsonPath("$[2].name", Matchers.is("test order 7")))
                .andExpect(jsonPath("$[0]", hasKey("createdDate")))
                .andExpect(jsonPath("$[0].createdDate", Matchers.is("2019-05-01")))
                .andExpect(jsonPath("$[1]", hasKey("createdDate")))
                .andExpect(jsonPath("$[1].createdDate", Matchers.is("2019-06-01")))
                .andExpect(jsonPath("$[2]", hasKey("createdDate")))
                .andExpect(jsonPath("$[2].createdDate", Matchers.is("2019-07-01")))
                .andExpect(jsonPath("$[0].holder", Matchers.not(hasKey("roles"))))
                .andExpect(jsonPath("$[1].holder", Matchers.not(hasKey("roles"))))
                .andExpect(jsonPath("$[2].holder", Matchers.not(hasKey("roles"))))
        ;
    }

    @Test
    @WithUserDetails("courier1")
    public void loadOrdersWithDateRangeOnCouriersPage() throws Exception {
        String from = LocalDate.of(2019, 5, 1).toString();
        String to = LocalDate.of(2019, 7, 1).toString();
        log.info("FROM: " + from);
        log.info("TO: " + to);
        this.mockMvc.perform(get("/order").param("from", from).param("to", to))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(6)))
                .andExpect(jsonPath("$[1].id", Matchers.is(7)))
                .andExpect(jsonPath("$[2].id", Matchers.is(8)))
                .andExpect(jsonPath("$[0].name", Matchers.is("test order 5")))
                .andExpect(jsonPath("$[1].name", Matchers.is("test order 6")))
                .andExpect(jsonPath("$[2].name", Matchers.is("test order 7")))
                .andExpect(jsonPath("$[0]", Matchers.not(hasKey("createdDate"))))
                .andExpect(jsonPath("$[1]", Matchers.not(hasKey("createdDate"))))
                .andExpect(jsonPath("$[2]", Matchers.not(hasKey("createdDate"))))
                .andExpect(jsonPath("$[0].holder", Matchers.not(hasKey("roles"))))
                .andExpect(jsonPath("$[1].holder", Matchers.not(hasKey("roles"))))
                .andExpect(jsonPath("$[2].holder", Matchers.not(hasKey("roles"))))
        ;
    }

}
