package com.example.goodbad;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.goodbad.fragments.InlineComposeDialogFragment;
import com.loopj.android.image.SmartImageView;

public class StoryLineArrayAdapter extends ArrayAdapter<TreeNode> {
	
	private int mScreenNo;
	private FragmentManager mFragmentManager;

	public StoryLineArrayAdapter(Context context, List<TreeNode> treeNodes, int screenNo, FragmentManager fragmentManager) {
		super(context, R.layout.treenode_item, treeNodes);
		this.mScreenNo = screenNo;
		this.mFragmentManager = fragmentManager;
	}
	
	public void addStory (List<TreeNode> nodes)
	{
		this.clear();
		this.addAll(nodes);
	}
	
	public String getRelativeTimeAgo(Date date) {
		String relativeDate = "";
		/*try {
			long dateMillis = date.getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		} */
	 
		return relativeDate;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TreeNode node = getItem (position);
		if (convertView == null)
		{
			LayoutInflater inflator = LayoutInflater.from(getContext());
			convertView = inflator.inflate(R.layout.treenode_item, parent, false);
		} 
	
		/*RelativeLayout rlTreeNodeItem = (RelativeLayout) convertView.findViewById(R.id.rlTreeNodeItem);	
		RelativeLayout rlTreeNodeItemBody = (RelativeLayout) convertView.findViewById(R.id.rlBody);	
		if(position==1) {

			rlTreeNodeItem.getLayoutParams().height = 250;
			rlTreeNodeItemBody.getLayoutParams().height = 185;
			
			RelativeLayout.LayoutParams lpImageView = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, 190);
			
			ImageView imageView = new ImageView(getContext());
			imageView.setImageResource(R.drawable.sample_0);
			imageView.setId(2);
			imageView.setPadding(0, 2, 0, 5);
			lpImageView.addRule(RelativeLayout.BELOW, convertView.findViewById(R.id.tvBody).getId());
			imageView.setLayoutParams(lpImageView);
			rlTreeNodeItemBody.addView(imageView);
		}*/
		
		if(mScreenNo == 0) {
			convertView.setBackgroundResource(R.drawable.border_ui);
			convertView.findViewById(R.id.llFollowers).setBackgroundColor(Color.LTGRAY);
		} else if(mScreenNo == 1) {
			convertView.setBackgroundResource(R.drawable.round_edges);
			//convertView.findViewById(R.id.llFollowers).setBackgroundResource(R.drawable.footer);
		}
		
		final SmartImageView ivItemImage = (SmartImageView) convertView.findViewById(R.id.ivItemImage);
		final VideoView vvItemVideo = (VideoView) convertView.findViewById(R.id.vvItemVideo);
		vvItemVideo.setVisibility(View.GONE);
		ivItemImage.setVisibility(View.GONE);
		if (node.getImageUrl() != null)
		{
			//Log.d ("DEBUG", "image url = " + node.getImageUrl());
			ivItemImage.setVisibility(View.VISIBLE);
			ivItemImage.setImageUrl(node.getImageUrl());
		}
		/*if(position==1) { 			
			vvItemVideo.setVisibility(View.GONE);
			ivItemImage.setVisibility(View.VISIBLE);
			ivItemImage.setImageResource(R.drawable.yosemite);
		} else if (position==3) { 			
			vvItemVideo.setVisibility(View.GONE);
			ivItemImage.setVisibility(View.VISIBLE);
			ivItemImage.setImageResource(R.drawable.football);			
		} else */ 
		if (position == 0) {
			ivItemImage.setVisibility(View.GONE);
			vvItemVideo.setVisibility(View.VISIBLE);
			vvItemVideo.setVideoPath("http://ia600204.us.archive.org/2/items/Pbtestfilemp4videotestmp4/video_test.mp4");
			
			//Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail("http://ia600204.us.archive.org/2/items/Pbtestfilemp4videotestmp4/video_test.mp4", Thumbnails.MINI_KIND);
	        //ivItemImage.setImageBitmap(bmThumbnail);
	        //ivItemImage.setVisibility(View.VISIBLE);
			
			MediaController mediaController = new MediaController(getContext());
			mediaController.setAnchorView(vvItemVideo);
			vvItemVideo.setMediaController(mediaController);
			vvItemVideo.requestFocus();
			vvItemVideo.start();
			
		}  else if (position == 4) { 
			ivItemImage.setVisibility(View.GONE);
			vvItemVideo.setVisibility(View.VISIBLE);
			vvItemVideo.setVideoPath("http://techslides.com/demos/sample-videos/small.mp4");
			MediaController mediaController = new MediaController(getContext());
			mediaController.setAnchorView(vvItemVideo);
			vvItemVideo.setMediaController(mediaController);
			vvItemVideo.requestFocus();
			/*vvItemVideo.setOnPreparedListener(new OnPreparedListener() {
			    // Close the progress bar and play the video
			    public void onPrepared(MediaPlayer mp) {*/
			    	vvItemVideo.start();
			   /* }
			});*/			    
		} else {
			ivItemImage.setVisibility(View.GONE);
			vvItemVideo.setVisibility(View.GONE);
		}
				
		/*ivItemImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ivItemImage.setVisibility(View.GONE);
				vvItemVideo.setVisibility(View.VISIBLE);
				
				vvItemVideo.setVideoPath("http://ia600204.us.archive.org/2/items/Pbtestfilemp4videotestmp4/video_test.mp4");
				MediaController mediaController = new MediaController(getContext());
				mediaController.setAnchorView(vvItemVideo);
				vvItemVideo.setMediaController(mediaController);
				vvItemVideo.requestFocus();
				vvItemVideo.start();
			}
		});*/
		
		 
		//ImageView ivProfileImage = (ImageView)v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		TextView tvVersions = (TextView)convertView.findViewById(R.id.tvVersions);
		TextView tvRelativeTime = (TextView)convertView.findViewById(R.id.tvRelativeTime);
		String relativeTime = getRelativeTimeAgo (node.getUpdatedAt());
		tvRelativeTime.setText(relativeTime);
		
		final TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
		final ImageView ivEmptyHeart = (ImageView) convertView.findViewById(R.id.ivEmptyHeart);
		ivEmptyHeart.setContentDescription("false");
		ivEmptyHeart.setTag(node);

		ivEmptyHeart.setOnClickListener(new OnClickListener() {
			
			@Override 
			public void onClick(View v) {
				int likes = 0;
				if (!tvLikes.getText().toString().isEmpty())
				{
				  likes = Integer.parseInt(tvLikes.getText().toString());
				}
				boolean isLiked = Boolean.parseBoolean(ivEmptyHeart.getContentDescription().toString());
				if(isLiked) {
					if(likes > 0) { 
						likes--;
						ivEmptyHeart.setContentDescription("false");
						ivEmptyHeart.setImageResource(R.drawable.ic_action_empty_heart);
						tvLikes.setText(likes+"");
					}
				} else {
					likes++;
					ivEmptyHeart.setContentDescription("true");				
					ivEmptyHeart.setImageResource(R.drawable.ic_action_filled_heart);
					tvLikes.setText(likes+"");
				}
				TreeNode tree = (TreeNode)v.getTag();
				tree.setLikes(likes);
				tree.saveInBackground();
			}
		}); 
		
		//tvUserName.setText (node.getUser().getUsername());		 
		//ivProfileImage.setImageResource(android.R.color.transparent);
		tvBody.setText (node.getText());
		
		ImageView ivForkStory = (ImageView) convertView.findViewById(R.id.ivForkStory);
		ivForkStory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InlineComposeDialogFragment inlineComposeStoryFragment = InlineComposeDialogFragment.newInstance("InLineCompose");
				inlineComposeStoryFragment.show(mFragmentManager, "dialog_fragment");				
			}
		});
		
		
