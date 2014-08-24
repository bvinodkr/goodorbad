package com.example.goodbad.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.goodbad.R;
import com.example.goodbad.Favorites;
import com.example.goodbad.TreeNodeAPI;
import com.example.goodbad.FavoritesArrayAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class FavStoryListFragment extends BaseListFragment {
	
	private List<Favorites> favStoryItemList = new ArrayList<Favorites>();
	//private TrendingStoryListFragmentListener listener;	
	private ListView lvNodes;
	private FavoritesArrayAdapter aaNodes;
	private Favorites mAddedFavorites;
	
	/*public interface TrendingStoryListFragmentListener {
		void onSelectedTrendingItem(TreeNode selectedTrendingItem);
	}*/
	
	public FavStoryListFragment newInstance(Favorites favorites) {
		
		mAddedFavorites = favorites;
		
		return null;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		populateTreeNodes("");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View favStoriesView = inflater.inflate(R.layout.fragment_fav_list, container, false);
		
		aaNodes = new FavoritesArrayAdapter(getActivity(), favStoryItemList, 2);
		
		lvNodes = (ListView) favStoriesView.findViewById(R.id.lvFavListFragment);
		lvNodes.setAdapter(aaNodes);

		return favStoriesView;
	}
	
	/*@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof TrendingStoryListFragmentListener) {
			listener = (TrendingStoryListFragmentListener) activity;
		} else { 
			throw new ClassCastException(activity.toString() + "must implement NewStoryListFragment");
		}
	}
 
	@Override
	public void onDetach() {	
		super.onDetach();
		listener = null;
	}*/ 
	
	
	@Override
	public void populateTreeNodes(String max_id) {
		getStories();
	}

	private void getStories() {
		ParseQuery<Favorites> query = ParseQuery.getQuery(Favorites.class);
		//query.whereGreaterThanOrEqualTo("createdAt", new Date(System.currentTimeMillis() - 36*60*60*1000l));
		query.orderByDescending("createdAt");
		query.whereEqualTo("parentid", JSONObject.NULL);
	 	query.include("FavUserId");
		query.include("FavNodeId");
		query.include("FavNodeId.storyid1");
		query.include("FavNodeId.user");

		query.findInBackground( new FindCallback<Favorites>() {

			public void done(List<Favorites> items, ParseException arg1) {			
				if (arg1 == null) {
					aaNodes.addAll((ArrayList<Favorites>)items);
					favStoryItemList = (ArrayList<Favorites>)items;
					
					for (Favorites treeRoot: items) {
						Log.d ("DEBUG", "storyid = " + treeRoot.getObjectId());
 						if (treeRoot.getUser () != null)
						{
							Log.d ("DEBUG", "user name " + treeRoot.getUser().getUsername());
						}
						else
						{
							Log.d ("DEBUG", "user name null");
						}
						break;
					}
					
				}
				else
				{
					Log.d ("DEBUG", "error in getting stories " + arg1.getMessage());
				}
			}
		});
	}
 
}
