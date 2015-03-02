package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.cemerson.flyst.CranwellSunsetCalculator;
import uk.co.cemerson.flyst.R;

public class StatusFragment extends Fragment
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

    public void updateStatusTimes()
    {
        final Date currentDate = new Date();

        DateFormat dateFormat = new SimpleDateFormat("EEEE MMMM d, yyyy", Locale.ENGLISH);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        long diffInMS = mSunsetTime.getTime() - currentDate.getTime();

        String differenceString = "";

        if (diffInMS > 0) {
            differenceString += " (";

            int minutes = (int) Math.ceil((double) (diffInMS / 60000)) + 1;
            int hours = (int) Math.floor(minutes / 60);
            minutes -= hours * 60;

            if (hours > 0) {
                differenceString += hours + "h ";
            }

            differenceString += minutes + "m from now)";
        }

        mDateTextView.setText(dateFormat.format(currentDate));
        mTimeTextView.setText(timeFormat.format(currentDate));
        mSunsetTextView.setText(getResources().getString(R.string.label_sunset) + timeFormat.format(mSunsetTime) + differenceString);
    }

    private void setupLayoutElements()
    {
        mDateTextView = (TextView) mView.findViewById(R.id.fragment_status_label_date);
        mSunsetTextView = (TextView) mView.findViewById(R.id.fragment_status_label_sunset);
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
