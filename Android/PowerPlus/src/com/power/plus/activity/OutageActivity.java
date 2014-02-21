package com.power.plus.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.power.plus.R;

public class OutageActivity extends FragmentActivity implements
		OnMapClickListener {

	private View mDecorView;
	private static final int INITIAL_HIDE_DELAY = 3000;
	private GoogleMap mMap;
	private LinearLayout infoLayout =null;
	private TextView txtInfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outage);
		
		txtInfo = (TextView)findViewById(R.id.txtInfo);
		mDecorView = getWindow().getDecorView();

		mDecorView
				.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
					@Override
					public void onSystemUiVisibilityChange(int visibility) {
						if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
							delayedHide(INITIAL_HIDE_DELAY);
						}
					}
				});

		SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mMap = supportMapFragment.getMap();

		infoLayout = (LinearLayout) findViewById(R.id.infoPane);
		final TextView txtHide = (TextView) findViewById(R.id.txtHide);
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
	protected void onStart() {
		super.onStart();
		mMap.setOnMapClickListener(this);

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		if (hasFocus) {
			delayedHide(INITIAL_HIDE_DELAY);
		} else {
			mHideHandler.removeMessages(0);
		}
	}

	private void hideSystemUI() {
		mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LOW_PROFILE
				| View.SYSTEM_UI_FLAG_IMMERSIVE);
	}

	@SuppressLint("HandlerLeak")
	private final Handler mHideHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			hideSystemUI();
		}
	};

	private void delayedHide(int delayMillis) {
		mHideHandler.removeMessages(0);
		mHideHandler.sendEmptyMessageDelayed(0, delayMillis);
	}

	@Override
	public void onMapClick(LatLng point) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0);
		params.weight = 1;
		infoLayout.setLayoutParams(params);
		txtInfo.setText("Info Will Go Here\n"+point.latitude+"\n"+point.longitude);
	}
}
