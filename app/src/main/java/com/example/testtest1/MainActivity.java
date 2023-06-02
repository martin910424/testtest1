package com.example.testtest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testtest1.dao.UserDao;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void reg(View view){

        startActivity(new Intent(getApplicationContext(),activity_register.class));

    }


    public void login(View view){

        EditText EditTextname = findViewById(R.id.username);
        EditText EditTextpassword = findViewById(R.id.password);

        new Thread(()->{

                UserDao userDao = new UserDao();

                boolean aa = userDao.login(EditTextname.getText().toString(),EditTextpassword.getText().toString());
                int msg = 0;
                if(aa){
                    msg = 1;
                }

                hand1.sendEmptyMessage(msg);

        }).start();


    }
    final Handler hand1 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "登錄成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "登錄失敗", Toast.LENGTH_LONG).show();
            }
        }
    };
}