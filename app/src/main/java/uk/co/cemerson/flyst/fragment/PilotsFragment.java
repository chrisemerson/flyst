package uk.co.cemerson.flyst.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.dialog.AddPilotDialog;
import uk.co.cemerson.flyst.entity.Pilot;

public class PilotsFragment extends FlystFragment
{
    private ListView mPilotsOnFlyingList;

    private static final String DIALOG_ADD_PILOT = "dialog_add_pilot";
    private static final int REQUEST_ADD_PILOT = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pilots, container, false);

        mPilotsOnFlyingList = (ListView) view.findViewById(R.id.pilots_list_view);

        Button button = (Button) view.findViewById(R.id.button_add_pilot);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AddPilotDialog dialog = new AddPilotDialog();
                dialog.setTargetFragment(PilotsFragment.this, REQUEST_ADD_PILOT);
                dialog.show(fm, DIALOG_ADD_PILOT);
            }
        });

        initFlyingListPilotsListAdapter();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_ADD_PILOT) {
            updatePilotsOnFlyingListView();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        updatePilotsOnFlyingListView();
    }

    private void initFlyingListPilotsListAdapter()
    {
        mPilotsOnFlyingList.setAdapter(new PilotListAdapter((ArrayList<Pilot>) getFlyingList().getPilots()));

        mPilotsOnFlyingList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                getFlyingList().deletePilot((Pilot) mPilotsOnFlyingList.getAdapter().getItem(position));
                updatePilotsOnFlyingListView();
            }
        });
    }

    private class PilotListAdapter extends ArrayAdapter<Pilot> {
        public PilotListAdapter(ArrayList<Pilot> pilots) {
            super(getActivity(), 0, pilots);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_pilot, null);
            }

            Pilot pilot = getItem(position);

            TextView pilotNameTextView = (TextView) convertView.findViewById(R.id.pilot_name);
            pilotNameTextView.setText(pilot.getDisplayName());

            TextView pilotTimeOnListTextView = (TextView) convertView.findViewById(R.id.time_on_list);
            pilotTimeOnListTextView.setText(pilot.getDateAdded().toString());

            return convertView;
        }
    }

    private void updatePilotsOnFlyingListView()
    {
        BaseAdapter adapter = (BaseAdapter) mPilotsOnFlyingList.getAdapter();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
