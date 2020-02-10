package io.github.bcalmac.overtime.server.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.math.BigDecimal.ZERO;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class Shift {

    private static final DateTimeFormatter SHORT_DATE = DateTimeFormatter.ofPattern("MM/dd");
    private static final DateTimeFormatter US_DATE = DateTimeFormatter.ofPattern("MM/dd/yy");

    @PastOrPresent
    LocalDate date;

    @DecimalMin(value = "1")
    @DecimalMax(value = "24")
    BigDecimal scheduledHours;

    @DecimalMin(value = "-24")
    @DecimalMax(value = "24")
    BigDecimal extraHours;

    @Min(0)
    @Max(40)
    int patients;

    @Min(0)
    @Max(20)
    int admissions;

    @Min(0)
    @Max(20)
    int discharges;

    @Min(0)
    @Max(20)
    int icuPatients;

    @Size(max = 1024)
    String notes;


    public static Shift of(LocalDate date) {
        return Shift.builder().date(date).scheduledHours(ZERO).extraHours(ZERO).build();
    }

    public String getDateAsShort() {
        return date.format(SHORT_DATE);
    }

    public String getDateAsUs() {
        return date.format(US_DATE);
    }

    public String getDateAsIso() {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getHoursSummary() {
        return String.format("%s %s%s",
                toPlainString(scheduledHours),
                extraHours.signum() == -1 ? "" : "+",
                toPlainString(extraHours));
    }

    private static String toPlainString(BigDecimal bigDecimal) {
        return bigDecimal.stripTrailingZeros().toPlainString();
    }

    public String getPatientsSummary() {
        return String.format("%d +%d -%d", patients, admissions, discharges);
    }
}
