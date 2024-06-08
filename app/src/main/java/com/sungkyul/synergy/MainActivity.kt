package com.sungkyul.synergy

import MyProfileFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sungkyul.synergy.databinding.ActivityMainBinding
import com.sungkyul.synergy.my_profile.Time
import com.sungkyul.synergy.learning_space.fragment.ExamResultFragment

/** 시너지 앱 메인 네비게이션 바 + fragment */

private const val Tag_learning = "learn_fragment"
private const val Tag_solving = "solving_fragment"
private const val Tag_review = "review_fragment"
private const val Tag_examResult = "examResult_fragment"
private const val Tag_myProfile = "myProfile_fragment"
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start the time counter
        Time.startTimeCounter()

        setFragment(Tag_learning, LearningFragment())
        //setFragment(Tag_learning, LearningFragment())
        setFragment(Tag_examResult, ExamResultFragment())
        binding.mainNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.learingFragment -> setFragment(Tag_learning, LearningFragment())
                R.id.solvingFragment -> setFragment(Tag_solving, SolvingFragment())
                //R.id.reviewFragment -> setFragment(Tag_review, ReviewFragment())
                R.id.myProfileFrangment -> setFragment(Tag_myProfile, MyProfileFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()
        if (manager.findFragmentByTag(tag) == null) {
            fragTransaction.add(R.id.mainMainFrameLayout, fragment, tag)
        }
        val learning = manager.findFragmentByTag(Tag_learning)
        val solving = manager.findFragmentByTag(Tag_solving)
        // val review = manager.findFragmentByTag(Tag_review)
       // val review = manager.findFragmentByTag(Tag_review)
        val examResult = manager.findFragmentByTag(Tag_examResult)
        val myProfile = manager.findFragmentByTag(Tag_myProfile)

        if (learning != null) {
            if (tag == Tag_learning) {
                fragTransaction.show(learning)
            } else {
                fragTransaction.hide(learning)
            }
        }
        if (solving != null) {
            if (tag == Tag_solving) {
                fragTransaction.show(solving)
            } else {
                fragTransaction.hide(solving)
            }
        }
        // if (review != null) {
        //     if (tag == Tag_review) {
        //         fragTransaction.show(review)
        //     } else {
        //         fragTransaction.hide(review)
        //     }
        // }
        if (examResult != null) {
            if (tag == Tag_examResult) {
                fragTransaction.show(examResult)
            } else {
                fragTransaction.hide(examResult)
            }
        }

//        if (review != null) {
//            if (tag == Tag_review) {
//                fragTransaction.show(review)
//            } else {
//                fragTransaction.hide(review)
//            }
//        }
        if (myProfile != null) {
            if (tag == Tag_myProfile) {
                fragTransaction.show(myProfile)
            } else {
                fragTransaction.hide(myProfile)
            }
        }
        fragTransaction.commitAllowingStateLoss()
    }

}
