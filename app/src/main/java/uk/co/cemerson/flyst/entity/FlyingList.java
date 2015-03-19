package uk.co.cemerson.flyst.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlyingList
{
    private static FlyingList instance = null;

    private Date mFlyingListDate;

    private List<Pilot> mPilots;
    private List<Glider> mGliders;

    private FlyingList(Date flyingListDate)
    {
        mFlyingListDate = flyingListDate;
    }

    public static FlyingList getInstance(Date flyingListDate)
    {
        if (instance == null) {
            instance = new FlyingList(flyingListDate);
        }

        return instance;
    }

    public void addPilot(Pilot member)
    {

    }

    public List<Pilot> getMembers()
    {
        return new ArrayList<Pilot>();
    }

    public void addGlider(Glider glider)
    {

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
}