//		Log.d("debug", node.getText()); 
		/*if (position%2 == 0) {
			convertView.setBackgroundColor(0xFF99CCFF);
		} */
		
		TreeNodeAPI api = new TreeNodeAPI ();
		int sibCount = api.getSiblingCount(node);
		if ( sibCount > 0)
		{
			String plural = (sibCount > 1)? "s":"";
			tvVersions.setText(sibCount + " version" + plural);
		}
		/*else
		{
			tvVersions.setVisibility(View.GONE);
		}*/
		
		Log.d ("debug", "in screen 1, adding swipe listeners");
		convertView.setOnTouchListener(new OnSwipeTouchListener(getContext(), node) {
			  
			  
			  @Override
			  public void onSwipeLeft() {
//			    Toast.makeText(getContext(), "Left", Toast.LENGTH_SHORT).show();
			    Log.d ("debug", "swipe left invoked");
			    TreeNode n = getTreeNode();
			    TreeNodeAPI api = new TreeNodeAPI ();
			    TreeNode sibling = api.getSibling(n, 1);
			    if (sibling != null)
			    {
			    	List<TreeNode> path = api.getPathContaining(sibling);
			    	addStory (path);
			    }
			  }
			  
			  
			  
			  @Override
			  public void onSwipeRight() {
			    Toast.makeText(getContext(), "Right", Toast.LENGTH_SHORT).show();
			    TreeNode n = getTreeNode();
			    Log.d ("debug", "swipe right invoked " + n.getObjectId());
			    TreeNodeAPI api = new TreeNodeAPI ();
			    TreeNode sibling = api.getSibling(n, -1);
			    if (sibling != null)
			    {
			    	List<TreeNode> path = api.getPathContaining(sibling);
			    	addStory (path);
			    }
			    else
			    {
			    	Log.d ("debug", "sibling is null");
			    }
			  }
			  
			});
		
		return convertView;

	}

	
}
