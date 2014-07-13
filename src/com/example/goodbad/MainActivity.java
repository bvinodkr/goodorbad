package com.example.goodbad;

import java.util.List;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.goodbad.fragments.HomeListFragment;
import com.example.goodbad.fragments.MyListFragment;
import com.example.goodbad.listeners.FragmentTabListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

//		addDummyData ();
		setupTabs ();
	}

	private void addStory (String x)
	{
		TreeNode root = new TreeNode (x  + " , good-root", null);


		//		Log.d("DEBUG", root.getObjectId());
		try {
			root.save ();
			root.setStoryId(root.getObjectId());

			TreeNodeAPI api = new TreeNodeAPI ();
			TreeNode firstPara = api.addChild(root, x + " bad-para1");
			//save both root and firstPara
			root.save();
			firstPara.save();
			
			TreeNode secondPara = api.addChild(firstPara, x + " good-para2");
			firstPara.save();
			secondPara.save();
			
			TreeNode secondParav1 = api.addChild(firstPara, x + " good-para2-v1");
			secondParav1.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}
	
	private void addDummyData ()
	{
/*		addStory ("story1");
		addStory ("story2");
		addStory("story3");
		*/
/*
		ParseQuery<TreeNode> query = ParseQuery.getQuery(TreeNode.class);
		query.whereEqualTo("parent", JSONObject.NULL);
		query.findInBackground( new FindCallback<TreeNode>() {

			public void done(List<TreeNode> items, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null)
				{

					TreeNode item = items.get(0);
					Toast.makeText(MainActivity.this, items.size() + "", Toast.LENGTH_SHORT).show ();
					TreeNode firstPara = new TreeNode ("This is second line, bad", item);
					firstPara.setStoryId(item);
					firstPara.saveInBackground();

				}
				else
				{
					Toast.makeText(MainActivity.this, "error:" + arg1.getMessage(), Toast.LENGTH_SHORT).show ();
				}
			}

			
		});
*/
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Good/Bad");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
				.newTab()
				.setText("Global")
				.setIcon(R.drawable.ic_global)
				.setTag("GlobalFragment")
				.setTabListener(new FragmentTabListener<HomeListFragment>(R.id.flContainer, this,
						"home", HomeListFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Favorite")
				.setIcon(R.drawable.ic_favorite)
				.setTag("FavoriteFragment")
				.setTabListener(new FragmentTabListener<MyListFragment>(R.id.flContainer, this,
						"mylist", MyListFragment.class));
		actionBar.addTab(tab2);
	}
}
