package uk.co.cemerson.flyst.repository;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class JSONFile
{
    private final Context mContext;
    private final String mFileKey;
    private BufferedReader reader = null;

    public JSONFile(Context context, String fileKey)
    {
        mContext = context;
        mFileKey = fileKey;
    }

    public void writeJSON(JSONSerializable object) throws JSONException, IOException
    {
        Writer writer = null;

        try {
            OutputStream out = mContext.openFileOutput(getFilename(), Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(object.toJSON().toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public JSONObject getJSON() throws IOException, JSONException
    {
        JSONObject jsonObject = new JSONObject();

        try {
            initReader();
            String jsonString = getStringFromReader(reader);

            jsonObject = (JSONObject) readJSONFromString(jsonString);
        } finally {
            closeReaderIfOpen();
        }

        return jsonObject;
    }

    private Object readJSONFromString(String jsonString) throws JSONException
    {
        return new JSONTokener(jsonString).nextValue();
    }

    private void initReader() throws FileNotFoundException
    {
        InputStream in = getInputStream();
        reader = new BufferedReader(new InputStreamReader(in));
    }

    private String getStringFromReader(BufferedReader reader) throws IOException
    {
        StringBuilder jsonString = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonString.append(line);
        }

        return jsonString.toString();
    }

    private void closeReaderIfOpen() throws IOException
    {
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }

    private InputStream getInputStream() throws FileNotFoundException
    {
        return mContext.openFileInput(getFilename());
    }

    private String getFilename()
    {
        return mFileKey + ".json";
    }
}
