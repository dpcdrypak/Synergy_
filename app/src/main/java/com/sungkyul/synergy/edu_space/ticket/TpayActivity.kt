package com.sungkyul.synergy.edu_space.ticket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.sungkyul.synergy.MainActivity
import com.sungkyul.synergy.R
import com.sungkyul.synergy.databinding.ActivityTpayBinding
import com.sungkyul.synergy.edu_space.kakaotaxi.adapter.ViewPagerAdapter
import com.sungkyul.synergy.utils.edu.EduCourses

class TpayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTpayBinding
    private lateinit var sliderViewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTpayBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_tpay)

        // 교육을 정의해보자!
        binding.eduScreen.post {
            // 교육 코스 customCourse를 지정한다.
            binding.eduScreen.course = EduCourses.TPayCourse(
                binding.eduScreen.context,
                binding.eduScreen.width.toFloat(),
                binding.eduScreen.height.toFloat()
            )
            binding.eduScreen.setOnFinishedCourseListener {
                // 교육 코스가 끝났을 때 어떻게 할지 처리하는 곳이다.

                // MainActivity로 되돌아 간다.
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
            // 교육을 시작한다.
            binding.eduScreen.start(this)
        }

        // 뒤로 가기 키를 눌렀을 때의 이벤트를 처리한다.
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // MainActivity로 되돌아 간다.
                val intent = Intent(binding.root.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        })

        sliderViewPager = findViewById(R.id.sliderViewPager) // R.id.sliderViewPager는 XML 레이아웃에서 설정한 ID로 변경
        sliderViewPager.adapter = ViewPagerAdapter(getPaymentList())
        sliderViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

    private fun getPaymentList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.card, R.drawable.card, R.drawable.pay)
    }
}