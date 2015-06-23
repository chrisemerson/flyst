package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.dialog.AddGliderDialog;

public class GlidersFragment extends FlystFragment
{
    private static final String DIALOG_ADD_GLIDER = "dialog_add_glider";
    private static final int REQUEST_ADD_GLIDER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_gliders, container, false);

        Button button = (Button) view.findViewById(R.id.button_add_glider);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AddGliderDialog dialog = new AddGliderDialog();
                dialog.setTargetFragment(GlidersFragment.this, REQUEST_ADD_GLIDER);
                dialog.show(fm, DIALOG_ADD_GLIDER);
            }
        });

        return view;
    }
}
