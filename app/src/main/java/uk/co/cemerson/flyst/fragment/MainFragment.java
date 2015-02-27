package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import uk.co.cemerson.flyst.lib.CranwellSunriseSunsetCalculator;
import uk.co.cemerson.flyst.R;

public class MainFragment extends Fragment
{
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        setupLayoutElements();

        return mView;
    }

    private void setupLayoutElements()
    {
        final Date currentDate = new Date();
        TextView currentDateTextView = (TextView) mView.findViewById(R.id.fragment_main_label_date);

        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.FULL);
        currentDateTextView.setText(dateFormatter.format(currentDate));

        TextView sunsetTextView = (TextView) mView.findViewById(R.id.fragment_main_label_sunset);

        CranwellSunriseSunsetCalculator sunsetCalculator = new CranwellSunriseSunsetCalculator();
        sunsetTextView.setText(getResources().getString(R.string.label_sunset) + sunsetCalculator.getOfficialSunsetForDate(currentDate));
    }
}
