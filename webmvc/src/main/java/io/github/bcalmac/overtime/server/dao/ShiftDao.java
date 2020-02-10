package io.github.bcalmac.overtime.server.dao;

import io.github.bcalmac.overtime.server.service.Shift;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Component // optional, just to avoid Intellij autowiring warnings
public interface ShiftDao {

    @Insert("insert into shift values (#{date}, #{scheduledHours}, #{extraHours}, #{patients}, #{admissions}, #{discharges}, #{icuPatients}, #{notes})")
    int insert(Shift shift);

    @Update("update shift set " +
            "   scheduled_hours = #{scheduledHours}, " +
            "   extra_hours = #{extraHours}, " +
            "   patients = #{patients}, " +
            "   admissions = #{admissions}, " +
            "   discharges = #{discharges}, " +
            "   icu_patients = #{icuPatients}, " +
            "   notes = #{notes} " +
           "where date = #{date}")
    int update(Shift shift);

    // TODO implement upsert in SQL. retire update.
    default int upsert(Shift shift) {
        if (exists(shift.getDate())) {
            return update(shift);
        } else {
            return insert(shift);
        }
    }

    default boolean exists(LocalDate date) {
        return select(date) != null;
    }

    @Delete("delete from shift where date = #{date}")
    int delete(LocalDate date);

    @Select("select * from shift where date = #{date}")
    Shift select(LocalDate date);

    @Select("select * from shift order by date desc limit 10")
    List<Shift> selectRecent();

}
