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
    private static final String JSON_KEY_GLIDER_QUEUES = "gliderqueues";

    private Context mContext;
    private Date mFlyingListDate;

    private List<Pilot> mPilots = new ArrayList<>();
    private List<GliderQueue> mGliderQueues = new ArrayList<>();

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

            JSONArray gliderQueuesArray = jsonObject.getJSONArray(JSON_KEY_GLIDER_QUEUES);

            for (int i = 0; i < gliderQueuesArray.length(); i++) {
                mGliderQueues.add(new GliderQueue(mContext, mPilots, gliderQueuesArray.getJSONObject(i)));
            }
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
        jsonObject.put(JSON_KEY_GLIDER_QUEUES, getGliderQueuesJSONArray());

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

    private JSONArray getGliderQueuesJSONArray() throws JSONException
    {
        JSONArray gliderQueues = new JSONArray();

        for (GliderQueue gliderQueue : mGliderQueues) {
            gliderQueues.put(gliderQueue.toJSON());
        }

        return gliderQueues;
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

    public void addGliderQueue(GliderQueue gliderQueue)
    {
        mGliderQueues.add(gliderQueue);
        save();
    }

    public void removeGliderQueue(GliderQueue gliderQueue)
    {
        mGliderQueues.remove(gliderQueue);
        save();
    }

    public List<GliderQueue> getGliderQueues()
    {
        return mGliderQueues;
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
        for (GliderQueue gliderQueue : mGliderQueues) {
            if (gliderQueue.getGlider() == glider) {
                return true;
            }
        }

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
        for (GliderQueue gliderQueue : mGliderQueues) {
            if (gliderQueue.getGlider() == glider) {
                removeGliderQueue(gliderQueue);

                return;
            }
        }
    }
}
