package com.example.Stareyou;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class CustomIndicator extends LinearLayout {
	private static final String TAG = "CustomIndicator";
	private int mOffsetX;// 指示器偏移值
	private int mHeight; // 指示器的高度
	private MyView mView; // 指示器 view
	private int mScreenW; // 每个tab占据的屏幕宽度
	private int mTab_number; // tab的数量
	private int mColor; // 指示器的颜色
	private int mWidth; // 指示器的宽度

	@SuppressLint("NewApi")
	public CustomIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initCustomIndicator(context, attrs);
	}

	public CustomIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomIndicator(Context context) {
		this(context, null);
	}

	private void initCustomIndicator(Context context, AttributeSet attrs) {
		// 获取xml配置的参数
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.CustomIndicator);
		mWidth = (int) ta.getDimension(R.styleable.CustomIndicator_tab_width,
				150);
		mTab_number = ta.getInt(R.styleable.CustomIndicator_tab_number, 2);
		mColor = ta.getColor(R.styleable.CustomIndicator_tab_color, 0x44ff0000);
		ta.recycle();
		mView = new MyView(getContext());
		this.addView(mView);

	}

	public MyView getViewNewInstance() {
		if (mView == null) {
			mView = new MyView(getContext());
		}
		return mView;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mScreenW = w / mTab_number;// 每个tab所占的屏幕
		mOffsetX = (mScreenW - mWidth) / 2;// 指示器的偏移值
		mHeight = h;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	public class MyView extends View {
		private Paint mPaint;

		public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			initMyView();
		}

		public MyView(Context context, AttributeSet attrs) {
			this(context, attrs, 0);
		}

		public MyView(Context context) {
			this(context, null);
		}

		private void initMyView() {
			mPaint = new Paint();
		}

		@SuppressLint("DrawAllocation")
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			mPaint.setColor(mColor);
			// 画矩形
			Rect rect = new Rect(mOffsetX, 0, mOffsetX + mWidth, mHeight);
			canvas.drawRect(rect, mPaint);
		}

		// viewpager滑动不断调用的方法
		public void drawArguments(int position, float offX) {
			// 偏移值+滑动的值+tab的位置
			mOffsetX = (mScreenW - mWidth) / 2 + (int) (offX * mScreenW)
					+ position * mScreenW;
			invalidate();// 重绘
		}

	}
}