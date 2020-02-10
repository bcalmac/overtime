package io.github.bcalmac.overtime.server.dao;

import io.github.bcalmac.overtime.server.AbstractIT;
import io.github.bcalmac.overtime.server.service.Shift;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static io.github.bcalmac.overtime.server.utils.OvertimeUtils.hours;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ShiftDaoTest extends AbstractIT {

    @Autowired
    ShiftDao shiftDao;

    @Test
    void crud() {
        Shift shift = shift();
        assertEquals(1, shiftDao.insert(shift));
        assertEquals(shift, shiftDao.select(shift.getDate()));
        Shift modifiedShift = shift.toBuilder()
                .extraHours(shift.getExtraHours().add(hours(0.5)))
                .build();
        assertEquals(1, shiftDao.update(modifiedShift));
        assertEquals(modifiedShift, shiftDao.select(shift.getDate()));
        assertEquals(1, shiftDao.delete(shift.getDate()));
        assertEquals(0, shiftDao.delete(shift.getDate()));
        assertNull(shiftDao.select(shift.getDate()));
    }

    @Test
    void crud_upsert() {
        Shift shift = shift();
        assertEquals(1, shiftDao.upsert(shift));
        assertEquals(shift, shiftDao.select(shift.getDate()));
        Shift modifiedShift = shift.toBuilder()
                .extraHours(shift.getExtraHours().add(hours(0.5)))
                .build();
        assertEquals(1, shiftDao.upsert(modifiedShift));
        assertEquals(modifiedShift, shiftDao.select(shift.getDate()));
        assertEquals(1, shiftDao.delete(shift.getDate()));
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
    }
}