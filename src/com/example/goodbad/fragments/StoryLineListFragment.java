package com.example.goodbad.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.goodbad.DrawingActivity;
import com.example.goodbad.PopUpImageAdapter;
import com.example.goodbad.PopUpWindowItem;
import com.example.goodbad.R;
import com.example.goodbad.StoryLineArrayAdapter;
import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeAPI;

public class StoryLineListFragment extends BaseListFragment {

	private ArrayList<TreeNode> storyLineNodeList = new ArrayList<TreeNode> ();
	private ListView lvNodes;
	private StoryLineArrayAdapter aaNodes;
	private TreeNode selectedStory;
	private TreeNode root;
	private PopupWindow popupWindow = null;
	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();

	public final static int RESULT_FROM_GALLERY_CODE = 1046;
	public final static int RESULT_FROM_DRAWING_CODE = 1047;
	public final static int RESULT_FROM_COMPOSE_DIALOG_CODE = 1048;
	public final static int RESULT_FROM_CAPTURE_IMAGE_ACTIVITY_CODE = 1034;

	public final String APP_TAG = "MyCustomApp";
	public String photoFileName = "photo.jpg";

	public void newInstance(TreeNode selectedStory, TreeNode root) {
		this.selectedStory = selectedStory;
		this.root = root;
	}

