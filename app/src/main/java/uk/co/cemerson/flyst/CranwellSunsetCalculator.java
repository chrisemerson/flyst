package uk.co.cemerson.flyst;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CranwellSunsetCalculator extends SunriseSunsetCalculator
{
    private static final String CranwellTimezone = "Europe/London";
    private static final String CranwellLatitude = "53.041961";
    private static final String CranwellLongitude = "-0.492393";

    public CranwellSunsetCalculator()
    {
        super(new Location(CranwellLatitude, CranwellLongitude), CranwellTimezone);
    }

    public String getOfficialSunsetForDate(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return super.getOfficialSunsetForDate(calendar);
    }
}
