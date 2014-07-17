package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.goodbad.PopUpImageAdapter;
import com.example.goodbad.PopUpWindowItem;
import com.example.goodbad.R;
import com.example.goodbad.StoryLineArrayAdapter;
import com.example.goodbad.TreeNode;
import com.example.goodbad.TreeNodeAPI;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class StoryLineListFragment extends BaseListFragment {

	private ArrayList<TreeNode> storyLineNodeList = new ArrayList<TreeNode> ();
	private ListView lvNodes;
	private StoryLineArrayAdapter aaNodes;
	private TreeNode selectedStory;
	private PopupWindow popupWindow = null;
	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	
	public void newInstance(TreeNode selectedStory) {
		this.selectedStory = selectedStory;
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
		
		//addNodestoList();
		aaNodes = new StoryLineArrayAdapter(getActivity(), storyLineNodeList, 1, getActivity().getSupportFragmentManager());

		//addNodestoAdapter(storyLineNodeList);
	/*	
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("storyid", selectedStory.getStoryId());
		query.include("user");
		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null)
				{
					TreeNodeAPI api = new TreeNodeAPI ();
					TreeNode root = api.toTree(items);
					Log.d ("DEBUG", "root object id" + root.getObjectId());
					List<TreeNode> leafNodes = api.getLeafNodes(items);
					Log.d ("DEBUG", "num of lead nodes " + leafNodes.size());
					addParas (api.getPathContaining(leafNodes.get(0)));
				}
			}
		});

		*/

		

		lvNodes = (ListView) storyLineView.findViewById(R.id.lvStoryLineFragment);
		lvNodes.setAdapter(aaNodes);
		
		/*
		 * set up the pop up item image 
		 */
		ImageView ivStoryLinePopUpImage = (ImageView) storyLineView.findViewById(R.id.ivStoryLinePopUpImage);

		ivStoryLinePopUpImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int location[] = {0,0};
				v.getLocationOnScreen(location);
				
				inflatePopUpWindow(inflater, container, location);
			}
		});
		
		return storyLineView;		
	}
	
	//private RelativeLayout.LayoutParams paramsNotFullscreen; //if you're using RelativeLatout           
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			 /*paramsNotFullscreen=(RelativeLayout.LayoutParams)mVideoView.getLayoutParams();
		        RelativeLayout.LayoutParams params=new LayoutParams(paramsNotFullscreen);
		        params.setMargins(0, 0, 0, 0);
		        params.height=ViewGroup.LayoutParams.MATCH_PARENT;
		        params.width=ViewGroup.LayoutParams.MATCH_PARENT;
		        params.addRule(RelativeLayout.CENTER_IN_PARENT);
		        mVideoView.setLayoutParams(params);

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
		        mVideoView.setLayoutParams(paramsNotFullscreen);
		}*/
			//newConfig.
		}	
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
					
					FragmentManager fm = getActivity().getSupportFragmentManager();
					InlineComposeDialogFragment inlineComposeStoryFragment = InlineComposeDialogFragment.newInstance("InLineCompose");
					inlineComposeStoryFragment.show(fm, "dialog_fragment");
					//composeStoryFragment.setTargetFragment(fragment, requestCode);
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
	
	public void addParas(List<TreeNode> nodes)
	{
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
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.story_line_action_bar, menu);
		
		menu.findItem(R.id.miActionBarComposeIcon).setVisible(false);
	}
	
	
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
