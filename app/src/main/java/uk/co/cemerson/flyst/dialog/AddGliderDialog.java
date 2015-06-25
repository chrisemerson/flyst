package uk.co.cemerson.flyst.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.entity.Glider;
import uk.co.cemerson.flyst.entity.GliderQueue;
import uk.co.cemerson.flyst.repository.FlyingListRepository;
import uk.co.cemerson.flyst.repository.GliderRepository;

public class AddGliderDialog extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_glider, null);

        initView(view);

        return new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(getResources().getString(R.string.add_glider_to_list_dialog_title))
            .setNegativeButton(android.R.string.cancel, null)
            .create();
    }

    private void initView(View view)
    {
        GridView clubGlidersView = (GridView) view.findViewById(R.id.club_gliders);
        GridView otherGlidersView = (GridView) view.findViewById(R.id.other_gliders);

        clubGlidersView.setAdapter(new GliderGridAdapter((ArrayList<Glider>) getClubGliders()));
        otherGlidersView.setAdapter(new GliderGridAdapter((ArrayList<Glider>) getOtherGliders()));

        clubGlidersView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Glider glider = (Glider) parent.getItemAtPosition(position);

                if (!getFlyingList().isGliderOnList(glider)) {
                    addGliderToFlyingList((Glider) parent.getItemAtPosition(position));
                }
            }
        });
    }

    private void addGliderToFlyingList(Glider glider)
    {
        getFlyingList().addGliderQueue(new GliderQueue(getActivity().getApplicationContext(), glider));
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent());
        getDialog().dismiss();
    }

    private List<Glider> getClubGliders()
    {
        return getGliderRepository().getAllClubGliders();
    }

    private List<Glider> getOtherGliders()
    {
        return getGliderRepository().getAllNonClubGliders();
    }

    private class GliderGridAdapter extends ArrayAdapter<Glider>
    {
        public GliderGridAdapter(ArrayList<Glider> gliders) {
            super(getActivity(), 0, gliders);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.grid_item_glider, null);
            }

            initView(convertView, getItem(position));

            return convertView;
        }

        private void initView(View view, Glider glider)
        {
            TextView gliderRegistrationTextView = (TextView) view.findViewById(R.id.glider_registration);
            TextView gliderTypeTextView = (TextView) view.findViewById(R.id.glider_type);

            gliderRegistrationTextView.setText(glider.getRegistration());
            gliderTypeTextView.setText(glider.getType());

            if (getFlyingList().isGliderOnList(glider)) {
                gliderRegistrationTextView.setTextColor(getResources().getColor(R.color.greyed_out_text));
                gliderTypeTextView.setTextColor(getResources().getColor(R.color.greyed_out_text));
            }
        }
    }

    private GliderRepository getGliderRepository()
    {
        return GliderRepository.getInstance(getActivity().getApplicationContext());
    }

    private FlyingList getFlyingList()
    {
        FlyingListRepository flyingListRepository = FlyingListRepository.getInstance(getActivity().getApplicationContext());

        return flyingListRepository.getCurrentFlyingList();
    }
}
