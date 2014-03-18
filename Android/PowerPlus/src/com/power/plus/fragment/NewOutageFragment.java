package com.power.plus.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.power.plus.R;
import com.powerplus.tools.ServiceHandler;

public class NewOutageFragment extends Fragment {

	private static final String TAG_RESULTS = "results";
	private static final String TAG_ADD_COMPONENTS = "address_components";
	private String url = null;
	private EditText txtPostalCode;
	private String postalcode = "";
	ProgressDialog progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.new_outage_layout, container, false);
		return v;
	}

	@Override
	public void onStart() {

		super.onStart();

		txtPostalCode = (EditText) getActivity().findViewById(
				R.id.txtPostalCode);
		ImageButton btnGetLocation = (ImageButton) getActivity().findViewById(
				R.id.btnGetLocation);
		btnGetLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				try{
				progressBar = ProgressDialog.show(getActivity(),
						"Getting Your Location",
						"Please wait while we get your location", true);
				}catch(Exception e){
					
				}

				LocationManager locationManager = (LocationManager) getActivity()
						.getSystemService(Context.LOCATION_SERVICE);

				// Define a listener that responds to location updates
				LocationListener locationListener = new LocationListener() {
					public void onLocationChanged(Location location) {
						url = "http://maps.googleapis.com/maps/api/geocode/json?";
						url += "latlng=" + location.getLatitude() + ",%20"
								+ location.getLongitude() + "&sensor=true";
						GetPostalCode getPostalCodeTask = new GetPostalCode();
						getPostalCodeTask.execute();

					}

					public void onStatusChanged(String provider, int status,
							Bundle extras) {
					}

					public void onProviderEnabled(String provider) {
					}

					public void onProviderDisabled(String provider) {

						Toast.makeText(getActivity().getApplicationContext(),
								"Cann't Find Your Location", Toast.LENGTH_LONG)
								.show();
					}
				};

				// Register the listener with the Location Manager to receive
				// location updates
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, 0, 0,
						locationListener);

			}
		});

	}

	private class GetPostalCode extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {

			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("URL", url);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					// Getting JSON Array node
					JSONArray results = jsonObj.getJSONArray(TAG_RESULTS);

					if (results != null) {

						// looping through All Contacts

						JSONArray adressComponents = results.getJSONObject(1)
								.getJSONArray(TAG_ADD_COMPONENTS);

						JSONObject adressComponent = adressComponents
								.getJSONObject(0);

						postalcode = adressComponent.getString("long_name");
						Log.d("POSTALCODE", postalcode);
					} else {
						Toast.makeText(getActivity().getApplicationContext(),
								"Cann't Find Your Location", Toast.LENGTH_LONG)
								.show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			txtPostalCode.setText(postalcode);
			progressBar.dismiss();
		}
	}

}
