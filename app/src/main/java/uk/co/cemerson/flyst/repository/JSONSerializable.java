package uk.co.cemerson.flyst.repository;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONSerializable
{
    public JSONObject toJSON() throws JSONException;
}
