package uk.co.cemerson.flyst.fragment.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.dialog.EditGliderDialog;
import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.entity.Glider;
import uk.co.cemerson.flyst.repository.FlyingListRepository;
import uk.co.cemerson.flyst.repository.GliderRepository;

public class GliderSettingsFragment extends Fragment
{
    private static final int REQUEST_EDIT_GLIDER = 0;
    private static final String DIALOG_EDIT_GLIDER = "dialog_edit_glider";

    private ListView mGlidersList;
    private GliderRepository mGliderRepository;
    private ArrayList<Glider> mAllGliders;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings_gliders, container, false);

        initView(view);

        return view;
    }

    private void initView(View view)
    {
        mGliderRepository = GliderRepository.getInstance(getActivity().getApplicationContext());
        mAllGliders = (ArrayList<Glider>) mGliderRepository.getAllGliders();

        Collections.sort(mAllGliders);

        mGlidersList = (ListView) view.findViewById(R.id.gliders_list);
        mGlidersList.setAdapter(new GlidersByRegistrationAdapter(mAllGliders));

        Button addGliderButton = (Button) view.findViewById(R.id.button_add_glider);
        addGliderButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();

                EditGliderDialog dialog = EditGliderDialog.newInstance();
                dialog.setTargetFragment(GliderSettingsFragment.this, REQUEST_EDIT_GLIDER);
                dialog.show(fm, DIALOG_EDIT_GLIDER);
            }
        });
    }

    private class GlidersByRegistrationAdapter extends ArrayAdapter<Glider>
    {
        public GlidersByRegistrationAdapter(ArrayList<Glider> gliders)
        {
            super(getActivity(), 0, gliders);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_glider, null);
            }

            initView(convertView, getItem(position));

            return convertView;
        }

        private void initView(View view, final Glider glider)
        {
            TextView gliderRegistrationTextView = (TextView) view.findViewById(R.id.glider_registration);
            gliderRegistrationTextView.setText(glider.getRegistration());

            TextView gliderTypeTextView = (TextView) view.findViewById(R.id.glider_type);
            gliderTypeTextView.setText(glider.getType());

            ImageButton editGliderButton = (ImageButton) view.findViewById(R.id.button_edit_glider);
            editGliderButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    FragmentManager fm = getActivity().getSupportFragmentManager();

                    EditGliderDialog dialog = EditGliderDialog.newInstance(glider);
                    dialog.setTargetFragment(GliderSettingsFragment.this, REQUEST_EDIT_GLIDER);
                    dialog.show(fm, DIALOG_EDIT_GLIDER);
                }
            });

            ImageButton deleteGliderButton = (ImageButton) view.findViewById(R.id.button_delete_glider);
            deleteGliderButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    FlyingList flyingList = FlyingListRepository.getInstance(getContext()).getCurrentFlyingList();
                    String message =
                        "Are you sure you want to remove "
                        + glider.getType() + " '" + glider.getRegistration() + "'"
                        + "? This action cannot be undone.";

                    if (flyingList.isGliderOnList(glider)) {
                        message +=
                            "\n\n"
                            + "This will also remove this glider from the flying list.";
                    }

                    new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Glider")
                        .setMessage(message)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                removeGlider(glider);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                }
            });
        }
    }

    private void removeGlider(Glider glider)
    {
        mGliderRepository.deleteGlider(glider);
        refreshGliderList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_EDIT_GLIDER) {
            refreshGliderList();
        }
    }

    private void refreshGliderList()
    {
        mAllGliders = (ArrayList<Glider>) mGliderRepository.getAllGliders();
        Collections.sort(mAllGliders);
        ((ArrayAdapter) mGlidersList.getAdapter()).notifyDataSetChanged();
    }
}
