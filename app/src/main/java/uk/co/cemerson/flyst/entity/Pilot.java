package uk.co.cemerson.flyst.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearchable;
import uk.co.cemerson.flyst.repository.JSONSerializable;

public class Pilot implements SimpleFuzzySearchable, JSONSerializable
{
    private Member mMember;
    private boolean mHasFlown;
    private String mNotes;
    private Date mDateAdded;

    public Pilot(Member member, Date dateAdded)
    {
        setMember(member);
        setDateAdded(dateAdded);
    }

    @Override
    public List<String> getFuzzySearchableTerms()
    {
        return mMember.getFuzzySearchableTerms();
    }

    @Override
    public int getFuzzySearchableRank()
    {
        return 0;
    }

    @Override
    public String toString()
    {
        return mMember.toString();
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        return null;
    }

    public String getDisplayName()
    {
        return mMember.getDisplayName();
    }

    public Member getMember()
    {
        return mMember;
    }

    public void setMember(Member member)
    {
        mMember = member;
    }

    public boolean hasFlown()
    {
        return mHasFlown;
    }

    public void setHasFlown(boolean hasFlown)
    {
        mHasFlown = hasFlown;
    }

    public String getNotes()
    {
        return mNotes;
    }

    public void setNotes(String notes)
    {
        mNotes = notes;
    }

    public Date getDateAdded()
    {
        return mDateAdded;
    }

    public void setDateAdded(Date dateAdded)
    {
        mDateAdded = dateAdded;
    }
}
