package Model;

/**
 * Created by Jacob on 5/3/2015.
 */

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.callumtaylor.asynchttp.AsyncHttpClient;
import net.callumtaylor.asynchttp.response.JsonResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 5/3/2015.
 */

/**
 * API Connection class.
 *
 * Note all methods are static-- this class requires no state information to function.
 *
 * Note: calling class is retained if its a destroyed activity or fragment.
 */
public class Backend {
    private static final String TAG = "ConnectionManager";
    private static final String SERVER_URL = "http://madrave.herokuapp.com/";

    //Callback interface: how calling objects receive responses asynchronously without delegation
    public interface BackendCallback {
        public void onRequestCompleted(Object result);
        public void onRequestFailed(String message);
    }


    public static void logIn(String email, String password, final BackendCallback callback) {
        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
        StringEntity jsonParams = null;

        try {
            JSONObject json = new JSONObject();
            JSONObject user = new JSONObject();

            user.put("email", email);
            user.put("password", password);
            json.put("user", user);
            jsonParams = new StringEntity(json.toString());
            Log.d(TAG, json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        headers.add(new BasicHeader("Content-Type", "application/json"));
        client.post("api/v1/sessions", jsonParams, headers, new JsonResponseHandler() {
            @Override public void onSuccess() {
                JsonObject result = getContent().getAsJsonObject();

                /*Remember, we defined our User class to have a field backendId.  Therefore, we must move the “id”
property in the object returned to a new property “backendId”.  Some of you might be thinking, “Why not just
change the field of user to “id” rather than backendId”?  The short answer is that this will create some
conflicts in what we will be doing later on, and become more clear in the next lab.*/

                result.addProperty("backendId", result.get("id").toString());
                result.remove("id");

                Log.d(TAG, "Login returned: " + result);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
                User user = gson.fromJson(result, User.class);

                callback.onRequestCompleted(user);
            }

            @Override
            public void onFailure() {
                callback.onRequestFailed(handleFailure(getContent()));
            }
        });
    }

    /**
     * Load the poses from the backend. Requires a user with a valid auth token-- if the token has expired
     * you must call login again, passing credentials again. In other words, if Date.now > user.tokenExpration,
     * the server will reject the request.
     *
     * @param user The user requesting the resources
     */
//    public static void loadPoses(User user, final BackendCallback callback) {
//        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
//
//        List<Header> headers = new ArrayList<Header>();
//        headers.add(new BasicHeader("Accept", "application/json"));
//        headers.add(new BasicHeader("Content-Type", "application/json"));
//        headers.add(new BasicHeader("X-USER-ID", Integer.toString(user.backendId)));
//        headers.add(new BasicHeader("X-AUTHENTICATION-TOKEN", user.authToken));
//
//        client.get("poses", null, headers, new JsonResponseHandler() {
//            @Override
//            public void onSuccess() {
//                JsonArray result = getContent().getAsJsonArray();
//
//                //Sugar and GSON don't play nice, need to ensure the ID property is mapped correctly
//                for (JsonElement element: result) {
//                    JsonObject casted = element.getAsJsonObject();
//                    casted.addProperty("backendId", casted.get("id").toString());
//                    casted.remove("id");
//                }
//
//                Log.d(TAG, "Load returned: " + result);
//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
//                //Pose[] poses = gson.fromJson(result, Pose[].class);
//
//                callback.onRequestCompleted(new ArrayList<>(Arrays.asList(poses)));
//            }
//
//            @Override
//            public void onFailure() {
//                callback.onRequestFailed(handleFailure(getContent()));
//            }
//        });
//    }
//
//    public static void loadRoutines(User user, final BackendCallback callback) {
//        AsyncHttpClient client = new AsyncHttpClient(SERVER_URL);
//
//        List<Header> headers = new ArrayList<Header>();
//        headers.add(new BasicHeader("Accept", "application/json"));
//        headers.add(new BasicHeader("Content-Type", "application/json"));
//        headers.add(new BasicHeader("X-USER-ID", Integer.toString(user.backendId)));
//        headers.add(new BasicHeader("X-AUTHENTICATION-TOKEN", user.authToken));
//
//        client.get("routines", null, headers, new JsonResponseHandler() {
//            @Override
//            public void onSuccess() {
//                JsonArray result = getContent().getAsJsonArray();
//
//                //Sugar and GSON don't play nice, need to ensure the ID property is mapped correctly
//                for (JsonElement element: result) {
//                    JsonObject casted = element.getAsJsonObject();
//                    casted.addProperty("backendId", casted.get("id").toString());
//                    casted.remove("id");
//                }
//
//                Log.d(TAG, "Load returned: " + result);
//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
//                Routine[] routines = gson.fromJson(result, Routine[].class);
//
//                callback.onRequestCompleted(new ArrayList<>(Arrays.asList(routines)));
//            }
//
//            @Override
//            public void onFailure() {
//                callback.onRequestFailed(handleFailure(getContent()));
//            }
//        });
//    }


    /* Convenience methods */
    /**
     * Convenience method for parsing server error responses, since most of the handling is similar.
     * @param response the raw response from a server failure.
     * @return a string with an appropriate error message.
     */
    private static String handleFailure(JsonElement response) {
        String errorMessage = "unknown server error";

        if (response == null)
            return errorMessage;

        JsonObject result = response.getAsJsonObject();

        //Server will return all error messages (except in the case of a crash) as a single level JSON
        //with one key called "message". This is a convention for this server.
        try {
            errorMessage = result.get("message").toString();
        }
        catch (Exception e) {
            Log.d(TAG, "Unable to parse server error message");
        }

        return errorMessage;
    }
}

