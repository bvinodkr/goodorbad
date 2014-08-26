package com.example.goodbad;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class OnTouchCircle extends View {

	Paint paint;
	Paint bitmapPaint;
	//ArrayList<PointF> circles;
	Path path;

	private int strokeWidth = 5;
	private int drawColor = Color.RED;
	private Bitmap bitmap;
	private RelativeLayout rlDrawingPane;
	private Canvas drawCanvas = null;
	private int width;
	private int height;


	private Drawable[] layers;

	public void setDrawColor(int drawColor) {
		invalidate();
		this.drawColor = drawColor;
	}

	public void newDrawing() {
		bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);

		drawCanvas = new Canvas(bitmap);

		path = new Path();
		bitmapPaint = new Paint(Paint.DITHER_FLAG);

		//Added later..
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(12);
		invalidate();
	}

	public String save() {
		this.setDrawingCacheEnabled(true);  
		this.buildDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(this.getDrawingCache());    
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"drawing"+".png";		
		File file = new File(path);    
		System.out.println(file);
		Toast.makeText(getContext(), file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 10, ostream);

			ostream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return path;
	}
	
	public String open() {
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).
				getAbsolutePath()+File.separator+"drawing"+".png";  
		return path; 
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		width = w;
		height = h;
		/*rlDrawingPane = (RelativeLayout) findViewById(R.id.rlDrawingPane);*/
		bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);		
		drawCanvas = new Canvas(bitmap);
	}

	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	//how to clear canvas - http://stackoverflow.com/questions/4650755/clearing-canvas-with-canvas-drawcolor

	public OnTouchCircle(Context context, AttributeSet attrs) {
		super(context, attrs);

		setFocusable(true);
		setFocusableInTouchMode(true);

		//circles = new ArrayList<PointF>();
		setUpDrawing();
	}


	private void setUpDrawing() {
		path = new Path();		
		paint = new Paint();
		bitmapPaint = new Paint(Paint.DITHER_FLAG);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		paint.setColor(drawColor);
		paint.setStyle(Paint.Style.STROKE);		
		paint.setStrokeWidth(strokeWidth);	
		
		/*for(PointF point:circles) {
			canvas.drawCircle(point.x, point.y, 5, paint);
		}*/

		canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
		canvas.drawPath(path, paint);	
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if(event.getAction() == MotionEvent.ACTION_DOWN) { // || event.getAction() == MotionEvent.ACTION_MOVE) {
			/*circles.add(new PointF(event.getX(), event.getY()));			
			postInvalidate();*/

			path.moveTo(event.getX(), event.getY());

			postInvalidate();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {			
			path.lineTo(event.getX(), event.getY());

			/*if(mCanvas!=null) {
				mCanvas.drawPath(path, paint);
				path.reset();

				mCanvas = null;
			}*/

			postInvalidate();
			return true;
		} else if(event.getAction()==MotionEvent.ACTION_UP) {
			path.lineTo(event.getX(), event.getY());
			drawCanvas.drawPath(path, paint);
			path.reset();
		}

		return super.onTouchEvent(event);
	}

}
