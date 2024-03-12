package com.sungkyul.synergy.utils

import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.sungkyul.synergy.R

data class EduDialog(
    var titleText: String? = null,
    var titleGravity: Int? = null,
    var contentText: String? = null,
    var contentGravity: Int? = null,
    var contentBolds: List<Pair<Int, Int>>? = null,
    var duration: Long? = null,
    var top: Float? = null,
    var bottom: Float? = null,
    var start: Float? = null,
    var end: Float? = null,
    var visibility: Boolean? = null
)
data class EduImageDialog(
    var titleText: String? = null,
    var source: Int? = null,
    var duration: Long? = null,
    var top: Float? = null,
    var bottom: Float? = null,
    var start: Float? = null,
    var end: Float? = null,
    var visibility: Boolean? = null
)
data class EduVerticalDialog(
    var titleText: String? = null,
    var titleGravity: Int? = null,
    var contentText: String? = null,
    var contentGravity: Int? = null,
    var contentBolds: List<Pair<Int, Int>>? = null,
    var duration: Long? = null,
    var height: Int? = null,
    var visibility: Boolean? = null
)
data class EduCover(
    var duration: Long? = null,
    var boxLeft: Float? = null,
    var boxTop: Float? = null,
    var boxRight: Float? = null,
    var boxBottom: Float? = null,
    var boxVisibility: Boolean? = null,
    var boxStrokeVisibility: Boolean? = null,
    var visibility: Boolean? = null
)
data class EduArrow(
    var duration: Long? = null,
    var endTo: String? = null,
    var visibility: Boolean? = null
)
data class EduAction(
    var id: String? = null,
    var message: String? = null
)
data class EduHand(
    var id: String,
    var source: Int = R.drawable.todo_circle,
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    var width: Float,
    var height: Float,
    var rotation: Float = 0.0f,
    var gesture: ((Context, ImageView) -> AnimatorSet)
)
data class EduData(
    val dialog: EduDialog = EduDialog(),
    val imageDialog: EduImageDialog = EduImageDialog(),
    val topDialog: EduVerticalDialog = EduVerticalDialog(),
    val bottomDialog: EduVerticalDialog = EduVerticalDialog(),
    val cover: EduCover = EduCover(),
    val arrow: EduArrow = EduArrow(),
    val action: EduAction = EduAction(),
    val hands: ArrayList<EduHand> = ArrayList()
)

/*
    ## 소개
    교육 화면이다.

    ## 주의점
    - 단위는 dp이다.

    ## 사용법
    0. 교육을 진행할 액티비티 이름은 Target이라 가정한다.

    1. activity_target.xml 안에 EduScreen을 추가한다.
    ```
    <com.sungkyul.synergy.utils.EduScreen
        android:id="@+id/edu_screen"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    ```

    2. EduCourses 안에 원하는 교육 코스 함수를 만들고, TargetActivity의 onCreate 함수 안에 아래 코드를 작성한다.
    ```
    // 교육을 진행해보자!
    binding.eduScreen.post {
        binding.eduScreen.course = EduCourses.customCourse(
            binding.eduScreen.context,
            binding.eduScreen.width.toFloat(),
            binding.eduScreen.height.toFloat()
        )
        binding.eduScreen.setOnFinishedCourseListener {
            // 교육 코스가 끝났을 때 어떻게 할지 처리하는 곳
        }
        binding.eduScreen.start(this)
    }
    ```

    3. TargetActivity의 onCreate 함수 안에 뒤로 가기 이벤트에 대한 코드를 작성한다.
    ```
    // 뒤로 가기 키를 눌렀을 때의 이벤트를 처리한다.
    onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // MainActivity로 되돌아 간다.
            val intent = Intent(binding.root.context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    })
    ```

    ### 프래그먼트에서 eduListener.onAction 함수 사용
    0. EduScreen에 액션을 보낼 프래그먼트 이름은 Target이라 가정한다.

    1. TargetFragment의 생성자를 다음과 같이 작성한다.
    ```
    class TargetFragment(private val eduListener: EduListener) : Fragment() {
        ...
    }
    ```

    2. TargetFragment 안의 원하는 이벤트 리스너에 eduListener.onAction을 호출한다. 다음은 button의 클릭 리스너 안에 호출하는 예시이다.
    ```
    binding.button.setOnClickListener {
        if(binding.eduScreen.onAction("click_button")) {
            // 보낸 액션이 현재 교육 진행에서 요구하는 액션이라면, 이 부분이 실행된다.
        }
    }
    ```

    3. 액티비티에서 사용할 때 다음과 같이 TargetFragment를 선언하고 사용하면 된다.
    ```
    var targetFragment = TargetFragment(binding.eduScreen)
    ```

    ## Members
    - course: 교육 코스이다.
    - onPosted()
    - onAction(id, message)
    - getAction()
    - setOnFinishedCourseListener(l): 교육 코스가 끝났을 때 이벤트 리스너 l이 호출된다.
    - start(activity): 교육을 시작한다.
    - next()
    - prev()
*/
class EduScreen(context: Context, attrs: AttributeSet?): FrameLayout(context, attrs), EduListener {
    private val eduScreenFragment = EduScreenFragment()
    private var onFinishedCourseListener: (() -> Unit)? = null
    private val currentEduData = EduData(
        EduDialog(
            titleText = "",
            titleGravity = Gravity.START,
            contentText = "",
            contentGravity = Gravity.START,
            contentBolds = listOf(),
            duration = 0,
            top = 0.0f,
            bottom = 0.0f,
            start = 0.0f,
            end = 0.0f,
            visibility = false
        ),
        EduImageDialog(
            titleText = "",
            source = R.drawable.todo_rect,
            duration = 0,
            top = 0.0f,
            bottom = 0.0f,
            start = 0.0f,
            end = 0.0f,
            visibility = false
        ),
        EduVerticalDialog(
            titleText = "",
            titleGravity = Gravity.START,
            contentText = "",
            contentGravity = Gravity.START,
            contentBolds = listOf(),
            duration = 0,
            height = 0,
            visibility = false
        ),
        EduVerticalDialog(
            titleText = "",
            titleGravity = Gravity.START,
            contentText = "",
            contentGravity = Gravity.START,
            contentBolds = listOf(),
            duration = 0,
            height = 0,
            visibility = false
        ),
        EduCover(
            duration = 0,
            boxLeft = 0.0f,
            boxTop = 0.0f,
            boxRight = 0.0f,
            boxBottom = 0.0f,
            boxVisibility = false,
            boxStrokeVisibility = false,
            visibility = false
        ),
        EduArrow(
            duration = 0,
            endTo = "dialog",
            visibility = false
        ),
        EduAction(),
        arrayListOf()
    )
    private var num = -1
    var course = ArrayList<EduData>()

