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
        ArrayAdapter<Pilot> adapter = new ArrayAdapter<>(
            getActivity(),
            android.R.layout.simple_list_item_1,
            getFlyingList().getPilots()
        );

        mPilotsOnFlyingList.setAdapter(adapter);

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

    private void updatePilotsOnFlyingListView()
    {
        BaseAdapter adapter = (BaseAdapter) mPilotsOnFlyingList.getAdapter();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
