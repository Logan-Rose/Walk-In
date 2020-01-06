package com.example.walkin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_EditUsers extends AppCompatActivity {

    private String name;
    private String role;
    private FirebaseAuth firebaseAuth;
    private Button add;
    private TextView accountLabel;
    private EditText typeInput;
    private EditText nameInput;
    User user;
    DatabaseReference dataref;
    ListView listViewUsers;
    List<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__edit_users);

        dataref = FirebaseDatabase.getInstance().getReference().child("User");
        listViewUsers = findViewById(R.id.listViewUsers);
        users =  new ArrayList<>();
        user = new User();
        firebaseAuth = FirebaseAuth.getInstance();

        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i);
                showDeleteDialogUser(user.getId(), user.getName());
                return true;
            }
        });


    }

    private void showDeleteDialogUser(final String userId, String userName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_update_user, null);
        final String name = userName;
        final Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteUser);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(userName);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(userId);//serviceID
                b.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
    }

    private boolean deleteUser(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("User").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
    protected void onStart(){
        super.onStart();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = new User();
                    user.setId(postSnapshot.child("id").getValue(String.class));
                    user.setName(postSnapshot.child("name").getValue(String.class));
                    user.setType(postSnapshot.child("type").getValue(String.class));
                    users.add(user);
                }
                User_List usersAdapter = new User_List(Admin_EditUsers.this, users);
                listViewUsers.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
