package com.example.goodbad.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.goodbad.PopUpImageAdapter;
import com.example.goodbad.PopUpWindowItem;
import com.example.goodbad.R;

public class InlineComposeDialogFragment extends DialogFragment{
	
	private ArrayList<PopUpWindowItem> popUpWindowItemList = new ArrayList<PopUpWindowItem>();
	private PopupWindow popupWindow = null;
	
	/*private InlineComposeDialogFragmentListener listener;
	
	public interface InlineComposeDialogFragmentListener {
		void onFinishInlineComposeDialog();
	}

	public InlineComposeDialogFragment() {
		// Empty constructor required for DialogFragment
	}*/

	public static InlineComposeDialogFragment newInstance(String title) {
		InlineComposeDialogFragment frag = new InlineComposeDialogFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);

		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setHasOptionsMenu(true);
	}
	
	/*@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof InlineComposeDialogFragmentListener) {
			listener = (InlineComposeDialogFragmentListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + "must implement ComposeTweetFragmentListener");
		}
	}

	@Override
	public void onDetach() {	
		super.onDetach();
		listener = null;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		listener.onFinishInlineComposeDialog();	
	}*/
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

		View inLineComposeView = inflater.inflate(R.layout.fragment_inline_compose_dialog, container, false);
		
		return inLineComposeView;
	}

}
