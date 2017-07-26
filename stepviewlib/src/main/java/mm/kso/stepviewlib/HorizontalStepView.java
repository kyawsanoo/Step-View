package mm.kso.stepviewlib;

/**
 * Created by Kyawsan Oo on 6/29/2017.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;


public class HorizontalStepView extends View{

    private Paint bgPaint;
    private Paint proPaint;
    private float bgRadius;
    private float proRadius;
    private float startX;
    private float stopX;
    private float bgCenterY;
    private int lineBgWidth;
    private int bgColor;
    private int lineProWidth;
    private int proColor;
    private int textPadding;
    private int maxStep;
    private int textSize;
    private int proStep;
    private int interval;
    String customFont;
    private String[] titles = {"step1", "step2", "step3", "step4"};

    public HorizontalStepView(Context context) {
        this(context, null);
    }

    public HorizontalStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorizontalStepView);
        bgRadius = ta.getDimension(R.styleable.HorizontalStepView_h_bg_radius, 10);
        proRadius = ta.getDimension(R.styleable.HorizontalStepView_h_pro_radius, 8);
        lineBgWidth = (int) ta.getDimension(R.styleable.HorizontalStepView_h_bg_width, 3f);
        bgColor = ta.getColor(R.styleable.HorizontalStepView_h_bg_color, Color.parseColor("#cdcbcc"));
        lineProWidth = (int) ta.getDimension(R.styleable.HorizontalStepView_h_pro_width, 2f);
        proColor = ta.getColor(R.styleable.HorizontalStepView_h_pro_color, Color.parseColor("#029dd5"));
        textPadding = (int) ta.getDimension(R.styleable.HorizontalStepView_h_text_padding, 20);
        maxStep = ta.getInt(R.styleable.HorizontalStepView_h_max_step, 5);
        textSize = (int) ta.getDimension(R.styleable.HorizontalStepView_h_textsize, 20);
        proStep = ta.getInt(R.styleable.HorizontalStepView_h_pro_step, 1);
        customFont = ta.getString(R.styleable.HorizontalStepView_h_custom_font);

        ta.recycle();
        init(context);
    }

    private void init(Context context) {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColor);
        bgPaint.setStrokeWidth(lineBgWidth);
        bgPaint.setTextSize(textSize);
        bgPaint.setTextAlign(Paint.Align.CENTER);

        proPaint = new Paint();
        proPaint.setAntiAlias(true);
        proPaint.setStyle(Paint.Style.FILL);
        proPaint.setColor(proColor);
        proPaint.setStrokeWidth(lineProWidth);
        proPaint.setTextSize(textSize);
        proPaint.setTextAlign(Paint.Align.CENTER);
        if(customFont!=null) setCustomFont(context, customFont);
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Log.e("font_path", asset);
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: "+e.getMessage());
            return false;
        }
        bgPaint.setTypeface(tf);
        proPaint.setTypeface(tf);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int bgWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            bgWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        } else
            bgWidth = Utils.dip2px(getContext(), 311);

        int bgHeight;
        if (heightMode == MeasureSpec.EXACTLY) {
            bgHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        } else
            bgHeight = Utils.dip2px(getContext(), 49);
        float left = getPaddingLeft() + bgRadius;
        stopX = bgWidth - bgRadius;
        startX = left;
        bgCenterY = bgHeight / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        interval = (int) ((stopX - startX) / (maxStep - 1));
        drawBg(canvas);
        drawProgress(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < maxStep; i++) {
            if (i < proStep) {
                if (null != titles && i<titles.length)
                    canvas.drawText(titles[i], startX + (i * interval), bgCenterY - textPadding, proPaint);

            } else {
                if (null != titles && i<titles.length) {
                    String title = titles[i];
                    if (null == title) continue;
                    canvas.drawText(title, startX + (i * interval), bgCenterY - textPadding, bgPaint);
                }
            }
        }
    }


    private void drawProgress(Canvas canvas) {
        float lastLeft = startX;
        int j=0;

        for (int i = 0; i < proStep; i++) {
            canvas.drawCircle(startX + (i * interval), bgCenterY, proRadius, proPaint);
        }
        while((proStep-j) >1){
            ++j;
            canvas.drawLine(j*lastLeft, bgCenterY, lastLeft + (j * interval), bgCenterY, proPaint);
        }
    }

    private void drawBg(Canvas canvas) {
        canvas.drawLine(startX, bgCenterY, stopX, bgCenterY, bgPaint);
        for (int i = 0; i < maxStep; i++) {
            canvas.drawCircle(startX + (i * interval), bgCenterY, bgRadius, bgPaint);
        }
    }

    /**
     * @param progress completed steps
     * @param maxStep  no of steps
     * @param titles   titles of steps

     */

    public void setProgress(int progress, int maxStep, String[] titles) {
        proStep = progress;
        this.maxStep = maxStep;
        this.titles = titles;
        invalidate();
    }


}
