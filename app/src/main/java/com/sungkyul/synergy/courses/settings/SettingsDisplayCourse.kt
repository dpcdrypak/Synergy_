package com.sungkyul.synergy.courses.settings

import android.view.Gravity
import com.sungkyul.synergy.R
import com.sungkyul.synergy.utils.AdaptiveUtils
import com.sungkyul.synergy.utils.DisplayUtils
import com.sungkyul.synergy.utils.HandGestures
import com.sungkyul.synergy.utils.edu.EduCourse
import com.sungkyul.synergy.utils.edu.EduData
import com.sungkyul.synergy.utils.edu.EduHand
import com.sungkyul.synergy.utils.edu.EduScreen

data class SettingsDisplayCourse(val eduScreen: EduScreen): EduCourse {
    override val list = ArrayList<EduData>()
    override val width = DisplayUtils.pxToDp(eduScreen.context, eduScreen.width.toFloat())
    override val height = DisplayUtils.pxToDp(eduScreen.context, eduScreen.height.toFloat())

    // 교육 코스를 만든다.
    init {
        list.add(EduData().apply {
            dialog.contentText = "\"글자 크기와 스타일\"을<br>눌러보세요."
            dialog.contentGravity = Gravity.CENTER
            dialog.contentFont = R.font.pretendard_medium
            dialog.contentSize = AdaptiveUtils.dialogContentMedium()
            dialog.top = 0.35f
            dialog.bottom = 0.35f
            dialog.start = 0.1f
            dialog.end = 0.1f
            dialog.visibility = true
            cover.visibility = true
            cover.isClickable = true
            dialog.contentColor = R.color.white
            dialog.background = R.drawable.edu_dialog_green_bg
        })

        list.add(EduData().apply {
            dialog.visibility = false
            cover.visibility = false
            cover.isClickable = false
            action.id = "scroll_to_bottom"
            hands.add(
                EduHand(
                    id = "drag",
                    source = R.drawable.hand,
                    x = 0.5f,
                    y = 0.5f,
                    width = 50.0f,
                    height = 75.0f,
                    gesture = HandGestures.Companion::verticalScrollGesture
                )
            )
        })

        list.add(EduData().apply {
            cover.boxLeft = 0.0f
            cover.boxRight = 1.0f
            cover.boxTop = 0.5f
            cover.boxBottom = 0.6f
            cover.boxVisibility = true
            cover.boxBorderVisibility = true

            action.id = "tap_font_item"
            hands.add(
                EduHand(
                    id = "tap",
                    x = 0.5f,
                    y = 0.55f,
                    gesture = HandGestures.Companion::tapGesture
                )
            )
        })
    }
}
