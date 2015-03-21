package uk.co.cemerson.flyst.fuzzysearch;

import android.support.annotation.NonNull;

public class SimpleFuzzySearchResult implements Comparable<SimpleFuzzySearchResult>
{
    private int mScore;
    private SimpleFuzzySearchable mSearchTerm;

    public SimpleFuzzySearchResult(int score, SimpleFuzzySearchable searchTerm)
    {
        mScore = score;
        mSearchTerm = searchTerm;
    }

    @Override
    public int compareTo(@NonNull SimpleFuzzySearchResult other)
    {
        return getScore() - other.getScore();
    }

    public int getScore()
    {
        return mScore;
    }

    public SimpleFuzzySearchable getFuzzySearchableItem()
    {
        return mSearchTerm;
    }
}
