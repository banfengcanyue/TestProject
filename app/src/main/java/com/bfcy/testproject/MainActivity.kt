package com.bfcy.testproject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(
                // 直接加载 FlutterActivity，初始路由是 ‘/’
//                FlutterActivity.createDefaultIntent(this)
                // 打开一个自定义 Flutter 初始路由的 FlutterActivity
//                FlutterActivity.withNewEngine().initialRoute("/my_route").build(this)
                // 加载预热的引擎
                FlutterActivity
                    .withCachedEngine("my_engine_id")
                    // 确保你的 Flutter 内容也有一个透明的背景。
                    // 如果你的 Flutter UI 绘制了一个特定的背景颜色，那么你的 FlutterActivity 依旧看起来会是有一个不透明的背景。
                    .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                    .build(this)
            )
        }
    }
}