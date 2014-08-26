package com.example.goodbad;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.loopj.android.image.SmartImageView;

public class TreeNodeArrayAdapter extends ArrayAdapter<TreeNode> {
	
	private int mScreenNo;
	private List<TreeNode> mTreeNodes;

	public TreeNodeArrayAdapter(Context context, List<TreeNode> treeNodes, int screenNo) {
		super(context, R.layout.treenode_item, treeNodes);
		this.mScreenNo = screenNo;
		this.mTreeNodes = treeNodes;
		Log.d ("DEBUG", "screen no " + mScreenNo);
	}
	
	public void addStory (List<TreeNode> nodes)
	{
		this.clear();
		this.addAll(nodes);
	}
	
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	
	public String getRelativeTimeAgo(Date date) {

	 
		String relativeDate = "";
		try {
			long dateMillis = date.getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
	 
		return relativeDate;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
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
			//convertView.setBackgroundResource(R.drawable.border_ui);
			convertView.setBackgroundResource(R.drawable.round_edges);
			//convertView.findViewById(R.id.llFollowers).setBackgroundColor(Color.LTGRAY);
		} else if(mScreenNo == 1) {
			convertView.setBackgroundResource(R.drawable.round_edges);
			//convertView.findViewById(R.id.llFollowers).setBackgroundResource(R.drawable.footer);
		}
		
		SmartImageView ivItemImage = (SmartImageView) convertView.findViewById(R.id.ivItemImage);
		final VideoView vvItemVideo = (VideoView) convertView.findViewById(R.id.vvItemVideo);
		vvItemVideo.setVisibility(View.GONE);
		ivItemImage.setVisibility(View.GONE);
		
		String imageUrl = node.getImageUrl(); {
			ivItemImage.setVisibility(View.GONE);
			vvItemVideo.setVisibility(View.GONE);
		}
		
		if (imageUrl != null && !imageUrl.isEmpty())
		{
			//Log.d ("DEBUG", "image url = " + node.getImageUrl());
			if (imageUrl.contains("mp4"))
			{
				vvItemVideo.setVisibility(View.VISIBLE);
				ivItemImage.setVisibility(View.GONE);
				vvItemVideo.setVideoPath(imageUrl);
				MediaController mediaController = new MediaController(getContext());
				mediaController.setAnchorView(vvItemVideo);
				vvItemVideo.setMediaController(mediaController);
				vvItemVideo.requestFocus();
				vvItemVideo.start();
			}
			else
			{
				ivItemImage.setVisibility(View.VISIBLE);
				vvItemVideo.setVisibility(View.GONE);
				if(node.getImageUrl().contains("http")) {
					ivItemImage.setImageUrl(node.getImageUrl());
				} else {
					String path = getPath(Uri.parse(node.getImageUrl()));
					if(path!=null) {
						ivItemImage.setImageUrl(path);
						String pathNew = "file://" + getPath(Uri.parse(node.getImageUrl()));
	
						try {
							Bitmap image = BitmapFactory.decodeStream(new URL(pathNew).openConnection().getInputStream());
							ivItemImage.setImageBitmap(image);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	
				}
			}
		}
		
		/*if(position==1) { 			
			vvItemVideo.setVisibility(View.GONE);
			ivItemImage.setVisibility(View.VISIBLE);
			ivItemImage.setImageResource(R.drawable.yosemite);
		} else if (position==3) { 			
			vvItemVideo.setVisibility(View.GONE);
			ivItemImage.setVisibility(View.VISIBLE);
			ivItemImage.setImageResource(R.drawable.football);			
		} else if (position == 3) { 
			ivItemImage.setVisibility(View.GONE);
			vvItemVideo.setVisibility(View.VISIBLE);
			vvItemVideo.setVideoPath("http://ia600204.us.archive.org/2/items/Pbtestfilemp4videotestmp4/video_test.mp4");
			MediaController mediaController = new MediaController(getContext());
			mediaController.setAnchorView(vvItemVideo);
			vvItemVideo.setMediaController(mediaController);
			vvItemVideo.requestFocus();
			vvItemVideo.setOnPreparedListener(new OnPreparedListener() {
			    // Close the progress bar and play the video
			    public void onPrepared(MediaPlayer mp) {
			    	vvItemVideo.start();
			    }
			});
		} else {
			ivItemImage.setVisibility(View.GONE);
			vvItemVideo.setVisibility(View.GONE);
		}
		*/
		
		 
		//ImageView ivProfileImage = (ImageView)v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		TextView tvVersions = (TextView) convertView.findViewById(R.id.tvVersions);
		TextView tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);
		TextView tvStoryTitle = (TextView) convertView.findViewById(R.id.tvStoryTitle);
		final TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);		
		final ImageView ivEmptyHeart = (ImageView) convertView.findViewById(R.id.ivEmptyHeart);
		ivEmptyHeart.setContentDescription("false");
		
		String relativeTime = getRelativeTimeAgo (node.getUpdatedAt());
		tvRelativeTime.setText(relativeTime);
		
		ivEmptyHeart.setTag(node);
		convertView.setTag(node);
		ivEmptyHeart.setOnClickListener(new OnClickListener() {
			
			@Override 
			public void onClick(final View v) {
				int likes = 0;
				if (!tvLikes.getText().toString().isEmpty())
				{
				  likes = Integer.parseInt(tvLikes.getText().toString());
				}
				 
				//this is for animation
				ObjectAnimator animator1 = ObjectAnimator.ofFloat(v, "translationY", -30).setDuration(1000);
				ObjectAnimator animator2 = ObjectAnimator.ofFloat(v, "alpha", 0).setDuration(1000);
				AnimatorSet set1 = new AnimatorSet();
				AnimatorSet set2 = new AnimatorSet();
				animator1.setRepeatCount(1);
				animator1.setRepeatMode(Animation.REVERSE);
				animator2.setRepeatCount(1);
				animator2.setRepeatMode(Animation.REVERSE);
				set1.setStartDelay(200);
				set2.setStartDelay(200);
				
				boolean isLiked = Boolean.parseBoolean(ivEmptyHeart.getContentDescription().toString());
				if(isLiked) {
					if(likes > 0) { 						
						set1.addListener(new AnimatorListener() {
							
							@Override
							public void onAnimationStart(Animator animation) {
								ivEmptyHeart.setImageResource(R.drawable.empty_heart_anime);
								Toast.makeText(getContext(), "start", Toast.LENGTH_SHORT).show();								
							}
							
							@Override
							public void onAnimationRepeat(Animator animation) {
								
							}
							
							@Override
							public void onAnimationEnd(Animator animation) {
								ivEmptyHeart.setImageResource(R.drawable.empty_heart);				
							}
							
							@Override
							public void onAnimationCancel(Animator animation) {
								// TODO Auto-generated method stub
								
							}
						});
						set1.play(animator1);
						set2.play(animator2);
						set1.start();
						set2.start();
						
						likes--;
						ivEmptyHeart.setContentDescription("false");						
						tvLikes.setText(likes+"");
					}
				} else {					
					set1.addListener(new AnimatorListener() {					
						@Override
						public void onAnimationStart(Animator animation) {
							ivEmptyHeart.setImageResource(R.drawable.full_heart_anime);							
							Toast.makeText(getContext(), "start", Toast.LENGTH_SHORT).show();							
						}
						
						@Override
						public void onAnimationRepeat(Animator animation) {
							
						}
						
						@Override
						public void onAnimationEnd(Animator animation) {
							ivEmptyHeart.setImageResource(R.drawable.full_heart);
						}
						
						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub
							
						}
					});
					set1.play(animator1);
					set2.play(animator2);
					set1.start();
					set2.start();
					
					likes++;
					ivEmptyHeart.setContentDescription("true");													
					tvLikes.setText(likes+"");
				}
				TreeNode tree = (TreeNode)v.getTag();
				tree.setLikes(likes);
				tree.saveInBackground();
			}
		}); 

