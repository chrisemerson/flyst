package uk.co.cemerson.flyst.fuzzysearch;

import android.support.annotation.NonNull;

public class SimpleFuzzySearchResult implements Comparable<SimpleFuzzySearchResult>
{
    private int mDistance;
    private SimpleFuzzySearchable mSearchTerm;

    public SimpleFuzzySearchResult(int distance, SimpleFuzzySearchable searchTerm)
    {
        mDistance = distance;
        mSearchTerm = searchTerm;
    }

    @Override
    public int compareTo(@NonNull SimpleFuzzySearchResult other)
    {
        return getDistance() - other.getDistance();
    }

    public int getDistance()
    {
        return mDistance;
    }

    public SimpleFuzzySearchable getFuzzySearchableItem()
    {
        return mSearchTerm;
    }
}
