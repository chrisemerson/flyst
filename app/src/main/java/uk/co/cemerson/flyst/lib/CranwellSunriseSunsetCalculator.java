package uk.co.cemerson.flyst.lib;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CranwellSunriseSunsetCalculator extends SunriseSunsetCalculator
{
    private static final String CranwellTimezone = "Europe/London";
    private static final String CranwellLatitude = "53.041961";
    private static final String CranwellLongitude = "-0.492393";

    public CranwellSunriseSunsetCalculator()
    {
        super(new Location(CranwellLatitude, CranwellLongitude), CranwellTimezone);
    }

    public String getOfficialSunriseForDate(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return super.getOfficialSunriseForDate(calendar);
    }

    public String getOfficialSunsetForDate(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return super.getOfficialSunsetForDate(calendar);
    }
}