    init {
        eduScreenFragment.setEduListener(this)
    }

    override fun onPosted() {
        next()
    }

    override fun onAction(id: String, message: String?): Boolean {
        // 교육 코스가 시작하지 않았거나 끝났으면, 모든 액션을 다시 사용할 수 있게 된다.
        if(num < 0 || num >= course.size) return true

        val eduData = course[num]
        Log.i(id, message ?: "null")
        if(id == eduData.action.id && (eduData.action.message == null || message == eduData.action.message)) {
            next()
            return true
        }
        return false
    }

    fun getAction(): EduAction {
        return currentEduData.action
    }

    fun setOnFinishedCourseListener(l: (() -> Unit)?) {
        onFinishedCourseListener = l
    }

    fun start(activity: AppCompatActivity) {
        bringToFront()

        setOnClickListener {
            next()
        }
        isClickable = false

        // fragment_edu_screen.xml 화면으로 전환한다.
        activity.supportFragmentManager.beginTransaction()
            .replace(id, eduScreenFragment)
            .commit()
    }

    fun next() {
        num++

        if(num >= course.size) {
            onFinishedCourseListener?.invoke()
            return
        }

        /*
        현재 EduData를 course[num]에 해당하는 EduData로 갱신하는 작업이다.
        */
        val currentDialog = currentEduData.dialog
        val dialog = course[num].dialog
        currentDialog.titleText = dialog.titleText ?: currentDialog.titleText
        currentDialog.titleGravity = dialog.titleGravity ?: currentDialog.titleGravity
        currentDialog.contentText = dialog.contentText ?: currentDialog.contentText
        currentDialog.contentGravity = dialog.contentGravity ?: currentDialog.contentGravity
        currentDialog.contentBolds = dialog.contentBolds ?: currentDialog.contentBolds
        currentDialog.duration = dialog.duration ?: currentDialog.duration
        currentDialog.top = dialog.top ?: currentDialog.top
        currentDialog.bottom = dialog.bottom ?: currentDialog.bottom
        currentDialog.start = dialog.start ?: currentDialog.start
        currentDialog.end = dialog.end ?: currentDialog.end

        val currentCover = currentEduData.cover
        val cover = course[num].cover
        currentCover.duration = cover.duration ?: currentCover.duration
        currentCover.boxLeft = cover.boxLeft ?: currentCover.boxLeft
        currentCover.boxTop = cover.boxTop ?: currentCover.boxTop
        currentCover.boxRight = cover.boxRight ?: currentCover.boxRight
        currentCover.boxBottom = cover.boxBottom ?: currentCover.boxBottom

        val currentArrow = currentEduData.arrow
        val arrow = course[num].arrow
        currentArrow.duration = arrow.duration ?: currentArrow.duration
        currentArrow.endTo = arrow.endTo ?: currentArrow.endTo

        val currentAction = currentEduData.action
        val action = course[num].action
        currentAction.id = action.id
        currentAction.message = action.message

        val currentHands = currentEduData.hands
        val hands = course[num].hands
        currentHands.clear()
        currentHands.addAll(hands)

        /*
        현재 EduData(+ course[num]의 EduData)를 참고하여 교육 화면을 변경한다.
        */
        // 다이얼로그의 제목과 내용을 변경한다.
        eduScreenFragment.setDialogTitle(currentDialog.titleText!!, currentDialog.titleGravity!!)
        eduScreenFragment.setDialogContent(currentDialog.contentText!!, currentDialog.contentGravity!!, currentDialog.contentBolds!!)
        // 다이얼로그를 이동시킨다.
        eduScreenFragment.translateDialog(
            currentDialog.duration!!,
            currentDialog.top!!,
            currentDialog.bottom!!,
            currentDialog.start!!,
            currentDialog.end!!
        )
        // 제목 텍스트가 없으면 숨기고, 있으면 보여준다.
        if(currentDialog.titleText == "") {
            eduScreenFragment.hideDialogTitle()
        } else {
            eduScreenFragment.showDialogTitle()
        }
        // 다이얼로그를 보여줄까 숨길까
        if(currentDialog.visibility == false && dialog.visibility == true) {
            eduScreenFragment.showDialog()
        }
        if(currentDialog.visibility == true && dialog.visibility == false) {
            eduScreenFragment.hideDialog()
        }

        // 박스를 이동시킨다.
        eduScreenFragment.translateBox(
            currentCover.duration!!,
            currentCover.boxLeft!!,
            currentCover.boxTop!!,
            currentCover.boxRight!!,
            currentCover.boxBottom!!
        )
        // 박스를 보여줄까 숨길까
        if(currentCover.boxVisibility == false && cover.boxVisibility == true) {
            eduScreenFragment.showBox()
        }
        if(currentCover.boxVisibility == true && cover.boxVisibility == false) {
            eduScreenFragment.hideBox()
        }
        // 박스 선을 보여줄까 숨길까
        if(currentCover.boxStrokeVisibility == false && cover.boxStrokeVisibility == true) {
            eduScreenFragment.showBoxStroke()
        }
        if(currentCover.boxStrokeVisibility == true && cover.boxStrokeVisibility == false) {
            eduScreenFragment.hideBoxStroke()
        }
        // 커버를 보여줄까 숨길까
        // 커버가 있으면 교육 화면을 클릭할 수 있게 된다.
        if(currentCover.visibility == false && cover.visibility == true) {
            isClickable = true
            eduScreenFragment.showCover()
        }
        if(currentCover.visibility == true && cover.visibility == false) {
            isClickable = false
            eduScreenFragment.hideCover()
        }

        // 화살표를 이동시킨다.
        eduScreenFragment.translateArrowStart(currentArrow.duration!!)
        if(currentArrow.endTo == "dialog") {
            eduScreenFragment.translateArrowEndToDialog(currentArrow.duration!!)
        }
        if(currentArrow.endTo == "box") {
            eduScreenFragment.translateArrowEndToBox(currentArrow.duration!!)
        }
        // 화살표를 보여줄까 숨길까
        if(currentArrow.visibility == false && arrow.visibility == true) {
            eduScreenFragment.showArrow()
        }
        if(currentArrow.visibility == true && arrow.visibility == false) {
            eduScreenFragment.hideArrow()
        }

        /*
        현재 EduData에 있는 손들을 제거하고, course[num]에 있는 손들을 추가한다.
        손 제스처를 화면에 보여준다.
        */
        eduScreenFragment.clearHands()
        for(i in currentHands) {
            eduScreenFragment.addHand(
                id = i.id,
                source = i.source,
                xDp = i.x,
                yDp = i.y,
                widthDp = i.width,
                heightDp = i.height,
                rotation = i.rotation,
                gesture = i.gesture
            )
        }

        /*
        교육 화면 변경 이후에 현재 EduData를 갱신해야 하는 작업은 마지막에 한다.
        */
        currentDialog.visibility = dialog.visibility ?: currentDialog.visibility
        currentCover.boxVisibility = cover.boxVisibility ?: currentCover.boxVisibility
        currentCover.boxStrokeVisibility = cover.boxStrokeVisibility ?: currentCover.boxStrokeVisibility
        currentCover.visibility = cover.visibility ?: currentCover.visibility
        currentArrow.visibility = arrow.visibility ?: currentArrow.visibility
    }

    fun prev() {
        if(num > 0) {
            num -= 2
            next()
        }
    }
}