package uk.co.cemerson.flyst.fuzzysearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleFuzzySearcher
{
    private List<? extends SimpleFuzzySearchable> mSearchSet;
    private Integer mThreshold;

    private static int DELETE_COST = 1;
    private static int INSERT_COST = 1;
    private static int REPLACE_COST = 1;
    private static int SWAP_COST = 1;

    public SimpleFuzzySearcher(List<? extends SimpleFuzzySearchable> searchSet)
    {
        mSearchSet = searchSet;
        mThreshold = null;
    }

    public SimpleFuzzySearcher(List<? extends SimpleFuzzySearchable> searchSet, int threshold)
    {
        mSearchSet = searchSet;
        mThreshold = threshold;
    }

    public List<SimpleFuzzySearchResult> search(String searchTerm)
    {
        DamerauLevenshteinDistanceCalculator distanceCalculator = new DamerauLevenshteinDistanceCalculator(
            DELETE_COST,
            INSERT_COST,
            REPLACE_COST,
            SWAP_COST
        );

        List<SimpleFuzzySearchResult> returnList = new ArrayList<>();

        for (SimpleFuzzySearchable searchableItem : mSearchSet) {
            int distance = distanceCalculator.calculateDistanceBetweenStrings(searchableItem.getSearchableTerm(), searchTerm);

            if (mThreshold == null || distance <= mThreshold) {
                returnList.add(new SimpleFuzzySearchResult(distance, searchableItem));
            }
        }

        Collections.sort(returnList);

        return returnList;
    }
}
