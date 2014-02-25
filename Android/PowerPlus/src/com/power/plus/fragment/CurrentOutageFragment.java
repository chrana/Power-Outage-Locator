package com.power.plus.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.power.plus.R;

public class CurrentOutageFragment extends Fragment implements
OnMapClickListener {
	
	private GoogleMap mMap;
	private LinearLayout infoLayout = null;
	private TextView txtInfo = null;
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	    }
	    try {
	        view = inflater.inflate(R.layout.current_outage_layout, container, false);
	    } catch (InflateException e) {
	    }
	    return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setOnMapClickListener(this);
		txtInfo = (TextView) getActivity().findViewById(R.id.txtInfo);
				infoLayout = (LinearLayout) getActivity().findViewById(R.id.infoPane);
				TextView txtHide = (TextView) getActivity().findViewById(R.id.txtHide);
				txtHide.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, 0);
						params.weight = 0;
						infoLayout.setLayoutParams(params);
					}
				});

	}
	
	@Override
	public void onMapClick(LatLng point) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0);
		params.weight = 1;
		infoLayout.setLayoutParams(params);
		txtInfo.setText("Info Will Go Here\n" + point.latitude + "\n"
				+ point.longitude);
	}
	
	@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    MapFragment f = (MapFragment) getChildFragmentManager()
	                                         .findFragmentById(R.id.map);
	    if (f != null) 
	    	getChildFragmentManager().beginTransaction().remove(f).commit();
	}
	
	@Override
	public void onStop() {
	    super.onStop();
	    MapFragment f = (MapFragment) getChildFragmentManager()
	                                         .findFragmentById(R.id.map);
	    if (f != null) 
	    	getChildFragmentManager().beginTransaction().remove(f).commit();
	}
	
	

}
