package uk.co.cemerson.flyst.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.entity.Member;
import uk.co.cemerson.flyst.entity.Pilot;
import uk.co.cemerson.flyst.repository.MemberRepository;

public class AddPilotDialog extends DialogFragment
{
    private EditText mSearchBox;
    private ListView mAutoCompleteBox;

    private static final int SEARCH_LIMIT = 6;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_pilot, null);

        initView(view);

        Dialog dialog = new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(getResources().getString(R.string.add_pilot_dialog_title))
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    resetKeyboardForce();
                }
            })
            .create();

        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);

        resetKeyboardForce();
    }

    private void initView(View view)
    {
        mAutoCompleteBox = (ListView) view.findViewById(R.id.pilots_autocomplete_view);
        mSearchBox = (EditText) view.findViewById(R.id.member_search_box);

        forceKeyboardAndFocusOnSearchBox();

        initSearchBoxAutocompleteListener(view);
    }

    private void forceKeyboardAndFocusOnSearchBox()
    {
        mSearchBox.requestFocus();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void resetKeyboardForce()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchBox.getWindowToken(), 0);
    }

    private void initSearchBoxAutocompleteListener(View view)
    {
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
                    setAutoCompleteBoxItems(getMemberRepository().searchForMemberByName(text, SEARCH_LIMIT));
                }
            }
        };

        mSearchBox.addTextChangedListener(textWatcher);
    }

    private void setAutoCompleteBoxItems(List<Member> members)
    {
        if (members == null) {
            mAutoCompleteBox.setAdapter(null);
        } else {
            final ListAdapter adapter = new MemberSearchListAdapter((ArrayList<Member>) members);
            mAutoCompleteBox.setAdapter(adapter);

            mAutoCompleteBox.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Member member = (Member) adapter.getItem(position);

                    if (!getFlyingList().isMemberOnList(member)) {
                        addMemberToFlyingList(member);
                    }
                }
            });
        }
    }

    private void addMemberToFlyingList(Member member)
    {
        getFlyingList().addPilot(new Pilot(member, new Date()));

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent());

        resetKeyboardForce();
        getDialog().dismiss();
        mSearchBox.setText("");
    }

    private class MemberSearchListAdapter extends ArrayAdapter<Member> {
        public MemberSearchListAdapter(ArrayList<Member> searchResults) {
            super(getActivity(), 0, searchResults);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_member_search, null);
            }

            initView(convertView, getItem(position));

            return convertView;
        }

        private void initView(View convertView, final Member member)
        {
            TextView memberNameTextView = (TextView) convertView.findViewById(R.id.member_name);
            memberNameTextView.setText(member.getDisplayName());

            TextView pilotAlreadyOnListTextView = (TextView) convertView.findViewById(R.id.already_on_list);

            if (getFlyingList().isMemberOnList(member)) {
                memberNameTextView.setTextColor(getResources().getColor(R.color.greyed_out_text));
                pilotAlreadyOnListTextView.setVisibility(View.VISIBLE);
            } else {
                memberNameTextView.setTextColor(getResources().getColor(R.color.basic_text));
                pilotAlreadyOnListTextView.setVisibility(View.INVISIBLE);
            }
        }

    }

    public MemberRepository getMemberRepository()
    {
        return MemberRepository.getInstance(getActivity().getApplicationContext());
    }

    public FlyingList getFlyingList()
    {
        return FlyingList.getInstance(new Date());
    }
}
