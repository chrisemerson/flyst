package uk.co.cemerson.flyst.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearchable;

public class Member implements SimpleFuzzySearchable
{
    private UUID id;
    private String mFirstName;
    private String mSurname;
    private boolean mIsWinchDriver;
    private boolean mIsRetrieveDriver;

    public Member (String firstName, String surname)
    {
        setFirstName(firstName);
        setSurname(surname);
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    @Override
    public List<String> getFuzzySearchableTerms()
    {
        List<String> searchTerms = new ArrayList<>();

        searchTerms.add(getFirstName());
        searchTerms.add(getSurname());
        searchTerms.add(getFirstName() + " " + getSurname());

        return searchTerms;
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

    public String getDisplayName()
    {
        return getFirstName() + " " + getSurname();
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

    @Override
    public String toString()
    {
        return mFirstName + " " + mSurname;
    }
}
