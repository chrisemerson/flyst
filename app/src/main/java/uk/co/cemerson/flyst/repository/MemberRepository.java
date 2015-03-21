package uk.co.cemerson.flyst.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearcher;
import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearchResult;
import uk.co.cemerson.flyst.entity.Member;

public class MemberRepository
{
    private List<Member> allMembers;

    public MemberRepository()
    {
        allMembers = new ArrayList<>();

        allMembers.add(new Member("Ian", "Mountain"));
        allMembers.add(new Member("Ian", "Campbell"));
        allMembers.add(new Member("Mick", "Wood"));
        allMembers.add(new Member("Mick", "Baker"));
        allMembers.add(new Member("Frank", "Kennedy"));
        allMembers.add(new Member("Dan", "Ullyat"));
        allMembers.add(new Member("Chris", "Emerson"));
        allMembers.add(new Member("Angus", "Watson"));
        allMembers.add(new Member("Miriam", "Watson"));
        allMembers.add(new Member("Pete", "Davey"));
        allMembers.add(new Member("Mandeep", "Singh"));
        allMembers.add(new Member("Tom", "Grover"));
        allMembers.add(new Member("Chris", "Franklin"));
        allMembers.add(new Member("Andy", "Langton"));
        allMembers.add(new Member("Rebecca", "Ward"));
        allMembers.add(new Member("Toby", "Evans"));
        allMembers.add(new Member("Mark", "Evans"));
    }

    public List<Member> searchForMemberByName(String searchTerm)
    {
        SimpleFuzzySearcher searcher = new SimpleFuzzySearcher(allMembers);

        List<SimpleFuzzySearchResult> searchResults = searcher.search(searchTerm);
        List<Member> returnList = new ArrayList<>();

        Log.i("uk.co.cemerson.flyst", "**********");
        Log.i("uk.co.cemerson.flyst", "Found search results for '" + searchTerm + "':");

        for (SimpleFuzzySearchResult searchResult : searchResults) {
            Log.i("uk.co.cemerson.flyst", searchResult.getFuzzySearchableItem().getFuzzySearchableTerm() + " with a distance of " + searchResult.getScore());

            returnList.add((Member) searchResult.getFuzzySearchableItem());
        }
        Log.i("uk.co.cemerson.flyst", "**********");

        return returnList;
    }
}
