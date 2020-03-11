package com.interview.testtask;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Slf4j
public class ControllersTests extends com.interview.testtask.TestInit{

    @Test
    @WithUserDetails("admin")
    public void adminPageTest() throws Exception{
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("test order")));
    }

    @Test
    @WithUserDetails("admin")
    public void loadOrdersWithDateRangeOnAdminPage() throws Exception{
        LocalDate from = LocalDate.of(2019,5, 1);
        LocalDate to = LocalDate.of(2019,7, 1);
        this.mockMvc.perform(get("/admin").param("from", from.toString()).param("to", to.toString()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    /*
    2020-03-11 16:59:37.830  WARN 6604 --- [           main] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDate'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [@org.springframework.web.bind.annotation.RequestParam java.time.LocalDate] for value '2019-05-01'; nested exception is java.lang.IllegalArgumentException: Parse attempt failed for value [2019-05-01]]
     */


}
