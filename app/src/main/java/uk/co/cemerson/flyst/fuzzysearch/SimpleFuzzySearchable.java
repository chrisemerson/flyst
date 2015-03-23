package uk.co.cemerson.flyst.fuzzysearch;

import java.util.List;

public interface SimpleFuzzySearchable
{
    /**
     * @return A list of strings that are considered equal searchable terms for this item. The search is
     *         carried out against all the terms returned by this method, and the best distance of all of
     *         them is used as the distance value of the overall searchable item.
     */
    public List<String> getFuzzySearchableTerms();

    /**
     * @return A rank to sort results by in the event that the distance value of the Fuzzy Search is equal.
     *         Higher values move the item further towards the top of the list, but distance is always the
     *         primary sorting value.
     */
    public int getFuzzySearchableRank();
}
