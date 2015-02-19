package uk.co.cemerson.flyst.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

import uk.co.cemerson.flyst.Entity.FlyingList;
import uk.co.cemerson.flyst.R;

public class LaunchFragment extends Fragment
{
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_launch, container);

        setupLayoutElements();

        return mView;
    }

    private void setupLayoutElements()
    {
        Button dateSelectorButton = (Button) mView.findViewById(R.id.fragment_launch_date_selector_button);
        Button goButton = (Button) mView.findViewById(R.id.fragment_launch_go_button);

        goButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FlyingList flyingList = new FlyingList(new Date());
            }
        });
    }
}
