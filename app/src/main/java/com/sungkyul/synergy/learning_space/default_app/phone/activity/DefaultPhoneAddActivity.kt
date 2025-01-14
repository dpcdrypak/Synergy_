package com.sungkyul.synergy.learning_space.default_app.phone.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ViewUtils
import androidx.compose.ui.geometry.Rect
import com.sungkyul.synergy.databinding.ActivityDefaultPhoneAddBinding
import com.sungkyul.synergy.courses.default_app.phone.DefaultPhoneCourse3
import com.sungkyul.synergy.home.activity.MainActivity
import com.sungkyul.synergy.utils.AnimUtils
import com.sungkyul.synergy.utils.TextUtils

class DefaultPhoneAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDefaultPhoneAddBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefaultPhoneAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 교육을 정의해보자!
        binding.eduScreen.post {
            binding.eduScreen.course = DefaultPhoneCourse3(binding.eduScreen)

            binding.eduScreen.setOnFinishedCourseListener {
                // 교육 코스가 끝났을 때 어떻게 할지 처리하는 곳이다.

                // DefaultAppActivity로 되돌아 간다.
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            // 교육을 시작한다.
            binding.eduScreen.start(this)
        }

        // 뒤로 가기 키를 눌렀을 때의 이벤트를 처리한다.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // DefaultAppActivity로 되돌아 간다.
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        })

        // 각 버튼의 터치 애니메이션을 초기화한다.
        AnimUtils.initTouchButtonAnimation(binding.cancelButton)
        AnimUtils.initTouchButtonAnimation(binding.saveButton)

        // 이벤트 리스너들을 추가한다.
        binding.cancelButton.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    AnimUtils.startTouchDownButtonAnimation(this, view)
                }

                MotionEvent.ACTION_UP -> {
                    AnimUtils.startTouchUpButtonAnimation(this, view)

                    //finish()
                }
            }
            true
        }
        binding.saveButton.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    AnimUtils.startTouchDownButtonAnimation(this, view)
                }

                MotionEvent.ACTION_UP -> {
                    AnimUtils.startTouchUpButtonAnimation(this, view)

                    // TODO(키보드 접자)
                    TextUtils.hideKeyboard(this, binding.phoneNameEditText)
                    TextUtils.hideKeyboard(this, binding.phoneNumEditText)
                    TextUtils.hideKeyboard(this, binding.phoneEmailEditText)
                    TextUtils.hideKeyboard(this, binding.phoneGroupEditText)

                    checkKeyboardClosed(this){
                        val intent = Intent(this, DefaultPhoneActivity::class.java)
                        intent.putExtra("from", "save_contact") // id 값 바꾸기 - 연락처 저장 눌렀을 때 변화하는 화면을 알아야 하기 때문
                        intent.putExtra("name", binding.phoneNameEditText.text.toString())
                        intent.putExtra("num", binding.phoneNumEditText.text.toString())
                        intent.putExtra("email", binding.phoneEmailEditText.text.toString())
                        intent.putExtra("group", binding.phoneGroupEditText.text.toString())
                        startActivity(intent)
                    }


                }
            }
            true
        }
        binding.phoneNameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.eduScreen.onAction("phone_name_edit_text")
            }

        }
        //이름 적는 칸
        binding.phoneNameEditText.setOnClickListener {
            if (binding.eduScreen.onAction("phone_name_edit_text")) {
                // id가 서로 일치하면 이 부분이 실행된다.
            }
        }
        //전화번호 적는 칸
        binding.phoneNumEditText.setOnClickListener {

            val inputText = binding.phoneNameEditText.text.toString()
            if (inputText == "시너지") {
                binding.eduScreen.onAction("phone_num_edit_text")
            }

           /* if (binding.eduScreen.onAction("phone_name_edit_text")) {
                // id가 서로 일치하면 이 부분이 실행된다.
            }

            */

        }


    }

    fun isKeyboardVisible(activity: Activity): Boolean {
        val rootView: View = activity.window.decorView.rootView
        val rect = android.graphics.Rect()
        rootView.getWindowVisibleDisplayFrame(rect)

        val screenHeight = rootView.height
        val keypadHeight = screenHeight - rect.bottom

        // 키패드가 화면의 10% 이상 차지하면 키보드가 열려있다고 판단함
        return keypadHeight > screenHeight * 0.1
    }

    fun checkKeyboardClosed(activity: Activity, onKeyboardClosed: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        val checkInterval: Long = 456 // 0.456초 간격으로 확인

        val keyboardChecker = object : Runnable {
            override fun run() {
                if (!isKeyboardVisible(activity)) {
                    onKeyboardClosed()
                } else {
                    handler.postDelayed(this, checkInterval)
                }
            }
        }

        handler.post(keyboardChecker)
    }
}
