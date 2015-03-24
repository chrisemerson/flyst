package uk.co.cemerson.flyst.entity;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearchable;
import uk.co.cemerson.flyst.repository.JSONSerializable;

public class Member implements SimpleFuzzySearchable, JSONSerializable
{
    private static final String JSON_KEY_FIRST_NAME = "firstname";
    private static final String JSON_KEY_SURNAME = "surname";
    private static final String JSON_KEY_IS_WINCH_DRIVER = "iswinchdriver";
    private static final String JSON_KEY_IS_RETRIEVE_DRIVER = "isretrievedriver";
    private static final String JSON_KEY_INSTRUCTOR_CATEGORY = "instructorcategory";

    private static final String LOG_TAG = "uk.co.cemerson.flyst";

    private String mFirstName;
    private String mSurname;
    private boolean mIsWinchDriver;
    private boolean mIsRetrieveDriver;
    private InstructorCategory mInstructorCategory;

    public Member()
    {
        setFirstName("");
        setSurname("");
        setIsWinchDriver(false);
        setIsRetrieveDriver(false);
        setInstructorCategory(InstructorCategory.NOT_AN_INSTRUCTOR);
    }

    public Member(String firstName, String surname)
    {
        setFirstName(firstName);
        setSurname(surname);
        setIsWinchDriver(false);
        setIsRetrieveDriver(false);
        setInstructorCategory(InstructorCategory.NOT_AN_INSTRUCTOR);
    }

    public Member (JSONObject jsonObject)
    {
        try {
            setFirstName(jsonObject.getString(JSON_KEY_FIRST_NAME));
            setSurname(jsonObject.getString(JSON_KEY_SURNAME));
            setIsWinchDriver(jsonObject.getBoolean(JSON_KEY_IS_WINCH_DRIVER));
            setIsRetrieveDriver(jsonObject.getBoolean(JSON_KEY_IS_RETRIEVE_DRIVER));
            setInstructorCategory(InstructorCategory.fromRank(jsonObject.getInt(JSON_KEY_INSTRUCTOR_CATEGORY)));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error decoding member information: ", e);
        }
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

    public int getFuzzySearchableRank()
    {
        return FlyingList.getInstance(new Date()).isMemberOnList(this) ? 0 : 1;
    }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_KEY_FIRST_NAME, getFirstName());
        jsonObject.put(JSON_KEY_SURNAME, getSurname());
        jsonObject.put(JSON_KEY_IS_WINCH_DRIVER, isWinchDriver());
        jsonObject.put(JSON_KEY_IS_RETRIEVE_DRIVER, isRetrieveDriver());
        jsonObject.put(JSON_KEY_INSTRUCTOR_CATEGORY, getInstructorCategory().getInstructorRank());

        return jsonObject;
    }

    @Override
    public String toString()
    {
        return mFirstName + " " + mSurname;
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
