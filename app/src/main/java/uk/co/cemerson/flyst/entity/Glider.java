package uk.co.cemerson.flyst.entity;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.cemerson.flyst.repository.JSONSerializable;

public class Glider implements JSONSerializable
{
    private String mRegistration;
    private String mType;
    private boolean mIsTwoSeater;

    @Override
    public JSONObject toJSON() throws JSONException
    {
        return null;
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
}
