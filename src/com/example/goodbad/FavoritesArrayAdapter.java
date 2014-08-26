package com.example.goodbad;

import java.io.Console;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.loopj.android.image.SmartImageView;
import com.parse.ParseUser;

public class FavoritesArrayAdapter extends ArrayAdapter<Favorites> {
	
	private int mScreenNo;

	public FavoritesArrayAdapter(Context context, List<Favorites> favorites, int screenNo) {
		super(context, R.layout.favnode_item, favorites);
		this.mScreenNo = screenNo;
		Log.d ("DEBUG", "screen no " + mScreenNo);
	}
	
 

	public void addStory (List<Favorites> nodes)
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
	public View getView(int position, View convertView, ViewGroup parent) {
		Favorites node = getItem (position);
		if (convertView == null)
		{
			LayoutInflater inflator = LayoutInflater.from(getContext());
			convertView = inflator.inflate(R.layout.favnode_item, parent, false);
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
		
		 RelativeLayout rlHeader=(RelativeLayout) convertView.findViewById(R.id.rlHeader);
		//ImageView ivProfileImage = (ImageView)v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		TextView tvOrigBody = (TextView) convertView.findViewById(R.id.tvOrigBody);

		TextView tvVersions = (TextView) convertView.findViewById(R.id.tvVersions);
		TextView tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);
		TextView tvStoryTitle = (TextView) convertView.findViewById(R.id.tvStoryTitle);
		final TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);		
		final ImageView ivEmptyHeart = (ImageView) convertView.findViewById(R.id.ivEmptyHeart);
		ivEmptyHeart.setContentDescription("true");
		ivEmptyHeart.setImageResource(R.drawable.full_heart); //make default as set to show red , being a favorite
		String relativeTime = getRelativeTimeAgo (node.getParseObject("FavNodeId").getUpdatedAt());// (node.getUpdatedAt());
		tvRelativeTime.setText(relativeTime);
		
		ivEmptyHeart.setTag(node);
		convertView.setTag(node);
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
						ivEmptyHeart.setImageResource(R.drawable.empty_heart);
						tvLikes.setText(likes+"");
					}
				} else {
					likes++;
					ivEmptyHeart.setContentDescription("true");				
					ivEmptyHeart.setImageResource(R.drawable.full_heart);
					tvLikes.setText(likes+"");
				}
				
				Favorites tree = (Favorites)v.getTag();
				tree.setLikes(likes);
				tree.saveInBackground();
			}
		}); 

		if ( node.getUser() != null)
		{
			//String name =   node.getUser().getString("name");
			String name =   node.getParseObject("FavNodeId").getParseObject("user").getString("name");
			Log.d ("DEBUG", "user game " + name);
			Log.d ("DEBUG", "MAINUSER: "+ ParseUser.getCurrentUser().getObjectId()  ); 
			if (name == null || name.isEmpty())
			{
				tvUserName.setText (node.getUser().getEmail());
			}
			else
			{
				//tvUserName.setText (node.getUser().getString("name"));
 				tvUserName.setText(name);
			}
		}
 

		//ivProfileImage.setImageResource(android.R.color.transparent);
		/*if(node.getText().equals("")) {
			tvBody.setVisibility(View.GONE);
		}*/
		String oText;
		String favText  ;
	  //  LayoutParams params = rlHeader.getLayoutParams();

		if(node.getParseObject("FavNodeId").getString("storyid1")==
			node.getParseObject("FavNodeId").getString("parentid"))
		{
			  oText="";
//			  View b = convertView.findViewById(R.id.tvOrigBody);
//			  b.setVisibility(View.GONE);
			    tvOrigBody.setVisibility(View.GONE);
			  //  params.height = 35;
			   // rlHeader.setLayoutParams(params);
		}
		else{
			 tvOrigBody.setVisibility(View.VISIBLE);
		  oText=  node.getParseObject("FavNodeId").getParseObject("storyid1").getString("text")  +"\n ----\n------\n"  ;
		  //  params.height = 70;
		//    rlHeader.setLayoutParams(params);

		}
 
		 favText=node.getParseObject("FavNodeId").getString("text");
		 tvBody.setText  ( favText) ;
		 tvOrigBody.setText(oText);
		 // tvBody.setText  (Html.fromHtml(oText)+favText) ;//getText());
	//	tvBody.setText  (node.getString("FavNodeId.text")) ;//getText());

		//tvStoryTitle.setText (node.getTitle());  
		String oTitle=node.getParseObject("FavNodeId").getParseObject("storyid1").getString("title");
		tvStoryTitle.setText(oTitle);
		int likes = node.getParseObject("FavNodeId").getInt("likes");//node.getLikes();
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
			int endings = node.getParseObject("FavNodeId").getInt("numTreeLeafNodes");//node.getNumTreeLeafNodes();
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
