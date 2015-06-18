package uk.co.cemerson.flyst.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.util.CranwellSunsetCalculator;

public class StatusFragment extends TimeTickReceivingFragment
{
    private View mView;

    private Date mSunsetTime;

    private TextView mDateTextView;
    private TextView mSunsetTextView;
    private TextView mBatteryTextView;
    private TextView mTimeTextView;

    private ImageView mBatteryIconView;

    private static final float BATTERY_EMPTY_THRESHOLD = 3;
    private static final float BATTERY_LOW_THRESHOLD = 20;
    private static final float BATTERY_HALF_THRESHOLD = 60;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_status, container, false);

        setupLayoutElements();
        updateStatus();

        return mView;
    }

    @Override
    protected void onTimeTick()
    {
        updateStatus();
    }

    public void updateStatus()
    {
        updateBatteryStatus();

        final Date currentDate = new Date();

        DateFormat machineReadableDateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat dateFormatter = new SimpleDateFormat("EEEE MMMM d, yyyy", Locale.ENGLISH);
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        if (!machineReadableDateFormatter.format(currentDate).equals(machineReadableDateFormatter.format(mSunsetTime))) {
            initSunsetTime();
        }

        mDateTextView.setText(dateFormatter.format(currentDate));
        mTimeTextView.setText(timeFormatter.format(currentDate));
        mSunsetTextView.setText(timeFormatter.format(mSunsetTime) + getTimeToSunsetAsDifferenceString(currentDate));
    }

    private void updateBatteryStatus()
    {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getActivity().registerReceiver(null, filter);

        try {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            int batteryLevel = Math.round(100 * level / (float) scale);
            mBatteryTextView.setText(batteryLevel + "%");

            int batteryResource = R.drawable.battery_full;

            if (batteryLevel < BATTERY_HALF_THRESHOLD) {
                batteryResource = R.drawable.battery_half;
            }

            if (batteryLevel < BATTERY_LOW_THRESHOLD) {
                batteryResource = R.drawable.battery_low;
            }

            if (batteryLevel < BATTERY_EMPTY_THRESHOLD) {
                batteryResource = R.drawable.battery_empty;
            }

            mBatteryIconView.setImageResource(batteryResource);
        } catch (NullPointerException e) {
            Log.e("uk.co.cemerson.flyst", "Error updating battery level", e);
        }
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
        mBatteryTextView = (TextView) mView.findViewById(R.id.fragment_status_label_battery);

        mBatteryIconView = (ImageView) mView.findViewById(R.id.batteryIcon);

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
