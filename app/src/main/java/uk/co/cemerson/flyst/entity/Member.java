package uk.co.cemerson.flyst.entity;

import java.util.UUID;

public class Member
{
    private UUID id;
    private String mFirstName;
    private String mSurname;
    private boolean mIsWinchDriver;
    private boolean mIsRetrieveDriver;

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return mFirstName;
    }

    public void setFirstName(String firstName)
    {
        mFirstName = firstName;
    }

    public String getSurname()
    {
        return mSurname;
    }

    public void setSurname(String surname)
    {
        mSurname = surname;
    }

    public boolean isWinchDriver()
    {
        return mIsWinchDriver;
    }

    public void setWinchDriver(boolean isWinchDriver)
    {
        mIsWinchDriver = isWinchDriver;
    }

    public boolean isRetrieveDriver()
    {
        return mIsRetrieveDriver;
    }

    public void setRetrieveDriver(boolean isRetrieveDriver)
    {
        mIsRetrieveDriver = isRetrieveDriver;
    }
}
