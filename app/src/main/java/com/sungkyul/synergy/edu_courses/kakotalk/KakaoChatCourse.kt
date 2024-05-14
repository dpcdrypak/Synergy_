package com.sungkyul.synergy.edu_courses.kakotalk

import android.view.Gravity
import com.sungkyul.synergy.R
import com.sungkyul.synergy.utils.DisplayUtils
import com.sungkyul.synergy.utils.HandGestures
import com.sungkyul.synergy.utils.edu.EduCourse
import com.sungkyul.synergy.utils.edu.EduData
import com.sungkyul.synergy.utils.edu.EduHand
import com.sungkyul.synergy.utils.edu.EduScreen

data class KakaoChatCourse(val eduScreen: EduScreen): EduCourse {
    override val list = ArrayList<EduData>()
    override val width = DisplayUtils.pxToDp(eduScreen.context, eduScreen.width.toFloat())
    override val height = DisplayUtils.pxToDp(eduScreen.context, eduScreen.height.toFloat())

    // 교육 코스를 만든다.
    init {
        list.add(EduData().apply {
            dialog.contentText = "이 공간은<br>친구와 채팅을<br>할 수 있습니다."
            dialog.contentGravity = Gravity.CENTER
            dialog.top = 300.0f
            dialog.bottom = 300.0f
            dialog.start = 50.0f
            dialog.end = 50.0f
            dialog.visibility = true
            cover.visibility = true
            cover.isClickable = true
        })

        /*
        dialog.visibility = false
        cover.visibility = false
            cover.isClickable = false
        arrow.visibility = false
        action.id = "click_message_edit_text"
        hands.add(EduHand(
            id = "tap",
            source = R.drawable.hand,
            x = 200.0f,
            y = 675.0f,
            rotation = 180.0f,
            gesture = HandGestures::tapGesture
        ))
        */

        list.add(EduData().apply {
            dialog.contentText = "메시지를 보내볼까요?"
            dialog.visibility = true
            cover.visibility = true
            cover.isClickable = true
        })

        list.add(EduData().apply {
            dialog.visibility = false
            cover.visibility = false
            cover.isClickable = false
            arrow.visibility = false
            action.id = "click_message_edit_text"
            hands.add(
                EduHand(
                    id = "tap",
                    source = R.drawable.hand,
                    x = 200.0f,
                    y = 675.0f,
                    rotation = 180.0f,
                    gesture = HandGestures.Companion::tapGesture
                )
            )
        })
    }
}