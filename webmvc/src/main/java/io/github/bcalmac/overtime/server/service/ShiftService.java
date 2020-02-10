package io.github.bcalmac.overtime.server.service;

import io.github.bcalmac.overtime.server.dao.ShiftDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShiftService {

    private static final Logger logger = LoggerFactory.getLogger(ShiftService.class);

    private final ShiftDao shiftDao;

    public ShiftService(ShiftDao shiftDao) {
        this.shiftDao = shiftDao;
    }

    public List<Shift> getRecentShifts() {
        return shiftDao.selectRecent();
    }

    public Shift getShift(LocalDate date) {
        Shift result = shiftDao.select(date);
        if (result != null) {
            return result;
        }
        return emptyShift(date);
    }

    public Shift emptyShift() {
        return emptyShift(LocalDate.now());
    }

    private Shift emptyShift(LocalDate date) {
        return Shift.of(date);
    }

    public void storeShift(Shift shift) {
        shiftDao.upsert(shift);
        logger.info("Stored {}", shift);
    }

    public void deleteShift(LocalDate date) {
        shiftDao.delete(date);
        logger.info("Deleted shift for {}", date);
    }
}
