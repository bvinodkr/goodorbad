package com.example.goodbad.fragments;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodbad.PopUpWindowItem;
import com.example.goodbad.R;

public class InlineComposeDialogFragment extends DialogFragment{
	
	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	private static Uri mPhotoUri = null;
	private ImageView ivInsertedDialog;
	private TextView tvUserNameInlineCompose;		
			
	/*private InlineComposeDialogFragmentListener listener;
	
	public interface InlineComposeDialogFragmentListener {
		void onFinishInlineComposeDialog();
	}

	public InlineComposeDialogFragment() {
		// Empty constructor required for DialogFragment
	}*/

	public static InlineComposeDialogFragment newInstance(Uri photoUri) {
		InlineComposeDialogFragment frag = new InlineComposeDialogFragment();
		Bundle args = new Bundle();
		if(photoUri!=null)
			args.putString("title", photoUri.toString());
		else
			args.putString("title", "");
		
		frag.setArguments(args);

		mPhotoUri = photoUri;
		
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
			throw new ClassCastException(activity.toString() + "must implement ComposeTweetFragmentListener");
		}
	}

	@Override
	public void onDetach() {	
		super.onDetach();
		listener = null;
	}
	
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
		
		tvUserNameInlineCompose.setText("SmileSuhas");
		
		Button btnDone = (Button) inLineComposeView.findViewById(R.id.btnDoneInLineCompose);
		btnDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		if(mPhotoUri==null) {
			ivInsertedDialog.setVisibility(View.GONE);
		} else {
			String path = "file://" + getPath(mPhotoUri);
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
			}
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
