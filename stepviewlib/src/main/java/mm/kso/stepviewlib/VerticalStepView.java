package mm.kso.stepviewlib;

/**
 * Created by Kyawsan Oo on 6/29/2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class VerticalStepView extends View {

    private Paint bgPaint;
    private Paint proPaint;
    private TextPaint textPaint;
    private float bgRadius;
    private float proRadius;
    private int lineBgWidth;
    private int bgColor;
    private int lineProWidth;
    private int proColor;
    private int interval;
    private int bgPositionX;
    private int maxStep;
    private int proStep;
    private int textPaddingLeft;

    private int textMoveTop;
    private int textsize;
    private float starY;
    private float stopY;
    private String[] titles;
    private int border;

    public VerticalStepView(Context context) {
        this(context, null);
    }

    public VerticalStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VerticalStepView);
        bgRadius = ta.getDimension(R.styleable.VerticalStepView_v_bg_radius, 10);
        proRadius = ta.getDimension(R.styleable.VerticalStepView_v_pro_radius, 8);
        lineBgWidth = (int) ta.getDimension(R.styleable.VerticalStepView_v_bg_width, 3f);
        bgColor = ta.getColor(R.styleable.VerticalStepView_v_bg_color, Color.parseColor("#cdcbcc"));
        lineProWidth = (int) ta.getDimension(R.styleable.VerticalStepView_v_pro_width, 2f);
        proColor = ta.getColor(R.styleable.VerticalStepView_v_pro_color, Color.parseColor("#029dd5"));
        interval = (int) ta.getDimension(R.styleable.VerticalStepView_v_interval, 140);
        maxStep = ta.getInt(R.styleable.VerticalStepView_v_max_step, 5);
        proStep = ta.getInt(R.styleable.VerticalStepView_v_pro_step, 3);
        bgPositionX = (int) ta.getDimension(R.styleable.VerticalStepView_v_bgPositionX, 200);
        textPaddingLeft = (int) ta.getDimension(R.styleable.VerticalStepView_v_textPaddingLeft, 40);
        textMoveTop = (int) ta.getDimension(R.styleable.VerticalStepView_v_textMoveTop, 10);
        textsize = (int) ta.getDimension(R.styleable.VerticalStepView_v_textsize, 17);
        ta.recycle();
        init();
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColor);
        bgPaint.setStrokeWidth(lineBgWidth);

        proPaint = new Paint();
        proPaint.setAntiAlias(true);
        proPaint.setStyle(Paint.Style.FILL);
        proPaint.setColor(proColor);
        proPaint.setStrokeWidth(lineProWidth);

        textPaint = new TextPaint();
        textPaint.setTextSize(textsize);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int bgWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            bgWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        } else
            bgWidth = Utils.dip2px(getContext(), 311);
        starY = getPaddingTop() + bgRadius;
        stopY = getPaddingTop() + bgRadius + (maxStep - 1) * interval;
        float bottom = stopY + bgRadius + getPaddingBottom();
        border = bgWidth - (bgPositionX + textPaddingLeft);
        setMeasuredDimension(bgWidth, (int) bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);
        drawProgress(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < maxStep; i++) {
            if (null != titles) {
                canvas.save();
                canvas.translate(bgPositionX + textPaddingLeft, (stopY - (i * interval) - textMoveTop));
                StaticLayout sl = new StaticLayout(titles[i], textPaint, border, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                sl.draw(canvas);
                canvas.restore();
            }
        }
    }

    private void drawProgress(Canvas canvas) {
        float lastBottom = stopY;
        int j=0;

        for (int i = 0; i < proStep; i++) {
            canvas.drawCircle(bgPositionX, stopY - (i * interval), proRadius, proPaint);
        }
        while((proStep-j) >1){
            ++j;
            canvas.drawLine(bgPositionX, (lastBottom), bgPositionX, lastBottom - (j* interval), proPaint);
        }
    }


    private void drawBg(Canvas canvas) {
        canvas.drawLine(bgPositionX, stopY, bgPositionX, starY, bgPaint);
        for (int i = 0; i < maxStep; i++) {
            canvas.drawCircle(bgPositionX, stopY - (i * interval), bgRadius, bgPaint);
        }
    }


    /**
     * @param progress completed steps
     * @param maxStep  no of steps
     * @param titles   titles of steps)
     */
    public void setProgress(int progress, int maxStep, String[] titles) {
        proStep = progress;
        this.maxStep = maxStep;
        this.titles = titles;
        invalidate();
    }
}
