package com.example.wgapp.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wgapp.MainActivity;
import com.example.wgapp.R;
import com.example.wgapp.models.Commune;
import com.example.wgapp.models.Roommate;
import com.example.wgapp.ui.signIn.FirebaseUIActivity;
import com.example.wgapp.ui.start.StartScreenActivity;
import com.google.gson.Gson;

import java.util.List;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        final ListView settingsListView = root.findViewById(R.id.settings_list_view);


        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();

                switch(item){

                    case "EventLog":
                        Intent EventLogIntent = new Intent(getContext(), EventLogActivity.class);

                        startActivity(EventLogIntent);

                        //toDo
                        break;
                    case "Share Invitation Link":
                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                        whatsappIntent.setType("text/plain");
                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, MainActivity.getCommune().getCommuneLink());
                        try {
                            startActivity(whatsappIntent);
                        } catch (android.content.ActivityNotFoundException ex) {

                        }
                        break;
                    case "User Data":
                        Gson gson = new Gson();
                        String usrData = gson.toJson(MainActivity.getLocalUser());

                        Intent usrDatIntent = new Intent(getContext(), PlainTextActivity.class);
                        usrDatIntent.putExtra("data", usrData);
                        startActivity(usrDatIntent);
                        //todo
                        break;
                    case "Leave WG":


                        Roommate usr = MainActivity.getLocalUser();
                        usr.setCommuneID("None");
                        usr.setBudget(0);
                        MainActivity.setLocalUser(usr);
                        MainActivity.getUserWriteRef().setValue(MainActivity.getLocalUser());

                        Commune com = MainActivity.getCommune();
                        List<Roommate> li = com.getRoommates();
                        li.remove(usr);
                        com.setRoommates(li);
                        MainActivity.setCommune(com);
                        MainActivity.getCommuneWriteRef().setValue(com);

                        MainActivity.setCommune(new Commune());

                        Intent intent = new Intent(getContext(), StartScreenActivity.class);
                        startActivity(intent);
                        break;
                    case "Logout":
                        FirebaseUIActivity.signOut(getContext());
                        Intent signOutintent = new Intent(getContext(), MainActivity.class);
                        startActivity(signOutintent);
                        break;
                    default:
                        break;

                }

            }
        });

        settingsViewModel.getSettingsList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> settingsList) {
                // update UI
                android.widget.ListAdapter adapter = new ArrayAdapter<String>( getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, settingsList);
                // Assign adapter to ListView
                settingsListView.setAdapter(adapter);
            }
        });

        return root;
    }
}