package com.bfcy.testproject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bfcy.testndk.TestDemo;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        textView.setText(new TestDemo().getString());
        testClipboard();
    }

    public void test() {
        System.out.println("Hello World");
    }

    private void testClipboard() {
        String originText = "这里是要复制的文字";
        Uri originUri = Uri.parse("http://www.baidu.com");
        Intent originIntent = new Intent();
        originIntent.putExtra("content", "这是测试的Intent");
        //获取剪贴板管理器：
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建文本型ClipData，"Label"是任意文字的标签
        ClipData clipDataText = ClipData.newPlainText("Label", originText);
        // 创建URL型ClipData
        ClipData clipDataUrl = ClipData.newRawUri("Label", originUri);
        // 创建Intent型ClipData
        ClipData clipDataIntent = ClipData.newIntent("Label", originIntent);

        // 上面3中方式创建了只包含一个item的ClipData，要添加多个item，使用addItem()方法
        clipDataText.addItem(new ClipData.Item(originUri));
        clipDataText.addItem(new ClipData.Item(originIntent));
        // 将ClipData内容放到系统剪贴板里。
        clipboardManager.setPrimaryClip(clipDataText);

        // 获取系统剪贴板中的内容
        ClipData clipData = clipboardManager.getPrimaryClip();
        String text = (String) clipData.getItemAt(0).getText();
        Uri uri = clipData.getItemAt(1).getUri();
        Intent intent = clipData.getItemAt(2).getIntent();

        Log.i("tag", "clipData-text: " + text);
        Log.i("tag", "clipData-uri: " + uri.toString());
        Log.i("tag", "clipData-intent: " + intent.getStringExtra("content"));
        // 打印信息
//        I/tag: clipData-text: 这里是要复制的文字
//        I/tag: clipData-uri: http://www.baidu.com
//        I/tag: clipData-intent: 这是测试的Intent

    }

    public void testAidl(View view) {
        Intent intent = new Intent(this, TestAidlActivity.class);
        startActivity(intent);
    }
}
