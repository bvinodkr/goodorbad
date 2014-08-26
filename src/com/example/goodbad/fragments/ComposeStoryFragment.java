package com.example.goodbad.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goodbad.DrawingActivity;
import com.example.goodbad.PopUpImageAdapter;
import com.example.goodbad.PopUpWindowItem;
import com.example.goodbad.R;
import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeAPI;
import com.parse.ParseUser;

public class ComposeStoryFragment extends Fragment {

	private ComposeStoryFragmentListener listener;
	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	private ImageView ivComposePopUpItemImage;	
	private PopupWindow popupWindow = null;
	private ImageView ivInsertedImageComposeStory;
	private EditText etStoryTitleCompose;
	private EditText etComposeStory;
	private Button btnPost;
	private boolean fromPost = false;
	private TextView tvUserName;
	private static TreeNode mParentNode;
	private static boolean mIsComingFromFork;
	
	public final static int RESULT_FROM_GALLERY_CODE = 1046;
	public final static int RESULT_FROM_DRAWING_CODE = 1047;
	public final static int RESULT_FROM_COMPOSE_DIALOG_CODE = 1048;
	public final static int RESULT_FROM_CAPTURE_IMAGE_ACTIVITY_CODE = 1034;

	public final String APP_TAG = "MyCustomApp";
	public String photoFileName = "photo.jpg";

	public interface ComposeStoryFragmentListener {
		void onFinishComposeDialog(String composeData, String composeStoryTitle, String imageUrl, boolean fromPost, TreeNode parentNode);
	}

	public ComposeStoryFragment() {
		// Empty constructor required for DialogFragment
	}

	public static ComposeStoryFragment newInstance(String title, TreeNode node, boolean isComingFromFork) {
		ComposeStoryFragment frag = new ComposeStoryFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);

		mParentNode = node;
		mIsComingFromFork = isComingFromFork;
		
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
		if(!fromPost) {		
			listener.onFinishComposeDialog("NA", "NA", "NA", fromPost, null);
		}
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


		View composeStoryView = inflater.inflate(R.layout.fragment_compose_story, container, false);

		fromPost = false;

		ivInsertedImageComposeStory = (ImageView) composeStoryView.findViewById(R.id.ivInsertedImageComposeStory);
		ivInsertedImageComposeStory.setVisibility(View.INVISIBLE);

		etStoryTitleCompose = (EditText) composeStoryView.findViewById(R.id.etStoryTitleCompose);
		if(mIsComingFromFork) {
			etStoryTitleCompose.setVisibility(View.INVISIBLE);
		}
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
		imm.showSoftInput(etStoryTitleCompose, 0);
		etStoryTitleCompose.requestFocus();

		btnPost = (Button) composeStoryView.findViewById(R.id.btnPost);
		if(!mIsComingFromFork) {
			btnPost.setVisibility(View.GONE);
		}
		
		etComposeStory = (EditText) composeStoryView.findViewById(R.id.etComposeStory);
		tvUserName = (TextView) composeStoryView.findViewById(R.id.tvComposeUserName);
		tvUserName.setText(ParseUser.getCurrentUser().getString("name"));
		ivComposePopUpItemImage = (ImageView) composeStoryView.findViewById(R.id.ivComposePopUpItemImage);

		ivComposePopUpItemImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int location[] = {0,0};
				v.getLocationOnScreen(location);

