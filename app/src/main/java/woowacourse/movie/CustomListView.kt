package woowacourse.movie

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.ListView

class CustomListView : ListView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleArr: Int) : super(context, attrs, defStyleArr)

    /*
        * View의 크기를 확인하기 위해 호출된다.
        * ViewGroup의 경우 계속해서 각 Child View에 대한 측정을 하고, 그에 대한 결과로 자신의 사이즈를 결정한다.
        * */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d(
            "Test",
            "onMeasure : ${MeasureSpec.getSize(widthMeasureSpec)} * ${MeasureSpec.getSize(heightMeasureSpec)}"
        )

        val mode = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.AT_MOST -> "AT MOST"
            MeasureSpec.EXACTLY -> "EXACTLY"
            MeasureSpec.UNSPECIFIED -> "UNSPECIFIED"
            else -> "NTH"
        }

        Log.d("Test", "onMeasure Mode: $mode")

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /*
    * 뷰를 측정하여 화면에 배치 한 후에 호출된다.
    * */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d("Test", "onLayout")
        super.onLayout(changed, l, t, r, b)
    }

    /*
    * 이전 단계에서 계산된 크기와 위치를 기준으로 그릴 수 있다.
    * 특정 뷰의 속성이 변경되었을 때 실행되는 두 가지 메서드가 있다 (invalidate(), requestLayout())
    * */
    override fun onDraw(canvas: Canvas?) {
        Log.d("Test", "onDraw")
        super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.d("Test", "onSizeChanged: height $oldh -> $h")
        super.onSizeChanged(w, h, oldw, oldh)
    }

    /*
    * 어떤 시점에서 뷰의 경계가 변경되었다면,
    * View를 다시 측정하기 위해 requestLayout()을 호출하여 Measure및 Layout 단계를 다시 거칠 수 있다.
    * */
    override fun requestLayout() {
        Log.d("Test", "requestLayout")
        super.requestLayout()
    }

    /*
    * 변경 사항을 보여주고자 하는 특정뷰에 대해 강제로 다시 그리기를 요구하는 메소드
    * */
    override fun invalidate() {
        Log.d("Test", "invalidate")
        super.invalidate()
    }
}
