package com.example.goodbad.fragments;

import com.example.goodbad.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class BrushSelectDialogFragment extends DialogFragment {

	private View layoutBrushSelect;
	private Button btnSmall;
	private Button btnMedium;
	private Button btnLarge; 
	
	private String selectedBrushSize;
	
	public interface BrushSelectDialogListener {
		void onFinishBrushSelectDialog(String brushSize);
	}


	public BrushSelectDialogFragment() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		layoutBrushSelect = inflater.inflate(R.layout.fragment_dialog_brush_select, container);
		
		btnSmall = (Button) layoutBrushSelect.findViewById(R.id.btnSmall);
		btnMedium = (Button) layoutBrushSelect.findViewById(R.id.btnMedium);
		btnLarge = (Button) layoutBrushSelect.findViewById(R.id.btnLarge);
		
		btnSmall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				
				BrushSelectDialogListener listener = (BrushSelectDialogListener) getActivity();
				listener.onFinishBrushSelectDialog("small");
				
				dismiss();
			}
		});

		btnMedium.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				
				BrushSelectDialogListener listener = (BrushSelectDialogListener) getActivity();
				listener.onFinishBrushSelectDialog("medium");
				
				dismiss();
			}
		});
		
		btnLarge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				
				BrushSelectDialogListener listener = (BrushSelectDialogListener) getActivity();
				listener.onFinishBrushSelectDialog("large");
				
				dismiss();
			}
		});

		return layoutBrushSelect;
	}
}
