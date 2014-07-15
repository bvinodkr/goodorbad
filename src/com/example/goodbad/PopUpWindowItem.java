package com.example.goodbad;

public class PopUpWindowItem {
	
	private String mItemName;
	private int mItemSource;
	
	public PopUpWindowItem(String itemName, int itemLocation) {
		mItemName = itemName;
		mItemSource = itemLocation;
	}

	public String getTitle() {
		return mItemName;
	}

	public int getSource() {
		return mItemSource;
	}
}