	/*private StoryLineListFragmentListener listener;

	public interface StoryLineListFragmentListener {
		void onSelectedTrendingItem();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof StoryLineListFragmentListener) {
			listener = (StoryLineListFragmentListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + "must implement ComposeTweetFragmentListener");
		}
	}

	@Override
	public void onDetach() {	
		super.onDetach();
		listener = null;
	}*/

	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);



		//populateTreeNodes("");
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View storyLineView = inflater.inflate(R.layout.fragment_story_line, container, false);
		final ImageView ivStoryLinePost = (ImageView) storyLineView.findViewById(R.id.ivStoryLinePost);

		ivStoryLinePost.setVisibility(View.INVISIBLE);

		//addNodestoList();
		aaNodes = new StoryLineArrayAdapter(getActivity(), storyLineNodeList, 1, getActivity().getSupportFragmentManager());

		TreeNodeAPI api = new TreeNodeAPI ();

		ArrayList<TreeNode> path = api.getPathContaining(selectedStory);
		//Log.d ("DEBUG", "num of nodes in path = " + path.size());
		addParas (path);

		/*ivInsertedStoryLineDialog = (ImageView) storyLineView.findViewById(R.id.ivInsertedStoryLineDialog);
		ivInsertedStoryLineDialog.setVisibility(View.GONE);*/

		lvNodes = (ListView) storyLineView.findViewById(R.id.lvStoryLineFragment);
		lvNodes.setAdapter(aaNodes);

		/*
		 * set up the pop up item image 
		 */
		final ImageView ivStoryLinePopUpImage = (ImageView) storyLineView.findViewById(R.id.ivStoryLinePopUpImage);

		ivStoryLinePopUpImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int location[] = {0,0};
				v.getLocationOnScreen(location);

				inflatePopUpWindow(inflater, container, location);
			}
		});

		final EditText etStoryLineCompose = (EditText) storyLineView.findViewById(R.id.etStoryLineCompose);

		etStoryLineCompose.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
				int currentCharCount = etStoryLineCompose.getText().toString().length();
				if(currentCharCount > 0) {
					ivStoryLinePopUpImage.setVisibility(View.INVISIBLE);
					ivStoryLinePost.setVisibility(View.VISIBLE);
				} else {
					ivStoryLinePopUpImage.setVisibility(View.VISIBLE);
					ivStoryLinePost.setVisibility(View.INVISIBLE);
				}			
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		ivStoryLinePost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String data = etStoryLineCompose.getText().toString();

				etStoryLineCompose.setText("");

				ivStoryLinePopUpImage.setVisibility(View.VISIBLE);
				ivStoryLinePost.setVisibility(View.INVISIBLE);		

				/*
				 * add to parse
				 */
				TreeNodeAPI api = new TreeNodeAPI();
				TreeNode childNode = api.addChild(selectedStory, data);
				childNode.saveInBackground();
				selectedStory.saveInBackground();
				selectedStory = childNode;

				//update root's num leaf nodes
				int numLeafNodes = root.getNumTreeLeafNodes();
				numLeafNodes++;
				root.setNumTreeLeafNodes(numLeafNodes);
				root.saveInBackground();

				storyLineNodeList.add(childNode);
				aaNodes.notifyDataSetChanged();

				//scroll to the bottom of list to show the added item
				lvNodes.setSelection(aaNodes.getCount()-1);
			}
		});

		return storyLineView;		
	}

	//private RelativeLayout.LayoutParams paramsNotFullscreen; //if you're using RelativeLatout           
	/*@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			 paramsNotFullscreen=(RelativeLayout.LayoutParams)mVideoView.getLayoutParams();
		        RelativeLayout.LayoutParams params=new LayoutParams(paramsNotFullscreen);
		        params.setMargins(0, 0, 0, 0);
		        params.height=ViewGroup.LayoutParams.MATCH_PARENT;
		        params.width=ViewGroup.LayoutParams.MATCH_PARENT;
		        params.addRule(RelativeLayout.CENTER_IN_PARENT);
		        mVideoView.setLayoutParams(params);

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
		        mVideoView.setLayoutParams(paramsNotFullscreen);
		}
			//newConfig.
		}	
	}*/

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

					//composeStoryFragment.setTargetFragment(fragment, requestCode);
					if(position == 0) {
						onGalleryIconClick(v);
					} else if (position == 1) {
						onCameraIconClick(v);
					} else if(position == 2) {
						onDrawingIconClick(v);	
					}
					popupWindow.dismiss();
				}
			});
		}
	}

	private void populatePopUpWindowItems() {
		popUpWindowItemList.clear();
		popUpWindowItemList.add(new PopUpWindowItem("Gallery", R.drawable.gallery1));
		popUpWindowItemList.add(new PopUpWindowItem("Photo", R.drawable.camera));
		popUpWindowItemList.add(new PopUpWindowItem("Draw", R.drawable.colors));
		popUpWindowItemList.add(new PopUpWindowItem("Audio", R.drawable.voice));
		popUpWindowItemList.add(new PopUpWindowItem("TBD", R.drawable.voice));
		popUpWindowItemList.add(new PopUpWindowItem("TBD", R.drawable.voice));
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
		intent.putExtra("title", root.getTitle());
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

		InlineComposeDialogFragment inlineComposeStoryFragment = null;

		if (requestCode==RESULT_FROM_GALLERY_CODE && data != null) {
			Uri photoUri = data.getData();
			// Do something with the photo based on Uri
			FragmentManager fm = getActivity().getSupportFragmentManager();
			inlineComposeStoryFragment = InlineComposeDialogFragment.newInstance(photoUri, getActivity().getContentResolver());

			//this is how the two fragments would communicate
			inlineComposeStoryFragment.setTargetFragment(this, RESULT_FROM_COMPOSE_DIALOG_CODE);
			inlineComposeStoryFragment.show(fm, "dialog_fragment");
		}

		if(requestCode==RESULT_FROM_DRAWING_CODE && data!=null) {
			String drawingImageUrl = data.getStringExtra("drawingImageUrl");
			Uri imageUri = Uri.parse("file://"+drawingImageUrl);

			// Do something with the photo based on Uri
			FragmentManager fm = getActivity().getSupportFragmentManager();
			inlineComposeStoryFragment = InlineComposeDialogFragment.newInstance(imageUri, getActivity().getContentResolver());

			//this is how the two fragments would communicate
			inlineComposeStoryFragment.setTargetFragment(this, RESULT_FROM_COMPOSE_DIALOG_CODE);
			inlineComposeStoryFragment.show(fm, "dialog_fragment");
		}
		
		if(requestCode==RESULT_FROM_CAPTURE_IMAGE_ACTIVITY_CODE && resultCode==Activity.RESULT_OK) {
			Uri takenPhotoUri = getPhotoFileUri(photoFileName);
			FragmentManager fm = getActivity().getSupportFragmentManager();
			inlineComposeStoryFragment = InlineComposeDialogFragment.newInstance(takenPhotoUri, getActivity().getContentResolver());

			//this is how the two fragments would communicate
			inlineComposeStoryFragment.setTargetFragment(this, RESULT_FROM_COMPOSE_DIALOG_CODE);
			inlineComposeStoryFragment.show(fm, "dialog_fragment");

			/* // by this point we have the camera photo on disk
	         Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
	         // Load the taken image into a preview
	         ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
	         ivPreview.setImageBitmap(takenImage);   */
		} 

		if(requestCode==RESULT_FROM_COMPOSE_DIALOG_CODE && resultCode==Activity.RESULT_OK && data!=null) {

			TreeNodeAPI api = new TreeNodeAPI();
			String inlineTextData = data.getStringExtra("inlineTextData");
			String inlineImageUrl = data.getStringExtra("inlineImageUrl");

			TreeNode childNode = api.addChild(selectedStory, inlineTextData);
			if (inlineImageUrl != null && !inlineImageUrl.isEmpty()){
				childNode.setImageUrl(inlineImageUrl);
			}

			childNode.saveInBackground();
			selectedStory.setLeafNode(false);
			selectedStory.saveInBackground();
			selectedStory = childNode;

			//update root's num leaf nodes
			int numLeafNodes = root.getNumTreeLeafNodes(); 
			numLeafNodes++;
			root.setNumTreeLeafNodes(numLeafNodes); 
			root.saveInBackground();

			storyLineNodeList.add(childNode);
			aaNodes.notifyDataSetChanged();

			//scroll to the bottom of list to show the added item
			lvNodes.setSelection(aaNodes.getCount()-1);
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


	public void addParas(List<TreeNode> nodes)
	{
		storyLineNodeList.clear();
		for (TreeNode n: nodes)
		{
			storyLineNodeList.add(n);
			//			Log.d ("DEBUG", "addParas called with text = " + n.getText ());
			aaNodes.notifyDataSetChanged();
		}
	}


	@Override
	public void populateTreeNodes(String max_id) {
		// TODO Auto-generated method stub

	}

	/*@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.action_bar_items, menu);

		MenuItem  playMenu = menu.findItem(R.id.miActionBarComposeIcon);
		if( ParseUser.getCurrentUser() != null ) {
			playMenu.setIcon(R.drawable.ic_read  ) ;
		}	else {playMenu.setIcon(R.drawable.ic_read  ) ;

		}   
		return;

	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.miActionBarComposeIcon) {
			//onReadClick(item);
			return true;
		}
		return false; 
	}*/


	//		inflater.inflate(R.menu.story_line_action_bar, menu);
	//
	//		menu.findItem(R.id.miActionBarComposeIcon).setVisible(false);
	//	}


	private void addNodestoList() {
		TreeNode n1 = new TreeNode ();
		n1.setText("This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1 This is para 1");
		storyLineNodeList.add(n1);

		TreeNode n2 = new TreeNode ();
		n2.setText("This is para 2");
		storyLineNodeList.add(n2);

		TreeNode n3 = new TreeNode ();
		n3.setText("This is para 3");
		storyLineNodeList.add(n3);

		TreeNode n4 = new TreeNode ();
		n4.setText("This is para 4");
		storyLineNodeList.add(n4);

		TreeNode n5 = new TreeNode ();
		n5.setText("This is para 5");
		storyLineNodeList.add(n5);

		TreeNode n6 = new TreeNode ();
		n6.setText("This is para 6");
		storyLineNodeList.add(n6);

		TreeNode n7 = new TreeNode ();
		n7.setText("This is para 7");
		storyLineNodeList.add(n7);

		TreeNode n8 = new TreeNode ();
		n8.setText("This is para 8");
		storyLineNodeList.add(n8);

		TreeNode n9 = new TreeNode ();
		n9.setText("This is para 9");
		storyLineNodeList.add(n9);

		TreeNode n10 = new TreeNode ();
		n10.setText("This is para 10");
		storyLineNodeList.add(n10);
	}



}
