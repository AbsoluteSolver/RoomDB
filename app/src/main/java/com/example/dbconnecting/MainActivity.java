package com.example.dbconnecting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addRowBtn, deleteRowBtn, refreshRowsBtn;
    private EditText idInputField, nameInputField, ageInputField;
    private TableLayout table;
    private List<User> usersList;
    private UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addRowBtn = findViewById(R.id.addRowBtn);
        deleteRowBtn = findViewById(R.id.deleteRowBtn);
        refreshRowsBtn = findViewById(R.id.refreshRowsBtn);

        idInputField = findViewById(R.id.id_inputField);
        nameInputField = findViewById(R.id.name_inputField);
        ageInputField = findViewById(R.id.age_inputField);

        table = findViewById(R.id.table);

        userDB = Room.databaseBuilder(MainActivity.this, UserDB.class, "UserDB").build();

        addRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User newUser = new User(
                                idInputField.getText().toString(),
                                nameInputField.getText().toString(),
                                ageInputField.getText().toString()
                        );

                        userDB.getUserDAO().addUser(newUser);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                idInputField.setText("");
                                nameInputField.setText("");
                                ageInputField.setText("");
                            }
                        });
                    }
                }).start();
            }
        });


        deleteRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idInputField.getText().length() > 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            usersList = userDB.getUserDAO().getAllUsers();

                            for (User u : usersList) {
                                if(u.id.equals(idInputField.getText().toString())){
                                    userDB.getUserDAO().deleteUser(u);
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    idInputField.setText("");
                                    nameInputField.setText("");
                                    ageInputField.setText("");
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        refreshRowsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        usersList = userDB.getUserDAO().getAllUsers();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                table.removeAllViewsInLayout();
                                for(User u : usersList){
                                    TableRow.LayoutParams row_params = new TableRow.LayoutParams(
                                            TableRow.LayoutParams.MATCH_PARENT,
                                            100);

                                    TableRow tableRow = new TableRow(MainActivity.this);
                                    tableRow.setLayoutParams(row_params);

                                    CreateTextView(u.id, tableRow);
                                    CreateTextView(u.name, tableRow);
                                    CreateTextView(u.age, tableRow);

                                    table.addView(tableRow);

                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }

    void CreateTextView(String text, TableRow TR){
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        TextView textView = new TextView(MainActivity.this);
        textView.setLayoutParams(params);
        textView.setPadding(10, 10, 10, 10);
        textView.setText(text);
        textView.setMaxEms(2);
        textView.setTextSize(15);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TR.addView(textView);
    }

}