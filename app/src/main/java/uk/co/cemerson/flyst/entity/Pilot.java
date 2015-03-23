package uk.co.cemerson.flyst.entity;

import java.util.List;

import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearchable;

public class Pilot implements SimpleFuzzySearchable
{
    private Member mMember;
    private boolean mHasFlown;
    private String mNotes;

    public Pilot(Member member)
    {
        setMember(member);
    }

    @Override
    public List<String> getFuzzySearchableTerms()
    {
        return mMember.getFuzzySearchableTerms();
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

    @Override
    public String toString()
    {
        return mMember.toString();
    }
}
