package uk.co.cemerson.flyst.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class JSONFile
{
    private final Context mContext;
    private final String mFilename;

    private BufferedReader reader = null;

    public JSONFile(Context context, String fileKey)
    {
        mContext = context;
        mFilename = fileKey + ".json";
    }

    public void writeJSON(JSONSerializable object)
    {
        new FileWriterTask().execute(mContext, mFilename, object);
    }

    public JSONObject readJSON() throws IOException, JSONException
    {
        JSONObject jsonObject = new JSONObject();

        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString1 = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonString1.append(line);
            }

            String jsonString = jsonString1.toString();

            jsonObject = (JSONObject) new JSONTokener(jsonString).nextValue();
        } finally {
            if (reader != null) {
                reader.close();
                reader = null;
            }
        }

        return jsonObject;
    }

    private class FileWriterTask extends AsyncTask<Object, Void, Void>
    {
        @Override
        protected Void doInBackground(Object... params)
        {
            Context context = (Context) params[0];
            String filename = (String) params[1];
            JSONSerializable object = (JSONSerializable) params[2];

            Writer writer = null;

            try {
                try {
                    OutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
                    writer = new OutputStreamWriter(out);
                    writer.write(object.toJSON().toString());
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
            } catch (Exception e) {
                Log.e("uk.co.cemerson.flyst", "Could not write JSON to file", e);
            }

            return null;
        }
    }
}
