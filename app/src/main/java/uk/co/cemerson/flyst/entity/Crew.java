package uk.co.cemerson.flyst.entity;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

import uk.co.cemerson.flyst.repository.JSONSerializable;

public class Crew implements JSONSerializable
{
    private static final String LOG_TAG = "uk.co.cemerson.flyst";

    private static final String JSON_KEY_P1 = "p1";
    private static final String JSON_KEY_P2 = "p2";

    private Context mContext;

    private Pilot mP1;
    private Pilot mP2;

    public Crew(Context context, Pilot p1, Pilot p2)
    {
        mContext = context;

        mP1 = p1;
        mP2 = p2;
    }

    public Crew(Context context, List<Pilot> pilotsOnList, JSONObject jsonObject)
    {
        mContext = context;

        try {
            mP1 = findPilotFromList(jsonObject.getJSONObject(JSON_KEY_P1), pilotsOnList);
            mP2 = findPilotFromList(jsonObject.getJSONObject(JSON_KEY_P2), pilotsOnList);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error decoding crew information: ", e);
        }
    }

    private Pilot findPilotFromList(JSONObject jsonObject, List<Pilot> pilotsOnList) throws JSONException
    {
        UUID memberID = UUID.fromString(jsonObject.getString(Pilot.JSON_KEY_MEMBER));

        for (Pilot pilot : pilotsOnList) {
            if (pilot.getMember().getID() == memberID) {
                return pilot;
            }
        }

        return null;
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        return null;
    }

    public Pilot getP1()
    {
        return mP1;
    }

    public void setP1(Pilot p1)
    {
        mP1 = p1;
    }

    public Pilot getP2()
    {
        return mP2;
    }

    public void setP2(Pilot p2)
    {
        mP2 = p2;
    }
}
