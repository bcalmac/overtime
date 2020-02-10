package io.github.bcalmac.overtime.server.controller;

import io.github.bcalmac.overtime.server.AbstractIT;
import io.github.bcalmac.overtime.server.dao.ShiftDao;
import io.github.bcalmac.overtime.server.service.Shift;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;

import static io.github.bcalmac.overtime.server.utils.OvertimeUtils.hours;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShiftsControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ShiftDao shiftDao;

    @Test
    void shiftForm() throws Exception {
        mockMvc.perform(get("/shifts/new"))
                .andExpect(content().string(containsString("Shift Submission")));
    }

    @Test
    void storeShift() throws Exception {
        Shift shift = shift();
        MockHttpServletRequestBuilder request = post("/shifts/submit")
                .param("date", shift.getDateAsUs())
                .param("scheduledHours", String.valueOf(shift.getScheduledHours()))
                .param("extraHours", shift.getExtraHours().toString())
                .param("patients", String.valueOf(shift.getPatients()))
                .param("admissions", String.valueOf(shift.getAdmissions()))
                .param("discharges", String.valueOf(shift.getDischarges()))
                .param("icuPatients", String.valueOf(shift.getIcuPatients()));
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/shifts/list"));

        assertTrue(shiftDao.exists(shift.getDate()));
    }

    private Shift shift() {
        return Shift.builder()
                .date(LocalDate.of(2001, 2, 3))
                .scheduledHours(hours(8))
                .extraHours(hours(2.25))
                .patients(10)
                .admissions(2)
                .discharges(1)
                .icuPatients(0)
                .build();
    }}