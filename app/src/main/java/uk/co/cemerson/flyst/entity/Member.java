package uk.co.cemerson.flyst.entity;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearchable;
import uk.co.cemerson.flyst.repository.FlyingListRepository;
import uk.co.cemerson.flyst.repository.JSONSerializable;

public class Member implements SimpleFuzzySearchable, JSONSerializable
{
    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_FIRST_NAME = "firstname";
    private static final String JSON_KEY_SURNAME = "surname";
    private static final String JSON_KEY_IS_WINCH_DRIVER = "iswinchdriver";
    private static final String JSON_KEY_IS_RETRIEVE_DRIVER = "isretrievedriver";
    private static final String JSON_KEY_INSTRUCTOR_CATEGORY = "instructorcategory";

    private static final String LOG_TAG = "uk.co.cemerson.flyst";
    private Context mContext;

    private UUID mID;
    private String mFirstName;
    private String mSurname;
    private boolean mIsWinchDriver;
    private boolean mIsRetrieveDriver;
    private InstructorCategory mInstructorCategory;

    public Member(Context context)
    {
        mContext = context;

        mID = UUID.randomUUID();

        mFirstName = "";
        mSurname = "";
        mIsWinchDriver = false;
        mIsRetrieveDriver = false;
        mInstructorCategory = InstructorCategory.NOT_AN_INSTRUCTOR;
    }

    public Member(Context context, String firstName, String surname)
    {
        mContext = context;

        mID = UUID.randomUUID();

        mFirstName = firstName;
        mSurname = surname;
        mIsWinchDriver = false;
        mIsRetrieveDriver = false;
        mInstructorCategory = InstructorCategory.NOT_AN_INSTRUCTOR;
    }

    public Member(Context context, JSONObject jsonObject)
    {
        mContext = context;

        try {
            mID = UUID.fromString(jsonObject.getString(JSON_KEY_ID));
            mFirstName = jsonObject.getString(JSON_KEY_FIRST_NAME);
            mSurname = jsonObject.getString(JSON_KEY_SURNAME);
            mIsWinchDriver = jsonObject.getBoolean(JSON_KEY_IS_WINCH_DRIVER);
            mIsRetrieveDriver = jsonObject.getBoolean(JSON_KEY_IS_RETRIEVE_DRIVER);
            mInstructorCategory = InstructorCategory.fromRank(jsonObject.getInt(JSON_KEY_INSTRUCTOR_CATEGORY));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error decoding member information: ", e);
        }
    }

    @Override
    public List<String> getFuzzySearchableTerms()
    {
        List<String> searchTerms = new ArrayList<>();

        searchTerms.add(mFirstName);
        searchTerms.add(mSurname);
        searchTerms.add(mFirstName + " " + mSurname);

        return searchTerms;
    }

    public int getFuzzySearchableRank()
    {
        FlyingListRepository flyingListRepository = FlyingListRepository.getInstance(mContext);
        return flyingListRepository.getCurrentFlyingList().isMemberOnList(this) ? 0 : 1;
    }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_KEY_ID, mID.toString());
        jsonObject.put(JSON_KEY_FIRST_NAME, mFirstName);
        jsonObject.put(JSON_KEY_SURNAME, mSurname);
        jsonObject.put(JSON_KEY_IS_WINCH_DRIVER, mIsWinchDriver);
        jsonObject.put(JSON_KEY_IS_RETRIEVE_DRIVER, mIsRetrieveDriver);
        jsonObject.put(JSON_KEY_INSTRUCTOR_CATEGORY, mInstructorCategory.getInstructorRank());

        return jsonObject;
    }

    @Override
    public String toString()
    {
        return mFirstName + " " + mSurname;
    }

    public UUID getID()
    {
        return mID;
    }

    public void setID(UUID ID)
    {
        mID = ID;
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

    public void setIsWinchDriver(boolean isWinchDriver)
    {
        mIsWinchDriver = isWinchDriver;
    }

    public boolean isRetrieveDriver()
    {
        return mIsRetrieveDriver;
    }

    public void setIsRetrieveDriver(boolean isRetrieveDriver)
    {
        mIsRetrieveDriver = isRetrieveDriver;
    }

    public InstructorCategory getInstructorCategory()
    {
        return mInstructorCategory;
    }

    public void setInstructorCategory(InstructorCategory instructorCategory)
    {
        mInstructorCategory = instructorCategory;
    }
}
