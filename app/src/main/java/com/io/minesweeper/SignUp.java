package com.io.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SignUp extends AppCompatActivity {

    LinearLayout layout_users;
    EditText et_name;
    Button btn_add;
    Button btn_delete;
    Button btn_play;
    View.OnClickListener playLoggedIn;
    boolean delete = false;
    HashSet<Integer> chosen_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        layout_users = findViewById(R.id.layout_users);
        et_name = findViewById(R.id.et_name);
        btn_add = findViewById(R.id.btn_add);
        btn_delete = findViewById(R.id.btn_del);
        btn_play = findViewById(R.id.btn_play);

        btn_delete.setBackgroundColor(getResources().getColor(R.color.purple_500));
        btn_play.setBackgroundColor(Color.GRAY);
        btn_play.setEnabled(false);

        delete = false;
        chosen_users = new HashSet<>();

        playLoggedIn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delete) {
                    if (chosen_users.contains(view.getId())) {
                        chosen_users.remove(view.getId());
                        btn_play.setBackgroundColor(Color.GRAY);
                        btn_play.setEnabled(false);
                    }
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUp.this);
                    // Toast.makeText(SignUp.this, "Id = " + view.getId(), Toast.LENGTH_SHORT).show();
                    dataBaseHelper.deleteOne(view.getId());
                    layout_users.removeView(view);
                    // Toast.makeText(SignUp.this, "Success = " + suc, Toast.LENGTH_SHORT).show();
                }
                else {
                    int id_to_remember = ((Button) view).getId();
                    if (chosen_users.contains(id_to_remember)) {
                        chosen_users.remove(id_to_remember);
                        ((Button)view).setBackgroundColor(Color.LTGRAY);
                        btn_play.setBackgroundColor(Color.GRAY);
                        btn_play.setEnabled(false);
                    }
                    else {
                        if (chosen_users.size() == 0) {
                            chosen_users.add(id_to_remember);
                            ((Button)view).setBackgroundColor(getResources().getColor(R.color.purple_200));

                            btn_play.setBackgroundColor(getResources().getColor(R.color.purple_500));
                            btn_play.setEnabled(true);
                        }
                        else {
                            Toast.makeText(SignUp.this, "Too many players!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserModel userModel = null;
                try {
                    userModel = new UserModel(-1, et_name.getText().toString());
                    //Toast.makeText(SignUp.this, userModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(SignUp.this, "Error creating database", Toast.LENGTH_SHORT).show();
                    userModel = new UserModel(-1, "error");
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUp.this);
                boolean success = dataBaseHelper.addOne(userModel);

                if (success) {
                    Button btn = new Button(SignUp.this);
                    userModel = dataBaseHelper.getOneByName(userModel.getName());
                    btn.setId(userModel.getId());
                    btn.setText(userModel.getName());
                    btn.setOnClickListener(playLoggedIn);
                    btn.setBackgroundColor(Color.LTGRAY);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(4, 4, 4, 4);
                    btn.setLayoutParams(params);

                    layout_users.addView(btn);
                }
                else {
                    Toast.makeText(SignUp.this, "Choose another name!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (! delete) {
                    delete = true;
                    btn_delete.setBackgroundColor(Color.RED);
                }
                else {
                    delete = false;
                    btn_delete.setBackgroundColor(getResources().getColor(R.color.purple_500));
                }
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer[] users_to_pass = chosen_users.toArray(new Integer[0]);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUp.this);
                UserModel[] userModel = new UserModel[1];

                userModel[0] = dataBaseHelper.getOneById(users_to_pass[0]);

                Intent i = new Intent(SignUp.this, LevelActivity.class);
                i.putExtra("user_id", users_to_pass[0]);
                i.putExtra("user_name", userModel[0].getName());
                startActivity(i);
            }
        });

        showUsers();
    }

    public void showUsers() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUp.this);
        List<UserModel> everyone = dataBaseHelper.getEveryone();
        layout_users.removeAllViews();

        for (int i = 0; i < everyone.size(); ++i) {
            Button btn = new Button(SignUp.this);
            btn.setId(everyone.get(i).getId());
            btn.setText(everyone.get(i).getName());
            btn.setOnClickListener(playLoggedIn);
            btn.setBackgroundColor(Color.LTGRAY);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 4, 4, 4);
            btn.setLayoutParams(params);

            layout_users.addView(btn);
        }
    }
}