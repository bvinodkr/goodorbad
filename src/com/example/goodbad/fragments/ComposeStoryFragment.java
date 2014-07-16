package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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

public class ComposeStoryFragment extends Fragment {

	private ComposeStoryFragmentListener listener;
	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	private ImageView ivComposePopUpItemImage;	
	private PopupWindow popupWindow = null;
	private ImageView ivInsertedImageComposeStory;
	private EditText etComposeStory;
	
	public interface ComposeStoryFragmentListener {
		void onFinishComposeDialog(String composeData, boolean fromPost);
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
		
		//this is to hide the set up tabs on this fragment that are present on main activity
		ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

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
	public void onDestroyView() {
		super.onDestroyView();
		
		String composeData = etComposeStory.getText()  + " : url :"  + ivInsertedImageComposeStory.getContentDescription();		
		listener.onFinishComposeDialog(composeData, false);	
	}
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

		View composeStoryView = inflater.inflate(R.layout.fragment_compose_story, container, false);

		ivInsertedImageComposeStory = (ImageView) composeStoryView.findViewById(R.id.ivInsertedImageComposeStory);
		ivInsertedImageComposeStory.setVisibility(View.INVISIBLE);
		ivInsertedImageComposeStory.setContentDescription("some url");
		
		etComposeStory = (EditText) composeStoryView.findViewById(R.id.etComposeStory);
		
		ivComposePopUpItemImage = (ImageView) composeStoryView.findViewById(R.id.ivComposePopUpItemImage);

		ivComposePopUpItemImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int location[] = {0,0};
				v.getLocationOnScreen(location);
				
				inflatePopUpWindow(inflater, container, location);
			}
		});

		return composeStoryView;
	}
	
	private void inflatePopUpWindow(LayoutInflater inflater, ViewGroup container, int location[]) {
		// Inflate the popup_layout.xml
		if(popupWindow!=null && popupWindow.isShowing()) {
			popupWindow.dismiss();
		} else {
			View popUpView = inflater.inflate(R.layout.pop_up_layout, container, false);
		
			// Creating the PopupWindow
			popupWindow = new PopupWindow(getActivity());
			popupWindow.setContentView(popUpView);
			popupWindow.setWidth(360);
			popupWindow.setHeight(220);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setFocusable(true);									

			/*// Clear the default translucent background
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.showAsDropDown(getCurrentFocus());*/


			//v.getLocationOnScreen(location);

			// Displaying the popup at the specified location, + offsets.
			popupWindow.showAtLocation(popUpView, Gravity.NO_GRAVITY, location[0]-300, location[1]-220);

			populatePopUpWindowItems();

			GridView gridview = (GridView) popUpView.findViewById(R.id.gvPopUp);		
			gridview.setAdapter(new PopUpImageAdapter(getActivity(), popUpWindowItemList));

			gridview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
					ivComposePopUpItemImage.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	private void populatePopUpWindowItems() {
		popUpWindowItemList.clear();
		popUpWindowItemList.add(new PopUpWindowItem("Gallery", R.drawable.gallery1));
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
		
		//this is to hide the menu item of main activity on this fragment
		menu.findItem(R.id.miActionBarComposeIcon).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.miActionBarComposeIcon:
			// Do Activity menu item stuff here
			return false;
		/*case R.id.miPopUpIcon:
			// Not implemented here
			return false;*/
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
		
		String composeData = etComposeStory.getText()  + " : url :"  + ivInsertedImageComposeStory.getContentDescription();		
		listener.onFinishComposeDialog(composeData, true);
		
	}

}