				inflatePopUpWindow(inflater, container, location);
			}
		});


		Button btnPost = (Button) composeStoryView.findViewById(R.id.btnPost);
		btnPost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onPostStoryIconClick(null);			
				getFragmentManager().popBackStackImmediate();
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
			popupWindow.setWidth(700);
			popupWindow.setHeight(430);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setFocusable(true);									

			/*// Clear the default translucent background
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.showAsDropDown(getCurrentFocus());*/


			//v.getLocationOnScreen(location);

			// Displaying the popup at the specified location, + offsets.
			popupWindow.showAtLocation(popUpView, Gravity.NO_GRAVITY, location[0]-300, location[1]-450);

			populatePopUpWindowItems();

			GridView gridview = (GridView) popUpView.findViewById(R.id.gvPopUp);		
			gridview.setAdapter(new PopUpImageAdapter(getActivity(), popUpWindowItemList));

			gridview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
					if(position == 0) {
						onGalleryIconClick(v);
					} else if (position == 1) {
						onCameraIconClick(v);
					} else if(position == 2) {
						onDrawingIconClick(v);	
					}
					ivInsertedImageComposeStory.setVisibility(View.VISIBLE);
					popupWindow.dismiss();
				}
			});			
		}
	}

	private void populatePopUpWindowItems() {
		popUpWindowItemList.clear();
		popUpWindowItemList.add(new PopUpWindowItem("Gallery", R.drawable.gallery4));
		popUpWindowItemList.add(new PopUpWindowItem("Photo", R.drawable.camera1));
		popUpWindowItemList.add(new PopUpWindowItem("Video", R.drawable.video1));
		popUpWindowItemList.add(new PopUpWindowItem("Audio", R.drawable.voice2));
		popUpWindowItemList.add(new PopUpWindowItem("TBD", R.drawable.voice2));
		popUpWindowItemList.add(new PopUpWindowItem("TBD", R.drawable.voice2));
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {		
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.compose_action_bar, menu);

		//this is to hide the menu item of main activity on this fragment
		if(menu.findItem(R.id.miActionBarComposeIcon)!=null) {
			menu.findItem(R.id.miActionBarComposeIcon).setVisible(false);
		}
		if(menu.findItem(R.id.miPostStoryIcon)!=null){
			menu.findItem(R.id.miPostStoryIcon).setVisible(true);
		}

		MenuItem  playMenu = menu.findItem(R.id.miPostStoryIcon);

		playMenu.setTitle(Html.fromHtml("<font color='#FFFFFF'>Post</font>"));

	} 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.miActionBarComposeIcon) {
			// Do Activity menu item stuff here
			return false;
		} else if (itemId == R.id.miPostStoryIcon) {
			onPostStoryIconClick(item);
			return true;
		} else {
		}

		return super.onOptionsItemSelected(item);
	}

	public void onPostStoryIconClick(MenuItem mi) {
		Toast.makeText(getActivity(), "Post Story", Toast.LENGTH_SHORT).show();			

		if(etComposeStory.getText()!=null ||
				etStoryTitleCompose.getText()!=null ||
				ivInsertedImageComposeStory.getContentDescription()!=null) {
			fromPost = true;

			String composeData;
			String composeStoryTitle;
			String imageUrl;

			if(etComposeStory.getText()!=null) {
				composeData = etComposeStory.getText().toString();
			} else {
				composeData = "";
			}
			if(etStoryTitleCompose.getText()!=null) {
				composeStoryTitle = etStoryTitleCompose.getText().toString();
			} else {
				composeStoryTitle = "";
			}
			if(ivInsertedImageComposeStory.getContentDescription()!=null) {
				imageUrl = ivInsertedImageComposeStory.getContentDescription().toString();
			} else {
				imageUrl = "";
			}

			listener.onFinishComposeDialog(composeData, composeStoryTitle, imageUrl, fromPost, mParentNode);
		}
	}
	
	public void onPostStoryIconClickToo(View v) {
		Toast.makeText(getActivity(), "Post Story Too", Toast.LENGTH_SHORT).show();			

		if(etComposeStory.getText()!=null ||
				etStoryTitleCompose.getText()!=null ||
				ivInsertedImageComposeStory.getContentDescription()!=null) {
			fromPost = true;

			String composeData;
			String composeStoryTitle;
			String imageUrl;

			if(etComposeStory.getText()!=null) {
				composeData = etComposeStory.getText().toString();
			} else {
				composeData = "";
			}
			if(etStoryTitleCompose.getText()!=null) {
				composeStoryTitle = etStoryTitleCompose.getText().toString();
			} else {
				composeStoryTitle = "";
			}
			if(ivInsertedImageComposeStory.getContentDescription()!=null) {
				imageUrl = ivInsertedImageComposeStory.getContentDescription().toString();
			} else {
				imageUrl = "";
			}

			listener.onFinishComposeDialog(composeData, composeStoryTitle, imageUrl, fromPost, mParentNode);
		}
	}

	public void onGalleryIconClick(View view) {
		// Create intent for picking a photo from the gallery
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// Bring up gallery to select a photo
		startActivityForResult(intent, RESULT_FROM_GALLERY_CODE);
	}

	public void onDrawingIconClick(View v) {
		Intent intent = new Intent(getActivity(), DrawingActivity.class);
		intent.putExtra("title", etStoryTitleCompose!=null?etStoryTitleCompose.getText().toString():"New Story");
		startActivityForResult(intent, RESULT_FROM_DRAWING_CODE);
	}

	public void onCameraIconClick(View v) {
		// create Intent to take a picture and return control to the calling application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
		// Start the image capture intent to take photo
		startActivityForResult(intent, RESULT_FROM_CAPTURE_IMAGE_ACTIVITY_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		try { 
			Bitmap selectedImage;
			Uri photoUri = null;
			if (requestCode==RESULT_FROM_GALLERY_CODE && data != null) {
				photoUri = data.getData();
			}
			if(requestCode==RESULT_FROM_DRAWING_CODE && data!=null) {
				String drawingImageUrl = data.getStringExtra("drawingImageUrl");
				photoUri = Uri.parse("file://"+drawingImageUrl);
				 
			}
			if(requestCode==RESULT_FROM_CAPTURE_IMAGE_ACTIVITY_CODE && resultCode==Activity.RESULT_OK) {
				photoUri = getPhotoFileUri(photoFileName);
			} 
			
			selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
	
			// Load the selected image into a preview	        
			ivInsertedImageComposeStory.setImageBitmap(selectedImage); 
			ivInsertedImageComposeStory.setContentDescription(photoUri.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Returns the Uri for a photo stored on disk given the fileName
	public Uri getPhotoFileUri(String fileName) {
		// Get safe storage directory for photos
		File mediaStorageDir = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
			Log.d(APP_TAG, "failed to create directory");
		}

		// Return the file target for the photo based on filename
		return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
	}

}
