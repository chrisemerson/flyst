package uk.co.cemerson.flyst.repository;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.cemerson.flyst.entity.Member;
import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearchResult;
import uk.co.cemerson.flyst.fuzzysearch.SimpleFuzzySearcher;

public class MemberRepository implements JSONSerializable
{
    public static final String LOG_TAG = "uk.co.cemerson.flyst";
    private static final String FILE_KEY = "members";
    private static final String JSON_KEY_MEMBERS = "members";

    private JSONFile jsonFile;
    private List<Member> allMembers;

    private static MemberRepository instance = null;

    private MemberRepository(Context context)
    {
        jsonFile = new JSONFile(context, FILE_KEY);
        allMembers = loadMembersFromStorage(jsonFile);
    }

    public static MemberRepository getInstance(Context context)
    {
        if (instance == null) {
            instance = new MemberRepository(context);
        }

        return instance;
    }

    @Override
    public JSONObject toJSON() throws JSONException
    {
        JSONArray array = new JSONArray();

        for (JSONSerializable member : allMembers) {
            array.put(member.toJSON());
        }

        JSONObject json = new JSONObject();
        json.put(JSON_KEY_MEMBERS, array);

        return json;
    }

    public void save()
    {
        saveMembersToStorage(jsonFile);
    }

    private List<Member> loadMembersFromStorage(JSONFile jsonFile)
    {
        ArrayList<Member> loadedMembers = new ArrayList<>();

        try {
            JSONObject loadedData = jsonFile.getJSON();
            JSONArray membersArray = loadedData.getJSONArray(JSON_KEY_MEMBERS);

            for (int i = 0; i < membersArray.length(); i++) {
                loadedMembers.add(new Member(membersArray.getJSONObject(i)));
            }
        } catch (Exception e) {
            Log.e("uk.co.cemerson.flyst", "Error loading members: ", e);
        }

        return loadedMembers;
    }

    private void saveMembersToStorage(JSONFile jsonFile)
    {
        try {
            jsonFile.writeJSON(this);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error saving members: ", e);
        }
    }

    public List<Member> searchForMemberByName(String searchTerm, int limit)
    {
        SimpleFuzzySearcher searcher = new SimpleFuzzySearcher(allMembers);

        List<SimpleFuzzySearchResult> searchResults = searcher.search(searchTerm);
        List<Member> returnList = new ArrayList<>();

        int counter = 0;

        for (SimpleFuzzySearchResult searchResult : searchResults) {
            returnList.add((Member) searchResult.getFuzzySearchableItem());

            if (++counter >= limit) {
                break;
            }
        }

        return returnList;
    }

    public void clearAllMembers()
    {
        allMembers.clear();
        save();
    }

    public void addMember(Member member)
    {
        allMembers.add(member);
        save();
    }

    public void deleteMember(Member member)
    {
        allMembers.remove(member);
        save();
    }
}
