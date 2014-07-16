package com.example.goodbad.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goodbad.PopUpImageAdapter;
import com.example.goodbad.PopUpWindowItem;
import com.example.goodbad.R;
import com.parse.ParseUser;

public class ComposeStoryFragment extends DialogFragment {

	private ComposeStoryFragmentListener listener;
	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	private ImageView ivComposePopUpItemImage;	
	PopupWindow popupWindow = null;
	
	public interface ComposeStoryFragmentListener {
		void onFinishComposeDialog();
	}

	public ComposeStoryFragment() {
		// Empty constructor required for DialogFragment
	}

	public static ComposeStoryFragment newInstance(String title) {
		ComposeStoryFragment frag = new ComposeStoryFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);

		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof ComposeStoryFragmentListener) {
			listener = (ComposeStoryFragmentListener) activity;
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
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

		View layoutComposeStory = inflater.inflate(R.layout.fragment_compose_story, container);

		ivComposePopUpItemImage = (ImageView) layoutComposeStory.findViewById(R.id.ivComposePopUpItemImage);
		TextView tvCompose;
		tvCompose=(TextView) layoutComposeStory.findViewById(R.id.tvComposeUserName);
		String email=ParseUser.getCurrentUser().getEmail();
		tvCompose.setText(email);
		listener.onFinishComposeDialog();		

		/*ivComposePopUpItemImage.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				posX = (int) event.getX();
				posY = (int) event.getY();	            
				return true;				
			}
		});*/

		ivComposePopUpItemImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int location[] = {0,0};
				v.getLocationOnScreen(location);
				Toast.makeText(getActivity(), "" + location[0] + " " + location[1], Toast.LENGTH_SHORT).show();
								
				// Inflate the popup_layout.xml
				if(popupWindow!=null && popupWindow.isShowing()) {
					popupWindow.dismiss();
				} else {
					View popUpView = inflater.inflate(R.layout.pop_up_layout, container, false);
				
					// Creating the PopupWindow
					popupWindow = new PopupWindow(getActivity());
					popupWindow.setContentView(popUpView);
					popupWindow.setWidth(330);
					popupWindow.setHeight(170);
					popupWindow.setOutsideTouchable(true);
					popupWindow.setFocusable(true);									
	
					/*// Clear the default translucent background
					popupWindow.setBackgroundDrawable(new BitmapDrawable());
					popupWindow.showAsDropDown(getCurrentFocus());*/
	
	
					//v.getLocationOnScreen(location);
	
					// Displaying the popup at the specified location, + offsets.
					popupWindow.showAtLocation(popUpView,  Gravity.NO_GRAVITY, 100, location[1]-20);
	
					populatePopUpWindowItems();
	
					GridView gridview = (GridView) popUpView.findViewById(R.id.gvPopUp);		
					gridview.setAdapter(new PopUpImageAdapter(getActivity(), popUpWindowItemList));
	
					gridview.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
							Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		});

		return layoutComposeStory;

	}

	private void populatePopUpWindowItems() {
		popUpWindowItemList.clear();
		popUpWindowItemList.add(new PopUpWindowItem("Gallery", R.drawable.gallery));
		popUpWindowItemList.add(new PopUpWindowItem("Photo", R.drawable.camera));
		popUpWindowItemList.add(new PopUpWindowItem("Video", R.drawable.video));
		popUpWindowItemList.add(new PopUpWindowItem("Audio", R.drawable.voice));
		popUpWindowItemList.add(new PopUpWindowItem("TBD", R.drawable.voice));
		popUpWindowItemList.add(new PopUpWindowItem("TBD", R.drawable.voice));
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {		
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.compose_action_bar, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miActionBarComposeIcon:
			// Do Activity menu item stuff here
			return false;
		case R.id.miPopUpIcon:
			// Not implemented here
			return false;
		case R.id.miPostStoryIcon:
			onPostStoryIconClick(item);
			return true;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onPostStoryIconClick(MenuItem mi) {
		Toast.makeText(getActivity(), "Post Story", Toast.LENGTH_SHORT).show();
	}

}
