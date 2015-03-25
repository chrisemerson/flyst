package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.cemerson.flyst.util.CranwellSunsetCalculator;
import uk.co.cemerson.flyst.R;

public class StatusFragment extends TimeTickReceivingFragment
{
    private View mView;

    private Date mSunsetTime;

    private TextView mDateTextView;
    private TextView mSunsetTextView;
    private TextView mTimeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_status, container, false);

        setupLayoutElements();
        updateStatusTimes();

        return mView;
    }

    @Override
    protected void onTimeTick()
    {
        updateStatusTimes();
    }

    public void updateStatusTimes()
    {
        final Date currentDate = new Date();

        DateFormat machineReadableDateFormatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        DateFormat dateFormatter = new SimpleDateFormat("EEEE MMMM d, yyyy", Locale.ENGLISH);
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        if (!machineReadableDateFormatter.format(currentDate).equals(machineReadableDateFormatter.format(mSunsetTime))) {
            initSunsetTime();
        }

        mDateTextView.setText(dateFormatter.format(currentDate));
        mTimeTextView.setText(timeFormatter.format(currentDate));
        mSunsetTextView.setText(timeFormatter.format(mSunsetTime) + getTimeToSunsetAsDifferenceString(currentDate));
    }

    private String getTimeToSunsetAsDifferenceString(Date measureFrom)
    {
        long diffInMS = mSunsetTime.getTime() - measureFrom.getTime();

        String differenceString = "";

        if (diffInMS > 0) {
            differenceString += " (";

            int minutes = (int) Math.floor((double)(diffInMS / 60000) + 1);
            int hours = (int) Math.floor(minutes / 60);
            minutes -= hours * 60;

            if (hours > 0) {
                differenceString +=
                    hours
                        + getResources().getString(R.string.hour_indicator)
                        + " ";
            }

            differenceString +=
                String.format("%02d", minutes)
                    + getResources().getString(R.string.minute_indicator)
                    + " "
                    + getResources().getString(R.string.label_from_now)
                    + ")";
        }

        return differenceString;
    }

    private void setupLayoutElements()
    {
        mDateTextView = (TextView) mView.findViewById(R.id.fragment_status_label_date);
        mSunsetTextView = (TextView) mView.findViewById(R.id.fragment_status_sunset_text);
        mTimeTextView = (TextView) mView.findViewById(R.id.fragment_status_label_time);

        if (mSunsetTime == null) {
            initSunsetTime();
        }
    }

    private void initSunsetTime()
    {
        try {
            CranwellSunsetCalculator sunsetCalculator = new CranwellSunsetCalculator();
            final Date currentDate = new Date();

            String sunsetForDate = sunsetCalculator.getOfficialSunsetForDate(currentDate);

            DateFormat currentDateFormat = new SimpleDateFormat("yyyy/MM/dd ss", Locale.ENGLISH);
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd ss hh:mm", Locale.ENGLISH);
            mSunsetTime = format.parse(currentDateFormat.format(currentDate) + " " + sunsetForDate);
        } catch (ParseException exception) {
            mSunsetTime = new Date();
        }
    }
}
