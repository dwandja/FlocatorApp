package com.example.flocator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.flocator.MainActivity;

public class SchoolActivity extends Activity {
	
	public ListView lv;
	public TextView tv;
	public Intent i;
    public Bundle b;
    public static int pos;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_school);
		
	    lv = (ListView) findViewById(R.id.listView1);
		tv= (TextView) findViewById(R.id.textView2);
		Bundle extras = getIntent().getExtras();
		
		   if(extras!= null) 
		   {  
		 ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, MainActivity.name);  	 
		 lv.setAdapter(adapter3);	
			  
		 i = new Intent(this, DetailsActivity.class);		  
		 b = new Bundle();
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				TextView tv = (TextView) arg1;
				 pos = MainActivity.name.indexOf(tv.getText());
				//Toast.makeText(getApplicationContext(), MainActivity.name.get(pos), Toast.LENGTH_LONG).show();	
				//Toast.makeText(getApplicationContext(), MainActivity.address.get(pos), Toast.LENGTH_LONG).show();
				//Toast.makeText(getApplicationContext(), MainActivity.postalcode.get(pos), Toast.LENGTH_LONG).show();
				//Toast.makeText(getApplicationContext(), MainActivity.area.get(pos), Toast.LENGTH_LONG).show();	
				//Toast.makeText(getApplicationContext(), MainActivity.cit.get(pos), Toast.LENGTH_LONG).show();	
				
				
				b.putString(MainActivity.name.get(pos),MainActivity.name.get(pos));
				b.putString(MainActivity.address.get(pos),MainActivity.address.get(pos));
				b.putString(MainActivity.postalcode.get(pos),MainActivity.postalcode.get(pos));
				b.putString(MainActivity.area.get(pos),MainActivity.area.get(pos));
				b.putString(MainActivity.cit.get(pos),MainActivity.cit.get(pos));
				i.putExtras(b);	
				 
				// Start activity
				startActivity(i);				 				
			  }
		  });	       										  
       }			   	   		   	   
   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.school, menu);
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
