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
            boolean searchMatched = false;
            int minDistance = 99999;

            for (String itemSearchableTerm :searchableItem.getFuzzySearchableTerms()) {
                String truncatedItemSearchableTerm;

                if (itemSearchableTerm.length() >= searchTerm.length()) {
                    truncatedItemSearchableTerm = itemSearchableTerm.substring(0, searchTerm.length());
                } else {
                    truncatedItemSearchableTerm = itemSearchableTerm;
                }

                int distance = distanceCalculator.calculateDistanceBetweenStrings(
                    searchTerm.toLowerCase(),
                    truncatedItemSearchableTerm.toLowerCase()
                );

                if (mThreshold == null || distance <= mThreshold) {
                    searchMatched = true;
                    minDistance = Math.min(minDistance, distance);
                }
            }

            if (searchMatched) {
                returnList.add(new SimpleFuzzySearchResult(searchableItem, minDistance));
            }
        }

        Collections.sort(returnList);

        return returnList;
    }
}
