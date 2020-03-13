package com.interview.cdekTask;


import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDateTime;
import java.util.regex.Matcher;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Slf4j
public class ControllersTests extends com.interview.cdekTask.TestInit {

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
        log.info("FROM: " + from);
        log.info("TO: " + to);
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
                .andExpect(jsonPath("$[0].created", Matchers.is("2019-05-01 12:00:00")))
                .andExpect(jsonPath("$[1]", hasKey("created")))
                .andExpect(jsonPath("$[1].created", Matchers.is("2019-06-01 12:00:00")))
                .andExpect(jsonPath("$[2]", hasKey("created")))
                .andExpect(jsonPath("$[2].created", Matchers.is("2019-07-01 12:00:00")))
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
        log.info("FROM: " + from);
        log.info("TO: " + to);
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
                .andExpect(jsonPath("$[0].created", Matchers.is("2019-05-01 12:00:00")))
                .andExpect(jsonPath("$[1]", hasKey("created")))
                .andExpect(jsonPath("$[1].created", Matchers.is("2019-06-01 12:00:00")))
                .andExpect(jsonPath("$[2]", hasKey("created")))
                .andExpect(jsonPath("$[2].created", Matchers.is("2019-07-01 12:00:00")))
                .andExpect(jsonPath("$[0].holder", Matchers.not(hasKey("roles"))))
                .andExpect(jsonPath("$[1].holder", Matchers.not(hasKey("roles"))))
                .andExpect(jsonPath("$[2].holder", Matchers.not(hasKey("roles"))))
        ;
    }

    @Test
    @WithUserDetails("courier1")
    public void loadOrdersWithDateRangeOnCouriersPage() throws Exception {
        String from = LocalDateTime.of(2019, 5, 1, 12, 0, 0).toString();
        String to = LocalDateTime.of(2019, 7, 1, 12, 0, 0).toString();
        log.info("FROM: " + from);
        log.info("TO: " + to);
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
                .andExpect(jsonPath("$[0].holder", Matchers.not(hasKey("roles"))))
                .andExpect(jsonPath("$[1].holder", Matchers.not(hasKey("roles"))))
                .andExpect(jsonPath("$[2].holder", Matchers.not(hasKey("roles"))))
        ;
    }

    /**
     * Before courier accepts the order, orders holder is operator.
     * After courier accepts the order,  the courier becomes the holder of the order.
     */
    @Test
    @WithUserDetails("courier1")
    public void whenCourierAcceptsOrderThenOrderChanges() throws Exception {
        int[] id = new int[1];
        String[] exp = new String[3];

        this.mockMvc.perform(get("/order"))
                .andDo(print())
                .andDo(mvcResult -> {
                    id[0] = getIdOfFirstOrder(mvcResult.getResponse().getContentAsString());
                    exp[0] = String.format("$[%d].id", id[0] - 1);
                    exp[1] = String.format("$[%d].holder.id", id[0] - 1);
                    exp[2] = String.format("/order/%d", id[0]);

                })
                .andExpect(jsonPath(exp[0], Matchers.is(id[0])))
                .andExpect(jsonPath(exp[1], Matchers.is(2)))
        ;
        this.mockMvc.perform(get(exp[2]))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasKey("id")))
                .andExpect(jsonPath("$.id", Matchers.is(id[0])))
                .andExpect(jsonPath("$", hasKey("holder")))
                .andExpect(jsonPath("$.holder.id", Matchers.is(3)))
        ;
    }

    @Test
    @WithUserDetails("courier1")
    public void whenCourierCompleteOrderThenListOfOrdersGetShorterByOne() throws Exception {
        this.mockMvc.perform(get("/order"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(12)));

        this.mockMvc.perform(delete("/order/5"))
                .andDo(print());

        this.mockMvc.perform(get("/order"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(11)));
    }





    private int getIdOfFirstOrder(String response) {
        String tmp = response.split(",")[0];
        return Integer.valueOf(tmp.substring(7));
    }
}
