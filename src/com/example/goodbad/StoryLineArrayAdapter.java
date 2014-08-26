package com.example.goodbad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.goodbad.fragments.ComposeStoryFragment;
import com.hipmob.gifanimationdrawable.GifAnimationDrawable;
import com.loopj.android.image.SmartImageView;
import com.parse.ParseUser;

public class StoryLineArrayAdapter extends ArrayAdapter<TreeNode> {
	
	private int mScreenNo;
	private FragmentManager mFragmentManager;
	private List<TreeNode> storyLineNodeList;
	private ImageView ivForkStory;
	
	public StoryLineArrayAdapter(Context context, List<TreeNode> treeNodes, int screenNo, FragmentManager fragmentManager) {
		super(context, R.layout.treenode_item, treeNodes);
		this.mScreenNo = screenNo;
		this.mFragmentManager = fragmentManager;
		this.storyLineNodeList = treeNodes;
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
	
	public ImageView getIvForkItem() {
		return ivForkStory;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final TreeNode node = getItem (position);
		if (convertView == null)
		{
			LayoutInflater inflator = LayoutInflater.from(getContext());
			convertView = inflator.inflate(R.layout.story_line_item, parent, false);
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
		String imageUrl = node.getImageUrl();
		
		{
			ivItemImage.setVisibility(View.GONE);
			vvItemVideo.setVisibility(View.GONE);
		}
		
		if (imageUrl != null && !imageUrl.isEmpty())
		{
			//Log.d ("DEBUG", "image url = " + node.getImageUrl());
			if (imageUrl.contains("mp4"))
			{
				vvItemVideo.setVisibility(View.VISIBLE);
				vvItemVideo.setVideoPath(imageUrl);
				MediaController mediaController = new MediaController(getContext());
				mediaController.setAnchorView(vvItemVideo);
				vvItemVideo.setMediaController(mediaController);
				vvItemVideo.requestFocus();
				vvItemVideo.start();
			}
			else if(imageUrl.contains(".gif")) 
			{
				ivItemImage.setVisibility(View.VISIBLE);
				vvItemVideo.setVisibility(View.GONE);
				loadGifIntoImageView(ivItemImage, R.drawable.got);
			}
			else
			{
				ivItemImage.setVisibility(View.VISIBLE);
				vvItemVideo.setVisibility(View.GONE);
				if(imageUrl.contains("http")) {
					ivItemImage.setImageUrl(imageUrl);
				} else {
					Bitmap imageBitmap;
					try {
						//imageBitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(Uri.parse(imageUrl)));						
						//imageBitmap = BitmapFactory.decodeFile(Uri.parse(imageUrl).getPath());
						imageBitmap = loadPrescaledBitmap(Uri.parse(imageUrl).getPath());
						ivItemImage.setImageBitmap(imageBitmap);
						ivItemImage.setContentDescription(imageUrl);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
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
		TextView tvVersions = (TextView) convertView.findViewById(R.id.tvVersions);
		TextView tvRelativeTime = (TextView) convertView.findViewById(R.id.tvRelativeTime);
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
						ivEmptyHeart.setImageResource(R.drawable.empty_heart);
						tvLikes.setText(likes+"");
					}
				} else {
					likes++;
					ivEmptyHeart.setContentDescription("true");				
					ivEmptyHeart.setImageResource(R.drawable.full_heart);
					tvLikes.setText(likes+"");
				}
				TreeNode tree = (TreeNode)v.getTag();
				tree.setLikes(likes);
				tree.saveInBackground();
			}
		}); 
		
		int likes = node.getLikes();
		if (likes > 0)
		{
			tvLikes.setText(likes + "");
		}
		else
		{
			tvLikes.setText("0");
		}
		//tvUserName.setText (node.getUser().getUsername());		 
		//ivProfileImage.setImageResource(android.R.color.transparent);
		/*if(node.getText().equals("")) {
			tvBody.setVisibility(View.GONE);
		}*/
		tvBody.setText (node.getText());
		
		ivForkStory = (ImageView) convertView.findViewById(R.id.ivForkStory);
		ivForkStory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/*InlineComposeDialogFragment inlineComposeStoryFragment = InlineComposeDialogFragment.newInstance(null);
				inlineComposeStoryFragment.show(mFragmentManager, "dialog_fragment");*/
				
				ParseUser user = ParseUser.getCurrentUser();

				//if(ParseUser.getCurrentUser() != null  ) {

					ComposeStoryFragment composeStoryFragment = ComposeStoryFragment.newInstance("Compose Story", node);
					//composeStoryFragment.show(mFragmentManager, "fragment_compose_tweet");

					//FragmentActivity fmActivity = new FragmentActivity();
					FragmentTransaction ft = mFragmentManager.beginTransaction();
					ft.replace(R.id.rlStoryLineViewPager, composeStoryFragment);
					ft.addToBackStack(null);
					ft.commit();

				//}else{
					/*Intent i = new Intent(MainActivity.this, ComposeDispatchActivity.class);
					//	String	username =user.getUsername();
					//	i.putExtra("result", username);

					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i); */
				//}
			}
		});
				
		if (node.getUser () != null)
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
		else
		{
			tvUserName.setText("anonymous");
		}

		
//		Log.d("debug", node.getText()); 
		/*if (position%2 == 0) {
			convertView.setBackgroundColor(0xFF99CCFF);
		} */
		
		if (mScreenNo == 1)
		{
			TreeNodeAPI api = new TreeNodeAPI ();
			int endings = api.getSiblingCount(node);

			String plural = (endings>1)? "s": "";
			tvVersions.setText(endings + " fork" + plural);
		}
		else
		{
	
		}
		
//		Log.d ("debug", "in screen 1, adding swipe listeners");
/*		convertView.setOnTouchListener(new OnSwipeTouchListener(getContext(), node) {
			  
			  
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
		*/
		return convertView;
	}	
	
	// Loads a GIF from the specified raw resource folder into an ImageView
    protected void loadGifIntoImageView(ImageView ivImage, int rawId) {
        try {
            GifAnimationDrawable anim = new GifAnimationDrawable(getContext().getResources().openRawResource(rawId));
            ivImage.setImageDrawable(anim);
            ((GifAnimationDrawable) ivImage.getDrawable()).setVisible(true, true);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Bitmap loadPrescaledBitmap(String filename) throws IOException {
    	  // Facebook image size
    	  final int IMAGE_MAX_SIZE = 630;

    	  File file = null;
    	  FileInputStream fis;

    	  BitmapFactory.Options opts;
    	  int resizeScale;
    	  Bitmap bmp;

    	  file = new File(filename);

    	  // This bit determines only the width/height of the bitmap without loading the contents
    	  opts = new BitmapFactory.Options();
    	  opts.inJustDecodeBounds = true;
    	  fis = new FileInputStream(file);
    	  BitmapFactory.decodeStream(fis, null, opts);
    	  fis.close();

    	  // Find the correct scale value. It should be a power of 2
    	  resizeScale = 1;

    	  if (opts.outHeight > IMAGE_MAX_SIZE || opts.outWidth > IMAGE_MAX_SIZE) {
    	    resizeScale = (int)Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(opts.outHeight, opts.outWidth)) / Math.log(0.5)));
    	  }

    	  // Load pre-scaled bitmap
    	  opts = new BitmapFactory.Options();
    	  opts.inSampleSize = resizeScale;
    	  fis = new FileInputStream(file);
    	  bmp = BitmapFactory.decodeStream(fis, null, opts);

    	  fis.close();

    	  return bmp;
    	} 
}


