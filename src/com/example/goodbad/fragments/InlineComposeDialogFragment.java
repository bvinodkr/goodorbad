package com.example.goodbad.fragments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodbad.PopUpWindowItem;
import com.example.goodbad.R;
import com.example.goodbad.StoryLineArrayAdapter;
import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeAPI;

public class InlineComposeDialogFragment extends DialogFragment{
	
	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	private static Uri mPhotoUri = null;
	private ImageView ivInsertedDialog;
	private TextView tvUserNameInlineCompose;	
	private static ContentResolver mContentResolver;		
	
	/*private InlineComposeDialogFragmentListener listener;
	
	public interface InlineComposeDialogFragmentListener {
		void onFinishInlineComposeDialog(String inlineTextData, String inlineImageUrl);
	}*/

	public InlineComposeDialogFragment() {
		// Empty constructor required for DialogFragment
	}

	public static InlineComposeDialogFragment newInstance(Uri photoUri, ContentResolver contentResolver) {
		InlineComposeDialogFragment frag = new InlineComposeDialogFragment();
		Bundle args = new Bundle();
		if(photoUri!=null)
			args.putString("title", photoUri.toString());
		else
			args.putString("title", "");
		
		frag.setArguments(args);

		mPhotoUri = photoUri;
		mContentResolver = contentResolver;
		
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setHasOptionsMenu(true);
	}
	
	/*@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof InlineComposeDialogFragmentListener) {
			listener = (InlineComposeDialogFragmentListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + "must implement InlineComposeDialogFragmentListener");
		}
	}

	@Override
	public void onDetach() {	
		super.onDetach();
		listener = null;
	}*/
	
	/*
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		listener.onFinishInlineComposeDialog();	
	}*/
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

		View inLineComposeView = inflater.inflate(R.layout.fragment_inline_compose_dialog, container, false);
		ivInsertedDialog = (ImageView) inLineComposeView.findViewById(R.id.ivInsertedDialog);
		tvUserNameInlineCompose = (TextView) inLineComposeView.findViewById(R.id.tvUserNameInlineCompose);
		final EditText etDataInlineCompose = (EditText) inLineComposeView.findViewById(R.id.etDataInlineCompose);
		
		tvUserNameInlineCompose.setText("SmileSuhas");
		
		Button btnDone = (Button) inLineComposeView.findViewById(R.id.btnDoneInLineCompose);
		btnDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String inlineTextData = etDataInlineCompose.getText()!=null? etDataInlineCompose.getText().toString():null;
				String inlineImageUrl = mPhotoUri!=null? mPhotoUri.toString():null;
				
				/*listener = (InlineComposeDialogFragmentListener) getFragmentManager().findFragmentById(R.id.rlstoryLineFragment);
				listener.onFinishInlineComposeDialog(inlineTextData, inlineImageUrl);*/						
				
				
				//send back the result two the calling fragment from here. this calls the onactivityresult in this
				//case with a request code 100.
				Intent i = new Intent();
				i.putExtra("inlineTextData", inlineTextData);
				i.putExtra("inlineImageUrl", inlineImageUrl);
				getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
				
				dismiss();
			}
		});
		
		if(mPhotoUri==null) {
			ivInsertedDialog.setVisibility(View.GONE);
		} else {
			try {
				Bitmap imageBitmap=BitmapFactory.decodeStream(mContentResolver.openInputStream(mPhotoUri));
				ivInsertedDialog.setImageBitmap(imageBitmap);
				ivInsertedDialog.setContentDescription(mPhotoUri.toString());	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*String path = "file://" + getPath(mPhotoUri);
			try {
				Bitmap image = BitmapFactory.decodeStream(new URL(path).openConnection().getInputStream());
				ivInsertedDialog.setImageBitmap(image);
				ivInsertedDialog.setContentDescription(mPhotoUri.toString());				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
		return inLineComposeView;
	}
	
	public String getPath(Uri uri) {
	    String[] projection = { MediaStore.Images.Media.DATA };
	    CursorLoader loader = new CursorLoader(getActivity(), uri, projection, null, null, null);
	    if(loader!=null) {
		    Cursor cursor = loader.loadInBackground();
		    if( cursor != null){
		    	int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    	cursor.moveToFirst();
		    	return cursor.getString(column_index);
		    }
	    }
	    return "";
	}

}
