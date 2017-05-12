package com.example.flocator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		 TextView nam= (TextView) findViewById(R.id.textView50);
		 TextView addr= (TextView) findViewById(R.id.textView40);
		 TextView postc= (TextView) findViewById(R.id.textView30);
		 TextView ar= (TextView) findViewById(R.id.textView20);
		 TextView ct= (TextView) findViewById(R.id.textView10);
		 
		 Bundle extras = getIntent().getExtras();
		 String nm = extras.getString(MainActivity.name.get(SchoolActivity.pos));
		 String adr = extras.getString(MainActivity.address.get(SchoolActivity.pos));
		 String poc = extras.getString(MainActivity.postalcode.get(SchoolActivity.pos));
		 String a = extras.getString(MainActivity.area.get(SchoolActivity.pos));
		 String c = extras.getString(MainActivity.cit.get(SchoolActivity.pos));
		 
		 //Set text
		 nam.setText("The Name of the School:" + " " + nm);
		 addr.setText("The Address   :" + " " + adr); 
		 postc.setText("The Postal Code:" + " " + poc);
		 ar.setText("The Area :" + " " + a);
		 ct.setText("The City :" + " " + c);
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
