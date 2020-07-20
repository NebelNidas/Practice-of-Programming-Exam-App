package de.cicero_interactive.practiceofprogrammingexamapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class ChatFragment  extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ChatAdapter adapter;

    Switch sender_switch;
    EditText message_text;
    Button send_button;

    private MyDatabase myDatabase;
    private ArrayList<ChatMessage> messages;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inf = inflater.inflate(R.layout.fragment_chat, container, false);

        getActivity().setTitle(R.string.chat_with_your_doctor);

        sender_switch = inf.findViewById(R.id.sender_switch);
        message_text = inf.findViewById(R.id.message_text);
        send_button = inf.findViewById(R.id.send_button);

        final MyDatabase myDatabase = new MyDatabase(getActivity());
        try {
            messages = myDatabase.getMessages();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView = inf.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity()) {{
            setStackFromEnd(true);
        }};
        adapter = new ChatAdapter(getActivity(), messages);

        buildRecyclerView();

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sender = "self";
                if (sender_switch.isChecked() == false) {
                    sender = "Dr. Jones";
                }
                try {
                    ArrayList<ChatMessage> messages_new = new ArrayList<ChatMessage>(messages);
                    messages_new.add(new ChatMessage(sender, message_text.getText().toString(), new Date()));
                    myDatabase.insertMessages(messages_new);
                    messages = messages_new;
                    buildRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return inf;
    }


    public void buildRecyclerView() {
        // Setting hasFixedSize to true will increase performance
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(getActivity(), messages);
        recyclerView.setAdapter(adapter);
    }

    // Filtering
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.medical_training_menu, menu);

        MenuItem search_item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search_item.getActionView();

        searchView.setImeOptions((EditorInfo.IME_ACTION_DONE));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }*/
}