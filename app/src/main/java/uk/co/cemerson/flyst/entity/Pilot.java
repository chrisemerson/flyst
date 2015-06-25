package uk.co.cemerson.flyst.entity;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearchable;
import uk.co.cemerson.flyst.repository.JSONSerializable;
import uk.co.cemerson.flyst.repository.MemberRepository;

public class Pilot implements SimpleFuzzySearchable, JSONSerializable
{
    private static final String LOG_TAG = "uk.co.cemerson.flyst";

    public static final String JSON_KEY_MEMBER = "member";
    private static final String JSON_KEY_HAS_FLOWN = "hasflown";
    private static final String JSON_KEY_NOTES = "notes";
    private static final String JSON_KEY_DATE_ADDED= "dateadded";

    private Context mContext;

    private Member mMember;
    private boolean mHasFlown;
    private String mNotes;
    private Date mDateAdded;

    public Pilot(Context context, Member member, Date dateAdded)
    {
        mContext = context;

        mMember = member;
        mHasFlown = false;
        mNotes = "";
        mDateAdded = dateAdded;
    }

    public Pilot(Context context, JSONObject jsonObject)
    {
        mContext = context;

        MemberRepository memberRepository = MemberRepository.getInstance(mContext);

        try {
            mMember = memberRepository.findByID(UUID.fromString(jsonObject.getString(JSON_KEY_MEMBER)));
            mHasFlown = jsonObject.getBoolean(JSON_KEY_HAS_FLOWN);
            mNotes = jsonObject.getString(JSON_KEY_NOTES);
            mDateAdded = new Date(jsonObject.getLong(JSON_KEY_DATE_ADDED));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error decoding pilot information: ", e);
        }
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
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_KEY_MEMBER, mMember.getID().toString());
        jsonObject.put(JSON_KEY_HAS_FLOWN, mHasFlown);
        jsonObject.put(JSON_KEY_NOTES, mNotes);
        jsonObject.put(JSON_KEY_DATE_ADDED, mDateAdded.getTime());

        return jsonObject;
    }

    public String getDisplayName()
    {
        return mMember.getDisplayNameFirstNameFirst();
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
