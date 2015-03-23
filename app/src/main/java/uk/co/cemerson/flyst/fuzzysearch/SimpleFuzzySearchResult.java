package uk.co.cemerson.flyst.fuzzysearch;

import android.support.annotation.NonNull;

public class SimpleFuzzySearchResult implements Comparable<SimpleFuzzySearchResult>
{
    private SimpleFuzzySearchable mSearchTerm;
    private int mDistance;

    public SimpleFuzzySearchResult(SimpleFuzzySearchable searchTerm, int distance)
    {
        mSearchTerm = searchTerm;
        mDistance = distance;
    }

    @Override
    public int compareTo(@NonNull SimpleFuzzySearchResult other)
    {
        if (getDistance() == other.getDistance()) {
            int thisRank = getFuzzySearchableItem().getFuzzySearchableRank();
            int otherRank = other.getFuzzySearchableItem().getFuzzySearchableRank();

            return otherRank - thisRank;
        } else {
            return getDistance() - other.getDistance();
        }
    }

    public SimpleFuzzySearchable getFuzzySearchableItem()
    {
        return mSearchTerm;
    }

    public int getDistance()
    {
        return mDistance;
    }
}
