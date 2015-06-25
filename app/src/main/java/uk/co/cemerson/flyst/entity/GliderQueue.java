package uk.co.cemerson.flyst.entity;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.cemerson.flyst.repository.GliderRepository;
import uk.co.cemerson.flyst.repository.JSONSerializable;

public class GliderQueue implements JSONSerializable
{
    private static final String LOG_TAG = "uk.co.cemerson.flyst";

    private static final String JSON_KEY_GLIDER = "glider";
    private static final String JSON_KEY_CREW = "crew";

    private Context mContext;

    private Glider mGlider;
    private List<Crew> mCrews = new ArrayList<>();

    public GliderQueue(Context context, Glider glider)
    {
        mContext = context;
        mGlider = glider;
    }

    public GliderQueue(Context context, List<Pilot> pilotsList, JSONObject jsonObject)
    {
        mContext = context;

        GliderRepository gliderRepository = GliderRepository.getInstance(context);

        try {
            mGlider = gliderRepository.findByRegistration(jsonObject.getString(JSON_KEY_GLIDER));

            JSONArray crewsArray = jsonObject.getJSONArray(JSON_KEY_CREW);

            for (int i = 0; i < crewsArray.length(); i++) {
                mCrews.add(new Crew(mContext, pilotsList, crewsArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error decoding glider queue information: ", e);
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_KEY_GLIDER, mGlider.getRegistration());
        jsonObject.put(JSON_KEY_CREW, getCrewArray());

        return jsonObject;
    }

    private JSONArray getCrewArray() throws JSONException
    {
        JSONArray crewArray = new JSONArray();

        for (Crew crew : mCrews) {
            crewArray.put(crew.toJSON());
        }

        return crewArray;
    }

    public Glider getGlider()
    {
        return mGlider;
    }

    public void setGlider(Glider glider)
    {
        mGlider = glider;
    }
}
