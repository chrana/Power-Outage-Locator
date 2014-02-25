package com.power.plus.fragment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

//import com.power.plus.R;


import com.power.plus.R;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class OutageInfoFragment extends Fragment {
	
	private String METHOD_NAME = "getApplicationName"; // our webservice method name
	private String NAMESPACE = "http://webservice.powerplus.com/"; // Here package name in webservice with reverse order.
	private String SOAP_ACTION = NAMESPACE + METHOD_NAME; // NAMESPACE + method name
	private static final String URL = "http://powerplus.cloudapp.net:80/PowerPlusConverter/sayhello?wsdl"; // you must use ipaddress here, don’t use Hostname or localhost
	private TextView tv;
	

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
		AsyncCallWS task = new AsyncCallWS();
        //Call execute 
        task.execute();
	}
	
	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        private static final String TAG = "CHRANA";
        private String resultMsg;

		@Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");
            getMSG();
            return null;
        }
 
        private void getMSG() {
        	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
        	envelope.setOutputSoapObject(request);
        	HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        	
        	try {
                //Invole web service
                androidHttpTransport.call(SOAP_ACTION, envelope);
                //Get the response
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                resultMsg = response.toString();
                tv.setText(resultMsg);
         
            } catch (Exception e) {
                e.printStackTrace();
            }
			
		}

		@Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            tv.setText(resultMsg);
        }
 
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            tv.setText("Calculating...");
        }
 
        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
        }
 
    }
}