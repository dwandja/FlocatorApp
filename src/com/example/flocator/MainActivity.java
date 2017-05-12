package com.example.flocator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class MainActivity extends Activity implements LocationListener {
	
	public static Spinner s1, s2,s3;
	public static ArrayList<String> numb= new ArrayList<String>();
	public static ArrayList<String> name= new ArrayList<String>();
	public static ArrayList<String> address= new ArrayList<String>();
	public static ArrayList<String> postalcode= new ArrayList<String>();
	public static ArrayList<String> area= new ArrayList<String>();
	public static ArrayList<String> cit= new ArrayList<String>();
	public static Button bt;
	public static String sp1, sp2,sp3;
    public static Cursor c;
	public static DBAdapter db;
	
	//Location
	public static String latitude;
	public static String longitude;
	public LocationManager locationManager;
	private String provider;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     
     // Get the location manager
		 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Define the criteria how to select the location provider 
		// -> use defaults
		Criteria criteria = new Criteria();
		
		/* from Dartmouth's code */
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		
		// for the emulator
		//criteria.setAccuracy(Criteria.ACCURACY_COARSE); 
		// ACCURACY_HIGH->run-time error on the emulator!
		
	    criteria.setPowerRequirement(Criteria.POWER_LOW);
	    criteria.setAltitudeRequired(false);
	    criteria.setBearingRequired(false);
	    criteria.setSpeedRequired(false);
	    criteria.setCostAllowed(true);
	   
		provider = locationManager.getBestProvider(criteria, false);
		Log.i( "LOCTAION", provider); // logging
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			Log.i("LOCATION", "Provider " + provider + " has been selected.");
			//onLocationChanged(location);
		    latitude =  Double.toString((double) (location.getLatitude()));
	        longitude = Double.toString((double) (location.getLongitude()));
	        Toast.makeText(this, latitude + ' ' + longitude,
			       Toast.LENGTH_SHORT).show();
			   
		} else {
			Log.i("LOCATION", "location is null");			
		}			    
        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        s3 = (Spinner) findViewById(R.id.spinner3);
        bt = (Button) findViewById(R.id.button1);
        db = new DBAdapter(this);       
        
        try {
            String destPath = "/data/data/" + getPackageName() +
                "/databases";
            File f = new File(destPath);
            if (!f.exists()) {            	
            	f.mkdirs();
                f.createNewFile();
            	
            	//---copy the db from the assets folder into 
            	// the databases folder---
                
                CopyDB(getBaseContext().getAssets().open("schools.db"),
                    new FileOutputStream(destPath + "/schools.db"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
          
        final List<String> category = Arrays.asList(getResources().getStringArray(R.array.Category));
		final List<String> city = Arrays.asList(getResources().getStringArray(R.array.City));
		final List<String> distance = Arrays.asList(getResources().getStringArray(R.array.Distance));
		
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, category);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, city);
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, distance);
		
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		s1.setAdapter(adapter1);
		s2.setAdapter(adapter2);
		s3.setAdapter(adapter3);
		
	    final Intent SIntent= new Intent(this, SchoolActivity.class);
	    
		bt.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sp1= s1.getSelectedItem().toString();
				sp2= s2.getSelectedItem().toString();
				sp3= s3.getSelectedItem().toString();
				
				if(sp1.equals("Toronto Schools") && sp2.equals("Toronto")){
					
					db.open();
					
					if(sp3.equals("All Schools")){	
			        c = db.getAllSchools();
			            
				        if (c.moveToFirst()) {
				        	 
				                 do {			                    
				                	SIntent.putExtra(c.getString(0),c.getString(1));
				                	numb.add(c.getString(0));
				                	name.add(c.getString(1));
				                	address.add(c.getString(2));
				                	postalcode.add(c.getString(3));
				                	area.add(c.getString(4));
				                	cit.add(c.getString(5));
				                	
     			                 } while (c.moveToNext());
				                 
				             }  			       		        
				        		db.close();      	 
				        		startActivity(SIntent);		        					        		
				        }
									
					else if( sp3.equals("1")){	
						
						
						/*final Cursor*/ c = db.getOneMileSchools();								      
				        if (c.moveToFirst()) {
				        	 
				                 do {
				                    
				                	SIntent.putExtra(c.getString(0),c.getString(1));
				                	numb.add(c.getString(0));
				                	name.add(c.getString(1));
				                	address.add(c.getString(2));
				                	postalcode.add(c.getString(3));
				                	area.add(c.getString(4));
				                	cit.add(c.getString(5));

				                 } while (c.moveToNext());
				                 
				             }  
			        
			        
				        		db.close();      	 
				        		startActivity(SIntent);		        																										
					}
										
					else if( sp3.equals("5")){
						
						/*final Cursor*/ c = db.getFiveMileSchools();							      
				        if (c.moveToFirst()) {			        	 
				                 do {				                    
				                	SIntent.putExtra(c.getString(0),c.getString(1));
				                	numb.add(c.getString(0));
				                	name.add(c.getString(1));
				                	address.add(c.getString(2));
				                	postalcode.add(c.getString(3));
				                	area.add(c.getString(4));
				                	cit.add(c.getString(5));
				                	
				                 } while (c.moveToNext());
				                 
				             }  			      			        
				        		db.close();      	 
				        		startActivity(SIntent);		        																										
					}
					
					else if( sp3.equals("10")){
						
						/*final Cursor*/ c = db.getTenMileSchools();				      
				        if (c.moveToFirst()) {				        	 
				                 do {				                    
				                	SIntent.putExtra(c.getString(0),c.getString(1));
				                	numb.add(c.getString(0));
				                	name.add(c.getString(1));
				                	address.add(c.getString(2));
				                	postalcode.add(c.getString(3));
				                	area.add(c.getString(4));
				                	cit.add(c.getString(5));
				                	
				                 } while (c.moveToNext());				                 
				             }  			      			        
				        db.close();      	 
				        		startActivity(SIntent);		        																									
					}					
					else if( sp3.equals("25")){
						
						 c = db.getTwentyFiveMileSchools();					      
				        if (c.moveToFirst()) {			        	 
				                 do {				                    
				                	SIntent.putExtra(c.getString(0),c.getString(1));
				                	numb.add(c.getString(0));
				                	name.add(c.getString(1));
				                	address.add(c.getString(2));
				                	postalcode.add(c.getString(3));
				                	area.add(c.getString(4));
				                	cit.add(c.getString(5));
				                	
				                 } while (c.moveToNext());				                 
				             }  			       			        
				        		db.close();      	 
				        		startActivity(SIntent);		        																									
					}
					
					else if( sp3.equals("50")){						
						 c = db.getFiftyMileSchools();
								      
				        if (c.moveToFirst()) {
				        	 
				                 do {
				                    
				                	SIntent.putExtra(c.getString(0),c.getString(1));
				                	numb.add(c.getString(0));
				                	name.add(c.getString(1));
				                	address.add(c.getString(2));
				                	postalcode.add(c.getString(3));
				                	area.add(c.getString(4));
				                	cit.add(c.getString(5));

				                 } while (c.moveToNext());				                 
				             }  		        
				        		db.close();      	 
				        		startActivity(SIntent);		        																									
					}																				
					else if( sp3.equals("100")){
						
						/*final Cursor*/ c = db.getHundredMileSchools();			
					      
				        if (c.moveToFirst()) {				        	 
				                 do {				                    
				                	SIntent.putExtra(c.getString(0),c.getString(1));
				                	numb.add(c.getString(0));
				                	name.add(c.getString(1));
				                	address.add(c.getString(2));
				                	postalcode.add(c.getString(3));
				                	area.add(c.getString(4));
				                	cit.add(c.getString(5));

				                 } while (c.moveToNext());				                 
				             }  			        			        
				        		db.close();      	 
				        		startActivity(SIntent);		        																					        		
					}																									
				}		
		
				else{
					
					if(!sp1.equals("Toronto Schools"))
					Toast.makeText(MainActivity.this,String.valueOf(s1.getSelectedItem()) + " " + "Will be implemented in the next version", Toast.LENGTH_LONG).show();
					if(!sp2.equals("Toronto"))
					Toast.makeText(MainActivity.this,String.valueOf(s2.getSelectedItem()) + " " + "Will be implemented in the next version", Toast.LENGTH_LONG).show();
					
				}					
			}
		});
		
    }
    
    public void CopyDB(InputStream inputStream, 
		    OutputStream outputStream) throws IOException {
		        //---copy 1K bytes at a time---
		        byte[] buffer = new byte[1024];
		        int length;
		        while ((length = inputStream.read(buffer)) > 0) {
		            outputStream.write(buffer, 0, length);
		        }
		        inputStream.close();
		        outputStream.close();
		    }		
	
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    

 // request for location updates (textbook, p. 381)
 		@Override
 		public void onResume(){
 			
 			super.onResume();
 			
 			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
 					                               0, 0, this);	
 		}
 		
 		// remove the listener for location updates (textbook, p. 382)
 		@Override
 		public void onPause(){
 				
 				super.onPause();
 				
 				locationManager.removeUpdates(this);	
 	    }
 		
 		
 		/* implementation of all the four  methods declared in LocationListner */
 		public void onLocationChanged(Location location) {
 			
 		    //latitude = Double.toString((double) (location.getLatitude()));
 		    //longitude = Double.toString((double) (location.getLongitude()));
 			
 			Toast.makeText(this, latitude + ' ' + longitude,
 				       Toast.LENGTH_SHORT).show();
 		}
 		
 		// monitor a change in the provider status (textbook, p. 384)
 		public void onStatusChanged(String provider, int status, Bundle extras) {
 			// TODO Auto-generated method stub ???
 		}

 		// invoked when the provider is enabled (textbook, p. 384, Vogel)
 		@Override
 		public void onProviderEnabled(String provider) {
 			Toast.makeText(this, "Enabled new provider " + provider,
 					       Toast.LENGTH_SHORT).show();

 		}

 		// invoked when the provider is disabled (textbook, p. 384, Vogel)
 		@Override
 		public void onProviderDisabled(String provider) {
 			Toast.makeText(this, "Disabled provider " + provider,
 					       Toast.LENGTH_SHORT).show();
 		}	
	
}
