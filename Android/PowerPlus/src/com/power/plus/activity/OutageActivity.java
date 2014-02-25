package com.power.plus.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.power.plus.R;

@TargetApi(19)
public class OutageActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private CharSequence mTitle;
	private String[] mNavTitles = { "View Outage", "Add Outage" };
	private ListView mDrawerList;
	private View outageView;
	private static final int INITIAL_HIDE_DELAY = 3000;
	private ActionBarDrawerToggle mDrawerToggle;
	LinearLayout currentOutageLayout;
	LinearLayout newOutageLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		outageView = getWindow().getDecorView();

		outageView
				.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
					@Override
					public void onSystemUiVisibilityChange(int visibility) {
						if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
							delayedHide(INITIAL_HIDE_DELAY);
						}
					}
				});

		currentOutageLayout = (LinearLayout) findViewById(R.id.current_outage_layout);
		newOutageLayout = (LinearLayout) findViewById(R.id.new_outage_layout);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mNavTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.open_drawer,
				R.string.closed_drawer) {
			private CharSequence mDrawerTitle;

			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}

		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		if(savedInstanceState == null){
			swapFragment(0);
		}
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
		outageView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
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

	private void swapFragment(int position) {
		if (position == 0) {
			newOutageLayout.setVisibility(View.GONE);
			currentOutageLayout.setVisibility(View.VISIBLE);
			
		} else if (position == 1) {
			
			newOutageLayout.setVisibility(View.VISIBLE);
			currentOutageLayout.setVisibility(View.GONE);
		}
		mDrawerList.setItemChecked(position, true);
		setTitle(mNavTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			swapFragment(position);
		}
	}

}