		if (node.getUser() != null)
		{
			String name = node.getUser().getString("name");
			if (name == null || name.isEmpty())
			{
				tvUserName.setText (node.getUser().getEmail());
			}
			else
			{
				tvUserName.setText (node.getUser().getString("name"));
			}
		}
 

		//ivProfileImage.setImageResource(android.R.color.transparent);
		/*if(node.getText().equals("")) {
			tvBody.setVisibility(View.GONE);
		}*/
		
		tvBody.setText (node.getText());
		tvStoryTitle.setText(node.getTitle());
		
		int likes = node.getLikes();
		if (likes > 0)
		{
			tvLikes.setText(likes + "");
		}
		else
		{
			tvLikes.setText("0");
		}
		
		if (mScreenNo == 1)
		{
		
		}
		else
		{
			int endings = node.getNumTreeLeafNodes();
			if (endings == 0)
			{
				endings = 1;
			}
			String plural = (endings>1)? "s": "";
			tvVersions.setText(endings + " ending" + plural);
		}

		return convertView;

	}

	public String getPath(Uri uri) {
	    String[] projection = { MediaStore.Images.Media.DATA };
	    CursorLoader loader = new CursorLoader(getContext(), uri, projection, null, null, null);
	    Cursor cursor = loader.loadInBackground();
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    
	    if(cursor!=null && cursor.moveToFirst()) {
	    	return cursor.getString(column_index);
	    } else {
	    	return null;  
	    }
	}
	
}
