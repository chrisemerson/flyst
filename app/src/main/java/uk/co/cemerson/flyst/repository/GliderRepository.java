package uk.co.cemerson.flyst.repository;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.cemerson.flyst.entity.Glider;

public class GliderRepository implements JSONSerializable
{
    private static final String LOG_TAG = "uk.co.cemerson.flyst";
    private static final String FILE_KEY = "gliders";
    private static final String JSON_KEY_GLIDERS = "gliders";

    private Context mContext;

    private JSONFile mJSONFile;
    private List<Glider> mGliders;

    private GlidersLoadedListener mListener;

    private static GliderRepository instance = null;

    private GliderRepository(Context context, GlidersLoadedListener listener)
    {
        mContext = context;
        mJSONFile = new JSONFile(context, FILE_KEY);
        mGliders = loadGlidersFromStorage(mJSONFile);
        mListener = listener;
    }

    public static GliderRepository getInstance(Context context, GlidersLoadedListener listener)
    {
        if (instance == null) {
            instance = new GliderRepository(context, listener);
        }

        return instance;
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONArray array = new JSONArray();

        for (JSONSerializable glider : mGliders) {
            array.put(glider.toJSON());
        }

        JSONObject json = new JSONObject();
        json.put(JSON_KEY_GLIDERS, array);

        return json;
    }

    private List<Glider> loadGlidersFromStorage(JSONFile jsonFile)
    {
        ArrayList<Glider> loadedGliders = new ArrayList<>();

        try {
            JSONObject loadedData = jsonFile.readJSON();
            JSONArray glidersArray = loadedData.getJSONArray(JSON_KEY_GLIDERS);

            for (int i = 0; i < glidersArray.length(); i++) {
                loadedGliders.add(new Glider(mContext, glidersArray.getJSONObject(i)));
            }
        } catch (Exception e) {
            Log.e("uk.co.cemerson.flyst", "Error loading gliders: ", e);
        }

        if (mListener != null) {
            mListener.onGlidersLoaded();
            mListener = null;
        }

        return loadedGliders;
    }

    public void save()
    {
        saveGlidersToStorage(mJSONFile);
    }

    public void saveGlidersToStorage(JSONFile jsonFile)
    {
        jsonFile.writeJSON(this);
    }

    public Glider findByRegistration(String registration)
    {
        for (Glider glider : mGliders) {
            if (glider.getRegistration().equals(registration)) {
                return glider;
            }
        }

        return null;
    }

    public void addGlider(Glider glider)
    {
        mGliders.add(glider);
        save();
    }

    public void deleteGlider(Glider glider)
    {
        mGliders.remove(glider);

        FlyingListRepository.getInstance(mContext, null).getCurrentFlyingList().removeGliderFromList(glider);

        save();
    }

    public List<Glider> getAllGliders()
    {
        return mGliders;
    }

    public interface GlidersLoadedListener
    {
        void onGlidersLoaded();
    }
}
