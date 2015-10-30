package com.example.dropdown;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static Spinner spinner1;
	public static TextView textView1;
	public static TextView textView2;
	public static TextView textView3;
	public static TextView textView4;
	public static TextView textView5;
	public static TextView textView6;
	public static Button button1;
	
	public static ProgressBar loadingSpinner;
	ArrayList<String> lst = new ArrayList<String>();
	static HashMap<String,String> basejobdetails= new HashMap();
	static HashMap<String,String> individualJobdetails= new HashMap();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Debug.startMethodTracing("dropdown");
		setContentView(R.layout.activity_main);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(itemSelectionListener);
		loadingSpinner = (ProgressBar) findViewById(R.id.progressBar1);
		textView1 = (TextView)findViewById(R.id.TextView6);
		textView2 = (TextView)findViewById(R.id.TextView8);
		textView3 = (TextView)findViewById(R.id.TextView10);
		textView4 = (TextView)findViewById(R.id.TextView12);
		textView5 = (TextView)findViewById(R.id.TextView14);
		textView6 = (TextView)findViewById(R.id.TextView16);
		button1 = (Button) findViewById(R.id.button1);
		new AsyncTaskHelper("GET","http://en-macpro.mynt.myntra.com:9090/api/json", spinnerDataHandler).execute();
	}
	
	

	OnItemSelectedListener itemSelectionListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			textView1.setText("");
			textView2.setText("");
			textView3.setText("");
			textView4.setText("");
			textView5.setText("");
			textView6.setText("");
			new AsyncTaskHelper("GET","http://192.168.20.63:8080/job/"+spinner1
					.getSelectedItem().toString()+"/api/json", jobDataHandler).execute();
			
			Toast toast = Toast.makeText(getApplicationContext(), spinner1
					.getSelectedItem().toString(), Toast.LENGTH_LONG);
			toast.show();

			// TODO : make the api call

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};
	
	Handler spinnerDataHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			String result = msg.getData().getString("result");
			lst=JSONHelper.getJenkinsJobs(result);
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_spinner_dropdown_item, lst);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner1.setAdapter(dataAdapter);
			loadingSpinner.setVisibility(View.GONE);
		}
	};
	
	Handler jobDataHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			String result = msg.getData().getString("result");
			basejobdetails=JSONHelper.getBasicJobDetails(result);
			String lastBuildUrl = basejobdetails.get("lastBuildUrl");
			if(lastBuildUrl!="")
			{
			getLastBuildStatus(lastBuildUrl);
			textView1.setText(basejobdetails.get("HealthReport"));
			textView3.setText(basejobdetails.get("lastBuild"));
			textView4.setText(basejobdetails.get("lastSuccessfulBuild"));
			textView5.setText(basejobdetails.get("nextBuildNumber"));
			}
			else
			{
			textView1.setText(basejobdetails.get("HealthReport"));
			textView2.setText("Not Applicable");
			textView3.setText(basejobdetails.get("lastBuild"));
			textView4.setText(basejobdetails.get("lastSuccessfulBuild"));
			textView5.setText(basejobdetails.get("nextBuildNumber"));
			textView6.setText("Not Applicable");
			}
			loadingSpinner.setVisibility(View.GONE);
		}
	};
	
	static Handler jobStatusDataHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			String result = msg.getData().getString("result");
			individualJobdetails=JSONHelper.getIndividualJobDetails(result);
			String lastBuildStatus=individualJobdetails.get("lastBuildStatus");
			textView2.setText(lastBuildStatus);
			textView6.setText(individualJobdetails.get("lastBuildRanAt"));
			loadingSpinner.setVisibility(View.GONE);
		}
	};
	
	public static void getLastBuildStatus(String lastBuildUrl)
	{
		new AsyncTaskHelper("GET",lastBuildUrl+"api/json", jobStatusDataHandler).execute();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
//		Debug.stopMethodTracing();
	}

}
