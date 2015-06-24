package uk.co.cemerson.flyst.entity;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.cemerson.flyst.repository.JSONSerializable;

public class GliderQueue implements JSONSerializable
{
    private Glider mGlider;

    public GliderQueue(Glider glider)
    {
        mGlider = glider;
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        return null;
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
