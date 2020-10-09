package com.bfcy.testproject;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BookService extends Service {
    private final String TAG = BookService.class.getSimpleName();
    private List<Book> list;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "aidl--BookService: onCreate");
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Book book = new Book("第" + i + "本书");
            list.add(book);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return bookManager;
    }

    private BookManager.Stub bookManager = new BookManager.Stub() {
        @Override
        public List<Book> getBookList() {
            Log.d(TAG, "aidl--getBookList");
            Log.d(TAG, "aidl--pid:" + Process.myPid());
            return list;
        }

        @Override
        public void addBook(Book book) {
            Log.d(TAG, "aidl--addBook");
            Log.d(TAG, "aidl--pid:" + Process.myPid());
            if (book != null) {
                list.add(book);
            }
            Log.d(TAG, book.toString());
        }
    };

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
