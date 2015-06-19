package uk.co.cemerson.flyst.repository;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.cemerson.flyst.entity.FlyingList;

public class FlyingListRepository
{
    private static final String LOG_TAG = "uk.co.cemerson.flyst";
    private static final String FILE_KEY = "flyinglist";

    private Context mContext;

    private static FlyingListRepository instance = null;
    private static FlyingList mFlyingList = null;

    private FlyingListRepository(Context context)
    {
        mContext = context;
    }

    public static FlyingListRepository getInstance(Context context)
    {
        if (instance == null) {
            instance = new FlyingListRepository(context);
        }

        return instance;
    }

    public FlyingList getCurrentFlyingList()
    {
        if (mFlyingList == null) {
            mFlyingList = findByDate(new Date());
        }

        return mFlyingList;
    }

    private FlyingList findByDate(Date flyingListDate)
    {
        JSONFile jsonFile = getJSONFileInstanceFromDate(flyingListDate);
        FlyingList flyingList;

        try {
            JSONObject jsonFlyingList = jsonFile.getJSON();
            flyingList = new FlyingList(mContext, jsonFlyingList);
        } catch (FileNotFoundException e) {
            flyingList = new FlyingList(mContext, flyingListDate);
            save(flyingList);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error loading flying list from filesystem");

            flyingList = new FlyingList(mContext, flyingListDate);
            save(flyingList);
        }

        return flyingList;
    }

    public void save(FlyingList flyingList)
    {
        JSONFile jsonFile = getJSONFileInstanceFromDate(flyingList.getFlyingListDate());

        try {
            jsonFile.writeJSON(flyingList);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error saving flying list to filesystem");
        }
    }

    private JSONFile getJSONFileInstanceFromDate(Date flyingListDate)
    {
        return new JSONFile(mContext, getFilenameFromDate(flyingListDate));
    }

    private String getFilenameFromDate(Date flyingListDate)
    {
        return FILE_KEY + "-" + new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(flyingListDate);
    }
}
