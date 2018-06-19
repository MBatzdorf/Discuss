package de.ur.aue.discuss.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ur.aue.discuss.Adapter.DiscussionsRecyclerViewAdapter;
import de.ur.aue.discuss.Adapter.MessageListAdapter;
import de.ur.aue.discuss.Global.DiscussApplication;
import de.ur.aue.discuss.Models.BaseMessage;
import de.ur.aue.discuss.Models.DiscussionItemElement.DiscussionItem;
import de.ur.aue.discuss.R;

public class DiscussionActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<BaseMessage> mMessageList;

    private EditText newMessageField;
    private Button sendButton;

    private DiscussionItem discussionItem;

    private String username = "";

    private final String DISCUSSIONS_URL = "http://discuss-001-site1.ftempurl.com/api/Discussions";
    private final String ARTICLES_URL = "http://discuss-001-site1.ftempurl.com/api/Articles";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        Toolbar toolbar = findViewById(R.id.toolbarDiscussion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        discussionItem = intent.getParcelableExtra("DiscussionItem");

        toolbar.setTitle(discussionItem.title);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(discussionItem.title);

        newMessageField = findViewById(R.id.edittext_chatbox);
        sendButton = findViewById(R.id.button_chatbox_send);

        username = getSharedPreferences(getString(R.string.shared_prevs_filename), Context.MODE_PRIVATE).getString(getString(R.string.saved_username), username);

        mMessageList = new ArrayList<>();

        mMessageRecycler = (RecyclerView) findViewById(R.id.recyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, mMessageList, username);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageAdapter.notifyDataSetChanged();


        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseMessage newMessage = new BaseMessage("DefaultTitle", mMessageAdapter.getItemCount()+1, newMessageField.getText().toString());
                mMessageAdapter.addMessageItem(newMessage);
                newMessageField.setText("");
                mMessageAdapter.notifyDataSetChanged();
                mMessageRecycler.smoothScrollToPosition(mMessageAdapter.getItemCount());

                if(discussionItem.id == -1) {
                    CreateDiscussionOnServer();
                }else{
                    SendArticleToServer(ARTICLES_URL, newMessage);
                }
            }
        });

        // Only fetch existing articles if this is not a new discussion
        if(discussionItem.id != -1){
            String url = "http://discuss-001-site1.ftempurl.com/api/Articles?discussion_Id=" + Integer.toString(discussionItem.id);
            GetDiscussionFromServer(url);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void GetDiscussionFromServer(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.w("INTERNET", response.toString());

                        for(int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject json = response.getJSONObject(i);
                                int id = json.getInt("Id");
                                String title = json.getString("Name");
                                String content = json.getString("Content");

                                BaseMessage newMessage = new BaseMessage(title, id, content);
                                mMessageAdapter.addMessageItem(newMessage);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mMessageAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("INTERNET", "HTTP Response error");
                        Log.w("INTERNET", error);
                    }
                }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //params.put("discussion_Id",Integer.toString(discussionId));

                return params;
            }
        };

        DiscussApplication app = (DiscussApplication) getApplication();
        app.getVolleyRequestQueue().add(jsonArrayRequest);
    }

    public void SendArticleToServer(String url, BaseMessage message){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("Id", Integer.toString(message.getId()));
            jsonBody.put("Discussion_Id", Integer.toString(discussionItem.id));
            jsonBody.put("Name", message.getTitle());
            jsonBody.put("Content", message.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    Log.i("VOLLEY", responseString);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        DiscussApplication app = (DiscussApplication) getApplication();
        app.getVolleyRequestQueue().add(stringRequest);
    }

    public void CreateDiscussionOnServer(){
        String url = "http://discuss-001-site1.ftempurl.com/api/Discussions";
        GetNumDiscussionsFromServer(url);
    }

    public void SendDiscussionToServer(final String url){
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("Id", Integer.toString(discussionItem.id));
            // TODO Replace magic number with real category id
            jsonBody.put("Category_Id", Integer.toString(1));
            jsonBody.put("Name", discussionItem.title);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                SendArticleToServer(ARTICLES_URL, mMessageAdapter.getItem(0));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    Log.i("VOLLEY", responseString);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        DiscussApplication app = (DiscussApplication) getApplication();
        app.getVolleyRequestQueue().add(stringRequest);
    }

    public void GetNumDiscussionsFromServer(final String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.w("INTERNET", response.toString());
                        discussionItem.id = response.length() + 1;
                        SendDiscussionToServer(url);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("INTERNET", "HTTP Response error");
                        Log.w("INTERNET", error);
                    }
                });

        DiscussApplication app = (DiscussApplication) getApplication();
        app.getVolleyRequestQueue().add(jsonArrayRequest);
    }
}
