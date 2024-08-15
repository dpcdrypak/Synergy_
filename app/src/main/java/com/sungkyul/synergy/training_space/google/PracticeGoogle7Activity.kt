package com.sungkyul.synergy.training_space.google

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.sungkyul.synergy.R
import com.sungkyul.synergy.courses.accountedu.GooglePutCodeCourse
import com.sungkyul.synergy.databinding.ActivityGooglePutCodeBinding
import com.sungkyul.synergy.databinding.ActivityPracticeGoogle7Binding
import com.sungkyul.synergy.home.activity.MainActivity
import com.sungkyul.synergy.learning_space.accountedu.GoogleMailAddActivity
import com.sungkyul.synergy.training_space.call.problem.ExamCallProblem2Activity

class PracticeGoogle7Activity : AppCompatActivity() {
    private lateinit var binding: ActivityPracticeGoogle7Binding
    private lateinit var timer: CountDownTimer
    private var isTimerRunning = false
    private var remainingTimeInMillis: Long = 300000
    private var pausedTimeInMillis: Long = 0 // 타이머가 일시정지된 시간
    private var success: Boolean = false // 성공 여부를 나타내는 변수 추가


    private var countDownTimer: CountDownTimer? = null
    private var countdownTime: Long = 30 // 초기 카운트다운 시간 (초)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeGoogle7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        remainingTimeInMillis = intent.getLongExtra("remainingTimeInMillis", 30000)

        startTimer()

        startCountdown()

        binding.putCodeEdittext.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                // 포커스가 있을 때는 G- 텍스트를 유지합니다.
                binding.putCodeEdittext.hint = "G-"
            } else {
                // 포커스가 없을 때도 G- 텍스트를 유지합니다.
                binding.putCodeEdittext.hint = "G-"
            }
        }

        binding.putNextButton.setOnClickListener {
            val nextIntent = Intent(this, PracticeGoogle8Activity::class.java)

            // 값을 전달한다.
            nextIntent.putExtras(intent)

            startActivity(nextIntent)
        }
        // editText의 텍스트 변경 감지
        binding.putCodeEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 필요시 구현
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.i("test", "onTextChanged")
                if (s.toString().isNotEmpty()) {
                    // 사용자가 텍스트를 입력한 경우
                    binding.eduScreen.onAction("code_input")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // 필요시 구현
            }
        })
        // 타이머 초기화
        timer = object : CountDownTimer(remainingTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                val secondsLeft = millisUntilFinished / 1000
                binding.timerTextView.text = secondsLeft.toString() // 초를 텍스트뷰에 표시
            }

            override fun onFinish() {
                binding.timerTextView.text = "0" // 타이머 종료 시 "0"으로 표시
            }
        }

        // 문제보기 클릭 시 다이얼로그 띄우기
        binding.problemText.setOnClickListener {
            showProblemDialog()
        }

        timer.start() // 액티비티가 생성되면 타이머 시작
        isTimerRunning = true
    }

    override fun onPause() {
        super.onPause()
        pausedTimeInMillis = remainingTimeInMillis
        timer.cancel() // 타이머를 취소하여 불필요한 시간 감소를 막음
        isTimerRunning = false
    }

    override fun onResume() {
        super.onResume()
        if (!isTimerRunning) {
            startTimer(pausedTimeInMillis)
        }

    }
    private fun startTimer(startTimeInMillis: Long = remainingTimeInMillis) {
        timer = object : CountDownTimer(startTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                val secondsLeft = millisUntilFinished / 1000
                findViewById<TextView>(R.id.timerTextView).text = secondsLeft.toString()
            }

            override fun onFinish() {
                if (!success) { // 성공하지 않았을 때만 실패로 저장
                    findViewById<TextView>(R.id.timerTextView).text = "0"
                    // saveResult(false) // 실패 결과 저장
                }
                // 타이머가 종료되면 자동으로 실패 처리됨
                //  returnToHomeScreen()
            }
        }

        // 문제보기 클릭 시 다이얼로그 띄우기
        findViewById<TextView>(R.id.problemText).setOnClickListener {
            showProblemDialog()
        }

        timer.start() // 액티비티가 생성되면 타이머 시작
        isTimerRunning = true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showProblemDialog() {
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)

        // 커스텀 레이아웃을 설정하기 위한 레이아웃 인플레이터
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialoglayout, null)

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()

        // 다이얼로그 메시지 텍스트뷰 설정
        val numberTextView = dialogView.findViewById<TextView>(R.id.dialogNumber)
        numberTextView.text = "문제 1."

        val messageTextView = dialogView.findViewById<TextView>(R.id.dialogMessage)
        messageTextView.text = "구글 계정 생성을 완료하시오."
        messageTextView.textSize = 20f

        // 확인 버튼 설정
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기

            // ///////saveResult(true) // 문제 풀이 성공으로 표시
            // returnToHomeScreen() // 홈 화면으로 이동
        }

        alertDialog.show()

        // 다이얼로그가 나타나면 타이머 멈춤
        timer.cancel()
        isTimerRunning = false
    }

    private fun returnToHomeScreen() {
        val intent = Intent(this, ExamCallProblem2Activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.stay, R.anim.stay)
    }

    private fun startCountdown() {
        countDownTimer = object : CountDownTimer(countdownTime * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdownTime--
                updateCountdownText()
            }

            override fun onFinish() {
                countdownTime = 0
                updateCountdownText()
                binding.countdownText.setTextColor(Color.parseColor("#1B76EB"))
                binding.countdownText.text = "새 코드 받기"
                binding.countdownText.setOnClickListener {
                    finish() // 현재 액티비티를 종료하여 뒤로 가기 버튼을 눌렀을 때 이전 액티비티로 돌아가지 않도록 합니다.
                }
            }
        }.start()
    }

    private fun updateCountdownText() {
        binding.countdownText.text = "새 코드 받기(${countdownTime.toString()}초)"
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
