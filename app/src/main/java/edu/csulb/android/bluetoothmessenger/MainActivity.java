package edu.csulb.android.bluetoothmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView chatHistoryListView;
    private ArrayList<String> previousChatNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chatHistoryListView = (ListView) findViewById(R.id.list_chat_history);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newChatIntent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(newChatIntent);
            }
        });


        // Start chat history and allow user to start connection
        // Currently ChatHistory is a stub to test that messages are in the
        // correct order
        chatHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, ChatHistory.class);
                String userInfo = chatHistoryListView.getItemAtPosition(position).toString();
                String splitLines[] = userInfo.split("[\\r?\\n]+");
                String userName = splitLines[0];
                String macAddress = splitLines[1];
                intent.putExtra("MAC-ADDRESS", macAddress);
                intent.putExtra("DEVICE-NAME", userName);
                startActivity(intent);
            }
        });

    }

    // Update chat history when you return
    @Override
    protected void onResume() {
        super.onResume();
        Messages db = new Messages(getApplicationContext());
        previousChatNames = (ArrayList<String>) db.getPreviousChatNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, previousChatNames);
        chatHistoryListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Open profile settings on action_profile click
        if (id == R.id.action_profile) {
            Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(profileIntent);
        }

        return super.onOptionsItemSelected(item);
    }

}
