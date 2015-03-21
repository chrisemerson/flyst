package uk.co.cemerson.flyst.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.entity.Member;

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

        mSearchBox = (EditText) view.findViewById(R.id.member_search_box);
        mSearchBox.addTextChangedListener(textWatcher);

        return view;
    }

    @Override
    public void onFlyingListUpdate(FlyingList flyingList)
    {}

    public TextWatcher textWatcher = new TextWatcher()
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
                mAutoCompleteBox.setVisibility(View.GONE);
            } else {
                mAutoCompleteBox.setVisibility(View.VISIBLE);

                List<Member> searchResults = mMemberRepository.searchForMemberByName(text);
            }
        }
    };
}
