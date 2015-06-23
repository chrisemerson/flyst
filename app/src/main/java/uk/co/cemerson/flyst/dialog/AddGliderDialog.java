package uk.co.cemerson.flyst.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uk.co.cemerson.flyst.R;
import uk.co.cemerson.flyst.entity.FlyingList;
import uk.co.cemerson.flyst.entity.Glider;
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
    }

    private List<Glider> getClubGliders()
    {
        return getAllGlidersNotOnFlyingList(getGliderRepository().getAllClubGliders());
    }

    private List<Glider> getOtherGliders()
    {
        return getAllGlidersNotOnFlyingList(getGliderRepository().getAllNonClubGliders());
    }

    private List<Glider> getAllGlidersNotOnFlyingList(List<Glider> gliders)
    {
        FlyingList flyingList = getFlyingList();

        List<Glider> glidersNotOnList = new ArrayList<>();

        for (Glider glider : gliders) {
            if (!flyingList.isGliderOnList(glider)) {
                glidersNotOnList.add(glider);
            }
        }

        return glidersNotOnList;
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
