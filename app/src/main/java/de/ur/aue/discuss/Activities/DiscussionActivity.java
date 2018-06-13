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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.ur.aue.discuss.Adapter.MessageListAdapter;
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

        for(int i = 0; i < 20; i++)
        {
            if(i%2 == 0)
            {
                mMessageList.add(new BaseMessage(username, "Message " + Integer.toString(i), Calendar.getInstance().getTime()));
            }else{
                mMessageList.add(new BaseMessage("Other", "Message MessageMessageMessageMessageMessageMessageMessageMessageMessageMessageMessage " + Integer.toString(100 - i), Calendar.getInstance().getTime()));
            }
        }

        mMessageRecycler = (RecyclerView) findViewById(R.id.recyclerview_message_list);
        mMessageAdapter = new MessageListAdapter(this, mMessageList, username);
        mMessageRecycler.setAdapter(mMessageAdapter);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageAdapter.notifyDataSetChanged();


        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseMessage newMessage = new BaseMessage(username, newMessageField.getText().toString(), Calendar.getInstance().getTime());
                mMessageAdapter.addMessageItem(newMessage);
                newMessageField.setText("");
                mMessageAdapter.notifyDataSetChanged();
                mMessageRecycler.smoothScrollToPosition(mMessageAdapter.getItemCount());

                // TODO update database and fetch updates
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
