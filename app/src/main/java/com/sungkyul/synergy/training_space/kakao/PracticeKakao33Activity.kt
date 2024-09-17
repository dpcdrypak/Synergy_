package com.sungkyul.synergy.training_space.kakao

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.sungkyul.synergy.R
import com.sungkyul.synergy.courses.kakotalk.KakaoChatCourse
import com.sungkyul.synergy.databinding.ActivityPracticeKakao2Binding
import com.sungkyul.synergy.databinding.ActivityPracticeKakao33Binding
import com.sungkyul.synergy.home.activity.MainActivity
import com.sungkyul.synergy.learning_space.kakaotalk.activity.KakaoMainActivity
import com.sungkyul.synergy.learning_space.kakaotalk.adapter.ChatAdapter
import com.sungkyul.synergy.learning_space.kakaotalk.data.ChatMessage
import com.sungkyul.synergy.training_space.call.problem.ExamCallProblem2Activity
import com.sungkyul.synergy.training_space.kakao.adapter.Chat2Adapter
import com.sungkyul.synergy.training_space.kakao.data.ChatMessage2
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PracticeKakao33Activity : AppCompatActivity() {
    private lateinit var binding: ActivityPracticeKakao33Binding
    private lateinit var timer: CountDownTimer
    private var isTimerRunning = false
    private var remainingTimeInMillis: Long = 300000
    private var pausedTimeInMillis: Long = remainingTimeInMillis // 타이머가 일시정지된 시간
    private var success: Boolean = false // 성공 여부를 나타내는 변수 추가


    private lateinit var chatAdapter: Chat2Adapter
    private val chatList = mutableListOf<ChatMessage2>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeKakao33Binding.inflate(layoutInflater)
        setContentView(binding.root)
        remainingTimeInMillis = intent.getLongExtra("remainingTimeInMillis", 30000)

        chatAdapter = Chat2Adapter(chatList)
        binding.recyclerView.adapter = chatAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        startTimer()


        // 뒤로 가기 키를 눌렀을 때의 이벤트를 처리한다.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // MainActivity로 되돌아 간다.
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        })

        binding.messageEditText.setOnClickListener {
            if (binding.eduScreen.onAction("click_message_edit_text")) {

            }
        }

        binding.sharpButton.setOnClickListener {
            val message = binding.messageEditText.text.toString()
            if (message.isNotEmpty()) {
                val chatMessage = ChatMessage2("나", message, getCurrentTimestamp())
                chatAdapter.addMessage(chatMessage)  // 어댑터에 메시지 추가

                // RecyclerView의 최신 메시지로 스크롤
                binding.recyclerView.scrollToPosition(chatList.size - 1)

                // 메시지 입력 필드 비우기
                binding.messageEditText.text.clear()
            }
        }
        // 문제보기 클릭 시 다이얼로그 띄우기
        binding.problemText.setOnClickListener {
            showProblemDialog()
        }
    }

    override fun onPause() {
        super.onPause()
        if (isTimerRunning) {
            pausedTimeInMillis = remainingTimeInMillis
            timer.cancel() // 타이머를 취소하여 불필요한 시간 감소를 막음
            isTimerRunning = false
        }
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
                binding.timerTextView.text = secondsLeft.toString() // 초를 텍스트뷰에 표시
            }

            override fun onFinish() {
                binding.timerTextView.text = "0" // 타이머 종료 시 "0"으로 표시
                // 타이머가 종료되면 자동으로 실패 처리됨
            }
        }

        timer.start() // 액티비티가 생성되면 타이머 시작
        isTimerRunning = true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showProblemDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        // 커스텀 레이아웃을 설정하기 위한 레이아웃 인플레이터
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialoglayout, null)

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()

        // 다이얼로그 메시지 텍스트뷰 설정
        val numberTextView = dialogView.findViewById<TextView>(R.id.dialogNumber)
        numberTextView.text = "문제 1."

        val messageTextView = dialogView.findViewById<TextView>(R.id.dialogMessage)
        messageTextView.text = "'임영웅'님께 카카오톡 메세지를 보내세요."
        messageTextView.textSize = 20f

        // 확인 버튼 설정
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기

            // 타이머를 재시작
            startTimer(remainingTimeInMillis)
            // 문제 풀이 성공으로 표시
            // saveResult(true)
            // returnToHomeScreen() // 홈 화면으로 이동
        }

        alertDialog.show()

        // 다이얼로그가 나타나면 타이머 멈춤
        if (isTimerRunning) {
            timer.cancel()
            isTimerRunning = false
        }
    }

    private fun returnToHomeScreen() {
        val intent = Intent(this, ExamCallProblem2Activity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.stay, R.anim.stay)
    }


    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    // 키보드를 자동으로 열어주는 함수
    private fun hideKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}