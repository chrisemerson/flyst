package uk.co.cemerson.flyst.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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

    private static final int SEARCH_LIMIT = 5;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_pilot, null);

        initView(view);

        Dialog dialog = new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(getResources().getString(R.string.add_pilot_dialog_title))
            .setNegativeButton(android.R.string.cancel, null)
            .create();

        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    private void initView(View view)
    {
        mAutoCompleteBox = (ListView) view.findViewById(R.id.pilots_autocomplete_view);
        mSearchBox = (EditText) view.findViewById(R.id.member_search_box);

        initSearchBoxAutocompleteListener(view);
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

                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent());
                    getDialog().dismiss();

                    mSearchBox.setText("");
                }
            });
        }
    }

    public MemberRepository getMemberRepository()
    {
        return new MemberRepository();
    }

    public FlyingList getFlyingList()
    {
        return FlyingList.getInstance(new Date());
    }
}
