package in.skylinelabs.barclaysmanager;


import com.google.gson.JsonObject;

import org.json.JSONException;


public interface AsyncTaskComplete {
    void handleResult(JsonObject result, String action) throws JSONException;

}
