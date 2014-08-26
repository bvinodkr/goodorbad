package com.example.goodbad;

import java.io.File;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.goodbad.fragments.BrushSelectDialogFragment;
import com.example.goodbad.fragments.BrushSelectDialogFragment.BrushSelectDialogListener;

public class DrawingActivity extends FragmentActivity implements BrushSelectDialogListener {

	private String selectedBrushSize;
	private OnTouchCircle onTouchCircle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		onTouchCircle = (OnTouchCircle) findViewById(R.id.onTouchCircle1);

		String title = getIntent().getStringExtra("title");
		ActionBar actionBar = getActionBar();

		if (title != null && !title.isEmpty()) {	
			getActionBar().setTitle(title);
			String s ="<font color='#FFFFFF'>"+ actionBar.getTitle().toString()+ "</font>";
			actionBar.setTitle(Html.fromHtml((s)));
		} else {
			actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Story Tellers</font>"));
		}
		
		ImageButton btnOrange = (ImageButton) findViewById(R.id.imgBtnOrange);
		btnOrange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTouchCircle.setDrawColor(Color.parseColor("#FF6600"));				
			}
		});

		ImageButton btnWhite = (ImageButton) findViewById(R.id.imgBtnWhite);
		btnWhite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTouchCircle.setDrawColor(Color.WHITE);				
			}
		});

		ImageButton btnBlack = (ImageButton) findViewById(R.id.imgBtnBlack);
		btnBlack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTouchCircle.setDrawColor(Color.BLACK);				
			}
		});

		ImageButton btnYellow = (ImageButton) findViewById(R.id.imgBtnYellow);
		btnYellow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTouchCircle.setDrawColor(Color.YELLOW);				
			}
		});

		ImageButton btnBlue = (ImageButton) findViewById(R.id.imgBtnBlue);
		btnBlue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTouchCircle.setDrawColor(Color.BLUE);				
			}
		});

		ImageButton btnGreen = (ImageButton) findViewById(R.id.imgBtnGreen);
		btnGreen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTouchCircle.setDrawColor(Color.GREEN);				
			}
		});

		ImageButton btnRed = (ImageButton) findViewById(R.id.imgBtnRed);
		btnRed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onTouchCircle.setDrawColor(Color.RED);				
			}
		});
	}

	public void onBrushSelect(View v) {	 	
		launchBrushSelectDialog();
	}

	private void launchBrushSelectDialog() {
		FragmentManager fm = getSupportFragmentManager();

		BrushSelectDialogFragment brushSeleceDialog = new BrushSelectDialogFragment();
		brushSeleceDialog.show(fm, "fragment_brush_select");
	}

	@Override
	public void onFinishBrushSelectDialog(String brushSize) {		
		selectedBrushSize = brushSize;
		if(selectedBrushSize.equalsIgnoreCase("small"))
			onTouchCircle.setStrokeWidth(10);
		if(selectedBrushSize.equalsIgnoreCase("medium"))
			onTouchCircle.setStrokeWidth(20);
		if(selectedBrushSize.equalsIgnoreCase("large"))
			onTouchCircle.setStrokeWidth(30);		
	}

	public void onNewClick(View v) {
		onTouchCircle.newDrawing();
	}
	
	public void onEraseClick(View v) {
		onTouchCircle.setDrawColor(Color.WHITE);
		onTouchCircle.setStrokeWidth(30);
	}

	public void onDone(View v) {
		//save image 
		String imageUrl = onTouchCircle.save();
		scanPhoto(imageUrl);
		
		//get the image url
		Intent i = new Intent();
		i.putExtra("drawingImageUrl", imageUrl);
		setResult(RESULT_OK, i);
		finish();
	}
	
	private void scanPhoto(String imageUrl){
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(imageUrl);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}
}
