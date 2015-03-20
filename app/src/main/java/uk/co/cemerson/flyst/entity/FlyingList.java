package uk.co.cemerson.flyst.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlyingList
{
    private static FlyingList instance = null;

    private Date mFlyingListDate;

    private List<Pilot> mPilots = new ArrayList<>();
    private List<Glider> mGliders = new ArrayList<>();

    private List<FlyingListUpdateReceiver> mUpdateReceivers = new ArrayList<>();

    private FlyingList(Date flyingListDate)
    {
        mFlyingListDate = flyingListDate;
    }

    public static FlyingList getInstance(Date flyingListDate)
    {
        if (instance == null) {
            instance = load(flyingListDate);
        }

        return instance;
    }

    private static FlyingList load(Date flyingListDate)
    {
        //Load from file system if flying list exists for this date
        return new FlyingList(flyingListDate);
    }

    public void save()
    {
        //Save flying list to file system
    }

    public void addPilot(Pilot member)
    {
        updateReceivers();
    }

    public List<Pilot> getPilots()
    {
        return new ArrayList<Pilot>();
    }

    public void addGlider(Glider glider)
    {
        updateReceivers();
    }

    public List<Glider> getGliders()
    {
        return new ArrayList<Glider>();
    }

    public int getNumberOfPilotsOnList()
    {
        return mPilots.size();
    }

    public int getNumberOfPilotsOnListWhoHaveFlown()
    {
        int numberOfPilotsFlown = 0;

        for (Pilot pilot : mPilots) {
            if (pilot.hasFlown()) {
                numberOfPilotsFlown++;
            }
        }

        return numberOfPilotsFlown;
    }

    public void attachUpdateReceiver(FlyingListUpdateReceiver updateReceiver)
    {
        mUpdateReceivers.add(updateReceiver);
    }

    public void detachUpdateReceiver(FlyingListUpdateReceiver updateReceiver)
    {
        mUpdateReceivers.remove(updateReceiver);
    }

    public void updateReceivers()
    {
        for (FlyingListUpdateReceiver receiver : mUpdateReceivers) {
            receiver.onFlyingListUpdate();
        }
    }

    public interface FlyingListUpdateReceiver
    {
        public void onFlyingListUpdate();
    }
}
