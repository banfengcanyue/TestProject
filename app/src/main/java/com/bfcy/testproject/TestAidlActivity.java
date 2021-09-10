package com.bfcy.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import java.util.List;

public class TestAidlActivity extends AppCompatActivity {

    private final String TAG = TestActivity.class.getSimpleName();

    private final String ACTION1 = "android.intent.action.ConnectService";
    private final String ACTION2 = "android.intent.action.BookService";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_aidl);
    }

    public void startService(View view) {
        Intent intent = new Intent(ACTION2);
        // 注意在 Android 5.0以后，不能通过隐式 Intent 启动 service，必须制定包名
        intent.setPackage("com.bfcy.testproject");
        bindService(intent, connection2, Context.BIND_AUTO_CREATE);
    }

    public void getData(View view) {
        try {
            if (manager != null) {
                List<Book> list = manager.getBookList();
                Log.d(TAG, "aidl--getBookList:" + list);
                Log.d(TAG, "aidl--pid:" + Process.myPid());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addData(View view) {
        Book book = new Book("添加的书");
        try {
            if (manager != null) {
                manager.addBook(book);
                Log.d(TAG, "aidl--添加的书");
                Log.d(TAG, "aidl--pid:" + Process.myPid());
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private BookManager manager;

    ServiceConnection connection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            manager = BookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            manager = null;
        }
    };
}