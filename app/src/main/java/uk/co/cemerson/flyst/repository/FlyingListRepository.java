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

    private FlyingListLoadedListener mListener;

    private FlyingListRepository(Context context, FlyingListLoadedListener listener)
    {
        mContext = context;
        mFlyingList = findByDate(new Date());
        mListener = listener;
    }

    public static FlyingListRepository getInstance(Context context, FlyingListLoadedListener listener)
    {
        if (instance == null) {
            instance = new FlyingListRepository(context, listener);
        }

        return instance;
    }

    public FlyingList getCurrentFlyingList()
    {
        return mFlyingList;
    }

    private FlyingList findByDate(Date flyingListDate)
    {
        JSONFile jsonFile = getJSONFileInstanceFromDate(flyingListDate);
        FlyingList flyingList;

        try {
            JSONObject jsonFlyingList = jsonFile.readJSON();
            flyingList = new FlyingList(mContext, jsonFlyingList);
        } catch (FileNotFoundException e) {
            flyingList = new FlyingList(mContext, flyingListDate);
            save(flyingList);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error loading flying list from filesystem");

            flyingList = new FlyingList(mContext, flyingListDate);
            save(flyingList);
        }

        if (mListener != null) {
            mListener.onFlyingListLoaded();
            mListener = null;
        }

        return flyingList;
    }

    public void save(FlyingList flyingList)
    {
        JSONFile jsonFile = getJSONFileInstanceFromDate(flyingList.getFlyingListDate());
        jsonFile.writeJSON(flyingList);
    }

    private JSONFile getJSONFileInstanceFromDate(Date flyingListDate)
    {
        return new JSONFile(mContext, getFilenameFromDate(flyingListDate));
    }

    private String getFilenameFromDate(Date flyingListDate)
    {
        return FILE_KEY + "-" + new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(flyingListDate);
    }

    public interface FlyingListLoadedListener
    {
        void onFlyingListLoaded();
    }
}
