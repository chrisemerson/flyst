package uk.co.cemerson.flyst.entity;

public class Pilot
{
    private Member mMember;
    private boolean mHasFlown;
    private String mNotes;

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
}
