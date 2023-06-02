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
import com.example.testtest1.entity.User;

import java.lang.ref.WeakReference;

public class activity_register extends AppCompatActivity {
    EditText name = null;
    EditText username = null;
    EditText password = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }
    public void register(View view) {


        String cname = name.getText().toString();
        String cusername = username.getText().toString();
        String cpassword = password.getText().toString();


        if (cname.length() < 2 || cusername.length() < 2 || cpassword.length() < 2) {
            Toast.makeText(getApplicationContext(), "輸入信息不符合要求請重新輸入", Toast.LENGTH_LONG).show();
            return;

        }


        User user = new User();

        user.setName(cname);
        user.setUsername(cusername);
        user.setPassword(cpassword);

        new Thread(()-> {

                int msg = 0;

                UserDao userDao = new UserDao();

                User uu = userDao.findUser(user.getUsername());

                if (uu != null) {
                    msg = 1;
                }

                boolean flag = userDao.register(user);
                if (flag) {
                    msg = 2;
                }
                handler.sendEmptyMessage(msg);

        }).start();
    }
    private static class MyHandler extends Handler {
        private final WeakReference<activity_register> activityRef;

        public MyHandler(activity_register activity) {
            super(Looper.getMainLooper());
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            activity_register activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (msg.what == 0) {
                Toast.makeText(activity.getApplicationContext(), "註冊失敗", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(activity.getApplicationContext(), "該賬號已經存在，請換一個賬號", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2) {
                Intent intent = new Intent();
                intent.putExtra("a", "註冊");
                activity.setResult(RESULT_CANCELED, intent);
                activity.finish();
            }
        }
    }

    private final MyHandler handler = new MyHandler(this);

}