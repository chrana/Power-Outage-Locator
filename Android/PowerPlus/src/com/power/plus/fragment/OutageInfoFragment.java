package com.power.plus.fragment;

import com.power.plus.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OutageInfoFragment extends Fragment {
	
	protected TextView tv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.outage_info_layout, container,	false);
		return v;
	}
	
	@Override
	public void onStart(){
		super.onStart();
		tv = (TextView) getView().findViewById(R.id.txtHide);
	}

}
