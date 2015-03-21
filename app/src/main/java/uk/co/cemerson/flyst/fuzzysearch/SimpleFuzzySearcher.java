package uk.co.cemerson.flyst.fuzzysearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleFuzzySearcher
{
    private List<? extends SimpleFuzzySearchable> mSearchSet;

    public SimpleFuzzySearcher(List<? extends SimpleFuzzySearchable> searchSet)
    {
        mSearchSet = searchSet;
    }

    public List<SimpleFuzzySearchResult> search(String searchTerm)
    {
        List<SimpleFuzzySearchResult> returnList = new ArrayList<>();

        for (SimpleFuzzySearchable searchableItem : mSearchSet) {
            if (searchableItem.getFuzzySearchableTerm().toLowerCase().contains(searchTerm.toLowerCase())) {
                returnList.add(new SimpleFuzzySearchResult(1, searchableItem));
            }
        }

        Collections.sort(returnList);
        Collections.reverse(returnList);

        return returnList;
    }
}
