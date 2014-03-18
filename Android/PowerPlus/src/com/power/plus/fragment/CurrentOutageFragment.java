package com.power.plus.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.power.plus.R;
import com.powerplus.model.Area;
import com.powerplus.model.Coordinates;
import com.powerplus.tools.RayCastingAlgirithm;

public class CurrentOutageFragment extends Fragment implements
		OnMapClickListener {

	private GoogleMap mMap;
	private LinearLayout infoLayout = null;
	protected TextView txtInfo = null;
	private View view;
	private HashMap<String, PolygonOptions> polygonMap = new HashMap<String, PolygonOptions>();
	private ArrayList<String> outageAreaNames = new ArrayList<String>();
	private String notes;

	private String GET_ALL_AREAS = "getAllAreasWithCoordinates";
	private String GET_ALL_OUTAGES = "getAllOutageNames";
	private String GET_NOTES_BY_AREA = "getNotesByAreaName";
	private String NAMESPACE = "http://webservice.powerplus.com/";
	private static final String URL = "http://powerplus.cloudapp.net:80/PowerPlusWebService/PowerPlusWebService?wsdl";

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
			view = inflater.inflate(R.layout.current_outage_layout, container,
					false);
		} catch (InflateException e) {
		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		mMap.setOnMapClickListener(this);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.68923,
				-79.5211), 11));
		txtInfo = (TextView) getActivity().findViewById(R.id.txtInfo);
		infoLayout = (LinearLayout) getActivity().findViewById(R.id.infoPane);
		TextView txtHide = (TextView) getActivity().findViewById(R.id.txtHide);
		txtHide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LinearLayout.LayoutParams params = new LayoutParams(1, 1);

				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, 0);
				}

				else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
					params = new LinearLayout.LayoutParams(0,
							LayoutParams.MATCH_PARENT);

				}
				params.weight = 0;
				infoLayout.setLayoutParams(params);
			}
		});

		AsyncCallLoadOutageAndMapData preloadTask = new AsyncCallLoadOutageAndMapData();
		// Call execute
		preloadTask.execute();

	}

	@Override
	public void onMapClick(LatLng point) {

		LinearLayout.LayoutParams params = new LayoutParams(1, 1);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		}

		else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);

		}
		params.weight = 2;
		infoLayout.setLayoutParams(params);
		// Ray Casting Algorithm
		for (final String key : polygonMap.keySet()) {

			if (RayCastingAlgirithm.isPointInPolygon(point,
					(ArrayList<LatLng>) polygonMap.get(key).getPoints())) {
				if (outageAreaNames.contains(key)) {
					AsyncCallGetNotes loadNotesTask = new AsyncCallGetNotes();
					loadNotesTask.execute(key);
				} else
					txtInfo.setText(key + "\nNo outage in this Area");
				break;
			}
		}
	}

	private class AsyncCallLoadOutageAndMapData extends
			AsyncTask<String, Void, Void> {
		private static final String TAG = "CHRANA";

		@Override
		protected Void doInBackground(String... params) {
			Log.i(TAG, "doInBackground");

			ArrayList<Area> areas = getAllAreas();
			outageAreaNames = getAllOutages();
			for (Area area : areas) {

				PolygonOptions pOptions = new PolygonOptions();
				pOptions.strokeColor(Color.BLACK);
				pOptions.fillColor(0x1F000000);
				pOptions.strokeWidth(2);

				Coordinates[] coordinates = area.getCoordinateList();
				for (int i = 0; i < coordinates.length; i++) {
					if (coordinates[i] != null) {
						pOptions.add(new LatLng(coordinates[i].getLatitude(),
								coordinates[i].getLongitude()));
					}
				}

				polygonMap.put(area.getAreaName(), pOptions);
			}

			return null;
		}

		private ArrayList<Area> getAllAreas() {
			ArrayList<Area> result = new ArrayList<Area>();
			SoapObject request = new SoapObject(NAMESPACE, GET_ALL_AREAS);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invoke web service
				androidHttpTransport.call(NAMESPACE + GET_ALL_AREAS, envelope);
				// Get the response
				SoapObject response = (SoapObject) envelope.getResponse();
				for (int i = 0; i < response.getPropertyCount(); i++) {
					SoapObject area = (SoapObject) response.getProperty(i);
					String areaName = area.getPropertyAsString(0);

					Coordinates[] coords = new Coordinates[area
							.getPropertyCount()];
					area.getPropertyCount();
					Coordinates c;
					for (int j = 1; j < area.getPropertyCount(); j++) {

						SoapObject coordinate = (SoapObject) area
								.getProperty(j);

						Double lat = Double.parseDouble(coordinate
								.getPropertyAsString(0));
						Double lon = Double.parseDouble(coordinate
								.getPropertyAsString(1));
						c = new Coordinates(lat, lon);
						coords[j - 1] = c;
					}
					result.add(new Area(areaName, coords));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		private ArrayList<String> getAllOutages() {

			ArrayList<String> result = new ArrayList<String>();
			SoapObject request = new SoapObject(NAMESPACE, GET_ALL_OUTAGES);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invoke web service
				androidHttpTransport
						.call(NAMESPACE + GET_ALL_OUTAGES, envelope);
				// Get the response
				SoapObject response = (SoapObject) envelope.getResponse();
				for (int i = 0; i < response.getPropertyCount(); i++) {
					String areaName = response.getPropertyAsString(i);
					result.add(areaName);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;

		}

		@Override
		protected void onPostExecute(Void result) {
			for (final String key : polygonMap.keySet()) {

				if (outageAreaNames.contains(key)) {
					polygonMap.get(key).fillColor(0x2FFF0000);
				}
				mMap.addPolygon(polygonMap.get(key));
			}
		}
	}

	private class AsyncCallGetNotes extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {

			notes = getNotesByArea(params[0]);
			return null;
		}

		private String getNotesByArea(String areaName) {
			String result = areaName + "\n";
			SoapObject request = new SoapObject(NAMESPACE, GET_NOTES_BY_AREA);
			request.addProperty("arg0", areaName);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invoke web service
				androidHttpTransport.call(NAMESPACE + GET_NOTES_BY_AREA,
						envelope);
				// Get the response
				SoapObject response = (SoapObject) envelope.getResponse();
				for (int i = 0; i < response.getPropertyCount(); i++) {
					SoapObject note = (SoapObject) response.getProperty(i);
					result += note.getPropertyAsString(1) + "\n";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Void result) {
			txtInfo.setText(notes);
		}

	}

}
