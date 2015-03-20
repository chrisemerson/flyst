package uk.co.cemerson.flyst.entity;

import java.util.Observable;

public class Glider extends Observable
{
    private String mRegistration;
    private String mType;
    private boolean mIsTwoSeater;

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
