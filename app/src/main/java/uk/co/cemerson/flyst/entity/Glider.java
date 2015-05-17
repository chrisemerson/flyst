package uk.co.cemerson.flyst.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.cemerson.flyst.repository.JSONSerializable;

public class Glider implements JSONSerializable, Comparable
{
    private static final String JSON_KEY_REGISTRATION = "registration";
    private static final String JSON_KEY_TYPE = "type";
    private static final String JSON_KEY_IS_TWO_SEATER = "istwoseater";
    private static final String JSON_KEY_IS_CLUB_GLIDER = "isclubglider";

    private static final String LOG_TAG = "uk.co.cemerson.flyst";
    private Context mContext;

    private String mRegistration;
    private String mType;
    private boolean mIsTwoSeater;
    private boolean mIsClubGlider;

    public Glider(Context context)
    {
        mContext = context;

        mRegistration = "";
        mType = "";
        mIsTwoSeater = false;
        mIsClubGlider = false;
    }

    public Glider(Context context, JSONObject jsonObject)
    {
        mContext = context;

        try {
            mRegistration = jsonObject.getString(JSON_KEY_REGISTRATION);
            mType = jsonObject.getString(JSON_KEY_TYPE);
            mIsTwoSeater = jsonObject.getBoolean(JSON_KEY_IS_TWO_SEATER);
            mIsClubGlider = jsonObject.getBoolean(JSON_KEY_IS_CLUB_GLIDER);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error decoding glider information: ", e);
        }
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_KEY_REGISTRATION, mRegistration);
        jsonObject.put(JSON_KEY_TYPE, mType);
        jsonObject.put(JSON_KEY_IS_TWO_SEATER, mIsTwoSeater);
        jsonObject.put(JSON_KEY_IS_CLUB_GLIDER, mIsClubGlider);

        return jsonObject;
    }

    @Override
    public int compareTo(@NonNull Object other)
    {
        return getRegistration().compareTo(((Glider) other).getRegistration());
    }

    public String getRegistration()
    {
        return mRegistration;
    }

    public void setRegistration(String registration)
    {
        mRegistration = registration;
    }

    public String getType()
    {
        return mType;
    }

    public void setType(String type)
    {
        mType = type;
    }

    public boolean isTwoSeater()
    {
        return mIsTwoSeater;
    }

    public void setTwoSeater(boolean isTwoSeater)
    {
        mIsTwoSeater = isTwoSeater;
    }

    public boolean isClubGlider()
    {
        return mIsClubGlider;
    }

    public void setClubGlider(boolean isClubGlider)
    {
        mIsClubGlider = isClubGlider;
    }
}
