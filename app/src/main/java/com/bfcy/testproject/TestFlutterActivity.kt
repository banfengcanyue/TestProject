package com.bfcy.testproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.android.FlutterFragment

class TestFlutterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_flutter)

        // 如果 FlutterActivity 和 FlutterFragment 都适用，优先使用FlutterActivity，因为FlutterActivity 快捷易用
        startFlutter1()
        startFlutter2()
        startFlutter3()
        startFlutter4()
    }

    // 冷加载 FlutterActivity
    private fun startFlutter1() {
        findViewById<Button>(R.id.testFlutter1).setOnClickListener {
            startActivity(
                // 冷加载 FlutterActivity，初始路由是'/'
                FlutterActivity.createDefaultIntent(this)
                // 打开一个自定义 Flutter 初始路由的 FlutterActivity
//                FlutterActivity.withNewEngine().initialRoute("/my_route").build(this)
            )
        }
    }

    // 热加载 FlutterActivity
    private fun startFlutter2() {
        findViewById<Button>(R.id.testFlutter2).setOnClickListener {
            startActivity(
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

    // 冷加载 FlutterFragment
    private fun startFlutter3() {
        findViewById<Button>(R.id.testFlutter3).setOnClickListener {
            startFlutterFragment()
        }
    }

    // 热加载 FlutterFragment
    private fun startFlutter4() {
        findViewById<Button>(R.id.testFlutter4).setOnClickListener {
            startFlutterFragmentPreWarmed()
        }
    }

    companion object {
        // Define a tag String to represent the FlutterFragment within this
        // Activity's FragmentManager. This value can be whatever you'd like.
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    }

    // Declare a local variable to reference the FlutterFragment so that you
    // can forward calls to it later.
    private var flutterFragment: FlutterFragment? = null

    private fun startFlutterFragment() {
        // Get a reference to the Activity's FragmentManager to add a new
        // FlutterFragment, or find an existing one.
        val fragmentManager: FragmentManager = supportFragmentManager

        // Attempt to find an existing FlutterFragment, in case this is not the
        // first time that onCreate() was run.
        flutterFragment = fragmentManager
            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?

        // Create and attach a FlutterFragment if one does not exist.
        if (flutterFragment == null) {
            val newFlutterFragment = FlutterFragment.createDefault()
            flutterFragment = newFlutterFragment
            fragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container,
                    newFlutterFragment,
                    TAG_FLUTTER_FRAGMENT
                )
                .commit()
        }
    }

    private fun startFlutterFragmentPreWarmed() {
        // Get a reference to the Activity's FragmentManager to add a new
        // FlutterFragment, or find an existing one.
        val fragmentManager: FragmentManager = supportFragmentManager

        // Attempt to find an existing FlutterFragment, in case this is not the
        // first time that onCreate() was run.
        flutterFragment = fragmentManager
            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?

        // Create and attach a FlutterFragment if one does not exist.
        if (flutterFragment == null) {
            // 使用预热引擎实例化FlutterFragment
//            val newFlutterFragment = FlutterFragment.createDefault()
            val newFlutterFragment: FlutterFragment =
                FlutterFragment.withCachedEngine("my_engine_id").build()
            flutterFragment = newFlutterFragment
            fragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container,
                    newFlutterFragment,
                    TAG_FLUTTER_FRAGMENT
                )
                .commit()
        }
    }

    // FlutterFragment 依赖宿主 Activity 的各种回调
    override fun onPostResume() {
        super.onPostResume()
        flutterFragment?.onPostResume()
    }

    override fun onNewIntent(@NonNull intent: Intent) {
        super.onNewIntent(intent)
        flutterFragment?.onNewIntent(intent)
    }

    override fun onBackPressed() {
        flutterFragment?.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        flutterFragment?.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    override fun onUserLeaveHint() {
        flutterFragment?.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        flutterFragment?.onTrimMemory(level)
    }
}