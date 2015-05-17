package uk.co.cemerson.flyst.entity;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.cemerson.flyst.repository.FlyingListRepository;
import uk.co.cemerson.flyst.repository.JSONSerializable;

public class FlyingList implements JSONSerializable
{
    private static final String LOG_TAG = "uk.co.cemerson.flyst";

    private static final String JSON_KEY_FLYING_LIST_DATE = "flyinglistdate";
    private static final String JSON_KEY_PILOTS = "pilots";
    private static final String JSON_KEY_GLIDERS = "gliders";

    private Context mContext;
    private Date mFlyingListDate;

    private List<Pilot> mPilots = new ArrayList<>();
    private List<Glider> mGliders = new ArrayList<>();

    public FlyingList(Context context, Date flyingListDate)
    {
        mContext = context;
        mFlyingListDate = flyingListDate;
    }

    public FlyingList(Context context, JSONObject jsonObject)
    {
        mContext = context;

        try {
            mFlyingListDate = new Date(jsonObject.getLong(JSON_KEY_FLYING_LIST_DATE));
            JSONArray pilotsArray = jsonObject.getJSONArray(JSON_KEY_PILOTS);

            for (int i = 0; i < pilotsArray.length(); i++) {
                mPilots.add(new Pilot(mContext, pilotsArray.getJSONObject(i)));
            }

//            JSONArray glidersArray = jsonObject.getJSONArray(JSON_KEY_GLIDERS);
//
//            for (int i = 0; i < glidersArray.length(); i++) {
//                mGliders.add(new Glider(mContext, glidersArray.getJSONObject(i)));
//            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error decoding flying list information: ", e);
        }

    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_KEY_FLYING_LIST_DATE, mFlyingListDate.getTime());
        jsonObject.put(JSON_KEY_PILOTS, getPilotsJSONArray());
        jsonObject.put(JSON_KEY_GLIDERS, getGlidersJSONArray());

        return jsonObject;
    }

    private JSONArray getPilotsJSONArray() throws JSONException
    {
        JSONArray pilots = new JSONArray();

        for (Pilot pilot : mPilots) {
            pilots.put(pilot.toJSON());
        }

        return pilots;
    }

    private JSONArray getGlidersJSONArray() throws JSONException
    {
        JSONArray gliders = new JSONArray();

        //Don't worry about gliders for now until we have implemented them a bit better

//        for (Glider glider : mGliders) {
//            gliders.put(glider.toJSON());
//        }

        return gliders;
    }

    private void save()
    {
        FlyingListRepository flyingListRepository = FlyingListRepository.getInstance(mContext);
        flyingListRepository.save(this);
    }

    public Date getFlyingListDate()
    {
        return mFlyingListDate;
    }

    public void addPilot(Pilot pilot)
    {
        mPilots.add(pilot);
        save();
    }

    public void removePilot(Pilot pilot)
    {
        mPilots.remove(pilot);
        save();
    }

    public List<Pilot> getPilots()
    {
        return mPilots;
    }

    public void addGlider(Glider glider)
    {
        mGliders.add(glider);
        save();
    }

    public void removeGlider(Glider glider)
    {
        mGliders.remove(glider);
        save();
    }

    public List<Glider> getGliders()
    {
        return mGliders;
    }

    public int getNumberOfPilotsOnList()
    {
        return mPilots.size();
    }

    public int getNumberOfPilotsOnListWhoHaveFlown()
    {
        int numberOfPilotsFlown = 0;

        for (Pilot pilot : mPilots) {
            if (pilot.hasFlown()) {
                numberOfPilotsFlown++;
            }
        }

        return numberOfPilotsFlown;
    }

    public boolean isMemberOnList(Member member)
    {
        for (Pilot pilot : mPilots) {
            if (pilot.getMember() == member) {
                return true;
            }
        }

        return false;
    }

    public boolean isGliderOnList(Glider glider)
    {
        return false;
    }

    public void removeMemberFromList(Member member)
    {
        for (Pilot pilot : mPilots) {
            if (pilot.getMember() == member) {
                removePilot(pilot);

                return;
            }
        }
    }

    public void removeGliderFromList(Glider glider)
    {
    }
}
