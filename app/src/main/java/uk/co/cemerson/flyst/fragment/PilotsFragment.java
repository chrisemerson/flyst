package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.Member;
import uk.co.cemerson.flyst.entity.Pilot;

public class PilotsFragment extends FlystFragment
{
    private EditText mSearchBox;
    private ListView mAutoCompleteBox;
    private ListView mPilotsOnFlyingList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pilots, container, false);

        mAutoCompleteBox = (ListView) view.findViewById(R.id.pilots_autocomplete_view);
        mPilotsOnFlyingList = (ListView) view.findViewById(R.id.pilots_list_view);

        initSearchBoxAutocompleteListener(view);
        initFlyingListPilotsListAdapter();

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        updatePilotsOnFlyingListView();
    }

    private void initSearchBoxAutocompleteListener(View view)
    {
        mSearchBox = (EditText) view.findViewById(R.id.member_search_box);

        TextWatcher textWatcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {}

            @Override
            public void afterTextChanged(Editable s)
            {
                String text = mSearchBox.getText().toString();

                if (text.equals("")) {
                    setAutoCompleteBoxItems(null);
                } else {
                    setAutoCompleteBoxItems(getMemberRepository().searchForMemberByName(text));
                }
            }
        };

        mSearchBox.addTextChangedListener(textWatcher);
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

    private void setAutoCompleteBoxItems(List<Member> members)
    {
        if (members == null) {
            mAutoCompleteBox.setVisibility(View.GONE);
        } else {
            mAutoCompleteBox.setVisibility(View.VISIBLE);

            ArrayAdapter<Member> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                members
            );

            mAutoCompleteBox.setAdapter(adapter);

            mAutoCompleteBox.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    getFlyingList().addPilot(new Pilot((Member) mAutoCompleteBox.getAdapter().getItem(position)));

                    resetPilotSearchBox();
                }
            });
        }
    }

    private void resetPilotSearchBox()
    {
        mSearchBox.setText("");
        mAutoCompleteBox.setVisibility(View.GONE);
    }

    private void updatePilotsOnFlyingListView()
    {
        BaseAdapter adapter = (BaseAdapter) mPilotsOnFlyingList.getAdapter();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
