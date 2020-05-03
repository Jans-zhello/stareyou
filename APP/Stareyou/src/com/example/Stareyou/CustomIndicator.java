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
	private int mOffsetX;// ָʾ��ƫ��ֵ
	private int mHeight; // ָʾ���ĸ߶�
	private MyView mView; // ָʾ�� view
	private int mScreenW; // ÿ��tabռ�ݵ���Ļ���
	private int mTab_number; // tab������
	private int mColor; // ָʾ������ɫ
	private int mWidth; // ָʾ���Ŀ��

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
		// ��ȡxml���õĲ���
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
		mScreenW = w / mTab_number;// ÿ��tab��ռ����Ļ
		mOffsetX = (mScreenW - mWidth) / 2;// ָʾ����ƫ��ֵ
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
			// ������
			Rect rect = new Rect(mOffsetX, 0, mOffsetX + mWidth, mHeight);
			canvas.drawRect(rect, mPaint);
		}

		// viewpager�������ϵ��õķ���
		public void drawArguments(int position, float offX) {
			// ƫ��ֵ+������ֵ+tab��λ��
			mOffsetX = (mScreenW - mWidth) / 2 + (int) (offX * mScreenW)
					+ position * mScreenW;
			invalidate();// �ػ�
		}

	}
}