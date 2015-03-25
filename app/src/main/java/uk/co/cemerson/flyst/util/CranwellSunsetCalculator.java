package uk.co.cemerson.flyst.util;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CranwellSunsetCalculator extends SunriseSunsetCalculator
{
    private static final String CRANWELL_TIMEZONE = "Europe/London";
    private static final String CRANWELL_LATITUDE = "53.041961";
    private static final String CRANWELL_LONGITUDE = "-0.492393";

    public CranwellSunsetCalculator()
    {
        super(new Location(CRANWELL_LATITUDE, CRANWELL_LONGITUDE), CRANWELL_TIMEZONE);
    }

    public String getOfficialSunsetForDate(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return super.getOfficialSunsetForDate(calendar);
    }
}
