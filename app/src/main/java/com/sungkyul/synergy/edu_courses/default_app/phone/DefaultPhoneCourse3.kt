package com.sungkyul.synergy.edu_courses.default_app.phone

import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import com.sungkyul.synergy.R
import com.sungkyul.synergy.utils.DisplayUtils
import com.sungkyul.synergy.utils.HandGestures
import com.sungkyul.synergy.utils.edu.EduCourse
import com.sungkyul.synergy.utils.edu.EduData
import com.sungkyul.synergy.utils.edu.EduHand
import com.sungkyul.synergy.utils.edu.EduScreen

data class DefaultPhoneCourse3(val eduScreen: EduScreen): EduCourse {
    override val list = ArrayList<EduData>()
    override val width = DisplayUtils.pxToDp(eduScreen.context, eduScreen.width.toFloat())
    override val height = DisplayUtils.pxToDp(eduScreen.context, eduScreen.height.toFloat())
    //연락처 추가
    // 교육 코스를 만든다.
    init {

        list.add(EduData().apply {
            dialog.contentText = "연락처를 추가하는 화면입니다."
            dialog.contentFont = R.font.pretendard_medium
            dialog.contentSize = 26.0f
            dialog.contentGravity = Gravity.CENTER
            dialog.start = 24.0f
            dialog.end = 24.0f
            dialog.top = 670.0f
            dialog.bottom = 56.0f

            cover.visibility = false
            cover.isClickable = true
            dialog.visibility = true
            dialog.contentColor = R.color.white
            dialog.background = R.drawable.edu_dialog_black_bg
        })

        list.add(EduData().apply {
            dialog.contentText = "이름과 전화번호를 입력하고 저장 버튼을 눌러주세요."
            dialog.top = 300.0f
            dialog.bottom = 400.0f
            dialog.contentColor = R.color.white
            dialog.background = R.drawable.edu_dialog_green_bg
            cover.boxVisibility = false
            cover.boxBorderVisibility = false
            cover.visibility = true

        })

        list.add(EduData().apply {
            dialog.visibility = false
            cover.visibility = false
            cover.isClickable = false
            action.id = "phone_name_edit_text"
            hands.add(
                EduHand(
                    id = "tap",
                    x = 190.0f,
                    y = 300.0f,
                    gesture = HandGestures.Companion::tapGesture
                )
            )
        })

        list.add(EduData().apply {
            action.id = "add_contact"

        })


        list.add(EduData().apply {
            dialog.contentText = "연락처 저장에<br>성공하였습니다."
            dialog.contentGravity = Gravity.CENTER
            dialog.top = 50.0f
            dialog.bottom = 550.0f

            cover.boxLeft = 10.0f
            cover.boxRight = width-10.0f
            cover.boxTop = 350.0f
            cover.boxBottom = 700.0f /*크기가 커질 수록 박스가 커짐 */
            cover.boxVisibility = true
            cover.boxBorderVisibility = true

            dialog.visibility = true
            cover.visibility = true
            dialog.contentColor = R.color.black
            dialog.background = R.drawable.edu_dialog_yellow_bg
        })
    }
}
