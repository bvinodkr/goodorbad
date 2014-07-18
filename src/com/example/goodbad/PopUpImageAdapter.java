package com.example.goodbad;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PopUpImageAdapter extends ArrayAdapter<PopUpWindowItem> {

	public PopUpImageAdapter(Context context,  List<PopUpWindowItem> popUpWindowItemList) {
		super(context, R.layout.popup_item, popUpWindowItemList);		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PopUpWindowItem popUpWindowItem = getItem (position);
		if (convertView == null)
		{
			LayoutInflater inflator = LayoutInflater.from(getContext());
			convertView = inflator.inflate(R.layout.popup_item, parent, false);
		} 		
		
		ImageView ivPopUpItemImage = (ImageView) convertView.findViewById(R.id.ivPopUpItemImage);		
		TextView tvPopUpItemImageTitle = (TextView) convertView.findViewById(R.id.tvPopUpItemImageTitle);
		
		ivPopUpItemImage.setImageResource(popUpWindowItem.getSource());
		tvPopUpItemImageTitle.setText(popUpWindowItem.getTitle());
		
		Log.d("debug", tvPopUpItemImageTitle.getText().toString()); 
		
		return convertView;
	}

}

/*public class PopUpImageAdapter extends BaseAdapter {

	private Context mContext;

	public PopUpImageAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(100, 80));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(1, 1, 1, 1);
			imageView.setBackgroundColor(0xFFCC00);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(R.drawable.sample_0);
		return imageView;
	}

}*/