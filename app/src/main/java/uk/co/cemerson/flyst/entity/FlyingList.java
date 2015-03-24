package uk.co.cemerson.flyst.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.cemerson.flyst.repository.JSONSerializable;

public class FlyingList implements JSONSerializable
{
    private static final String JSON_KEY_PILOTS = "pilots";
    private static final String JSON_KEY_GLIDERS = "gliders";

    private static FlyingList instance = null;

    private Date mFlyingListDate;

    private List<Pilot> mPilots = new ArrayList<>();
    private List<Glider> mGliders = new ArrayList<>();

    private FlyingList(Date flyingListDate)
    {
        mFlyingListDate = flyingListDate;
    }

    public static FlyingList getInstance(Date flyingListDate)
    {
        if (instance == null) {
            instance = load(flyingListDate);
        }

        return instance;
    }

    private static FlyingList load(Date flyingListDate)
    {
        //Load from file system if flying list exists for this date
        return new FlyingList(flyingListDate);
    }

    public void save()
    {
        //Save flying list to file system
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();

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

        for (Glider glider : mGliders) {
            gliders.put(glider.toJSON());
        }

        return gliders;
    }

    public void addPilot(Pilot pilot)
    {
        mPilots.add(pilot);
    }

    public void deletePilot(Pilot pilot)
    {
        mPilots.remove(pilot);
    }

    public List<Pilot> getPilots()
    {
        return mPilots;
    }

    public void addGlider(Glider glider)
    {
        mGliders.add(glider);
    }

    public void deleteGlider(Glider glider)
    {
        mGliders.remove(glider);
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
}
