package uk.co.cemerson.flyst.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FlyingList extends Observable implements Observer
{
    private static FlyingList instance = null;

    private Date mFlyingListDate;

    private List<Pilot> mPilots = new ArrayList<>();
    private List<Glider> mGliders = new ArrayList<>();

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

    @Override
    public void update(Observable observable, Object data)
    {
        notifyObservers();
    }

    public void save()
    {
        //Save flying list to file system
    }

    public void addPilot(Pilot pilot)
    {
        pilot.addObserver(this);
        mPilots.add(pilot);

        notifyObservers();
    }

    public void deletePilot(Pilot pilot)
    {
        mPilots.remove(pilot);

        notifyObservers();
    }

    public List<Pilot> getPilots()
    {
        return mPilots;
    }

    public void addGlider(Glider glider)
    {
        glider.addObserver(this);
        mGliders.add(glider);

        notifyObservers();
    }

    public void deleteGlider(Glider glider)
    {
        mGliders.remove(glider);

        notifyObservers();
    }

    public List<Glider> getGliders()
    {
        return mGliders;
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
