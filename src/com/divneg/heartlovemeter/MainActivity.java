package com.divneg.heartlovemeter;

import java.io.File;

import com.divneg.heartlovemeter.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * An {@link Activity} that requests and can display an InterstitialAd.
 */
public class MainActivity extends Activity {
	public boolean heartbut = true;
  /** The log tag. */
  private static final String LOG_TAG = "InterstitialAdd";

  /** Your ad unit id. Replace with your actual ad unit id. */
  private static final String AD_UNIT_ID = "a153067f84203c3";

  /** The interstitial ad. */
  private InterstitialAd interstitialAd;

  /** The button that show the interstitial. */
  private Button showButton;
  public TextView title,desc,buttext,his,her; 
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    interstitialAd = new InterstitialAd(this);
    interstitialAd.setAdUnitId(AD_UNIT_ID);
   
    AdView adView = (AdView) this.findViewById(R.id.ad);
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);
    //showInterstitial();
    loadInterstitial();
    // Create an ad.
   
    Intent intent = getIntent();
  if(intent.getIntExtra("key", 500)!=500)
  {
	  TextView rate = (TextView) findViewById(R.id.rate);
	  rate.setText(String.valueOf(intent.getIntExtra("key", 100)));
	  showInterstitial();
  } 
    // Set the AdListener.
    interstitialAd.setAdListener(new AdListener() {
      @Override
      public void onAdLoaded() {
        Log.d(LOG_TAG, "onAdLoaded");
       // Toast.makeText(MainActivity.this, "onAdLoaded", Toast.LENGTH_SHORT).show();
        

       // showInterstitial();
        
      }

      @Override
      public void onAdFailedToLoad(int errorCode) {
        String message = String.format("onAdFailedToLoad (%s)", getErrorReason(errorCode));
        Log.d(LOG_TAG, message);
       // Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

        // Change the button text and disable the button.
      }
    });
    
    //animate the beats of sound 
    ImageView silent = (ImageView) findViewById(R.id.beats);
    RelativeLayout rl = (RelativeLayout) findViewById(R.id.meterbox);
    float w = rl.getWidth();
   // Toast.makeText(this,String.valueOf(w), Toast.LENGTH_LONG).show();
    TranslateAnimation animation_finger = new TranslateAnimation(0, 400,
    		0, 0);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
    animation_finger.setDuration(4000);  // animation duration 
    animation_finger.setRepeatCount(animation_finger.INFINITE);  // animation repeat count
    animation_finger.setRepeatMode(1);   // repeat animation (left to right, right to left )
    //animation.setFillAfter(true);      

    silent.startAnimation(animation_finger);  // start animation 
    
  }

  /** Called when the Load Interstitial button is clicked. */
  public void loadInterstitial() {
    // Disable the show button until the new ad is loaded.
  // Toast.makeText(this, "ok loading", Toast.LENGTH_LONG).show();
    // Check the logcat output for your hashed device ID to get test ads on a physical device.
    AdRequest adRequest = new AdRequest.Builder().build();

    // Load the interstitial ad.
    interstitialAd.loadAd(adRequest);
   
  }

  /** Called when the Show Interstitial button is clicked. */
  public void showInterstitial() {
    // Disable the show button until another interstitial is loaded

    if (interstitialAd.isLoaded()) {
      interstitialAd.show();
    } else {
      Log.d(LOG_TAG, "Interstitial ad was not ready to be shown.");
    }
  }

  /** Gets a string error reason from an error code. */
  private String getErrorReason(int errorCode) {
    
	  String errorReason = "";
    switch(errorCode) {
      case AdRequest.ERROR_CODE_INTERNAL_ERROR:
        errorReason = "Internal error";
        break;
      case AdRequest.ERROR_CODE_INVALID_REQUEST:
        errorReason = "Invalid request";
        break;
      case AdRequest.ERROR_CODE_NETWORK_ERROR:
        errorReason = "Network Error";
        break;
      case AdRequest.ERROR_CODE_NO_FILL:
        errorReason = "No fill";
        break;
    }
    return errorReason;
    }
  
  	public void ratebutton(View v)
  	{

  	RelativeLayout button = (RelativeLayout)findViewById(R.id.button);
  	ImageView onoff = (ImageView)findViewById(R.id.switches);
  	
  	button.setBackgroundColor(Color.WHITE);
  	onoff.setImageResource(R.drawable.switch_on);
  	heartbut = false;
  	Intent myIntent = new Intent(MainActivity.this, HeartRateMonitor.class);
  	//myIntent.putExtra("key", value); //Optional parameters
  	MainActivity.this.startActivity(myIntent);
  	finish();
  	}

  	@SuppressWarnings("deprecation")
	public void calculate(View v)
  
  	{
  		TextView tv = (TextView)findViewById(R.id.rate);
  		if(tv.getText().toString().equals("--"))
  		{
  			Toast.makeText(this, "Please Read your heartbeat first", Toast.LENGTH_SHORT).show();
  		}else
  		{
  		EditText his = (EditText)findViewById(R.id.edithis);
  		EditText her = (EditText)findViewById(R.id.edither);
  		
  		if(his.getText().toString().equals("") || his.getText().toString() == null || her.getText().toString().equals("") || her.getText().toString() == null )
  		{  	
  			Toast.makeText(this, "You need to fill the Heart beats",Toast.LENGTH_SHORT).show();
  		}else
  		{
  		int hisint = Integer.parseInt(his.getText().toString());
  		int herint = Integer.parseInt(her.getText().toString());
  		
  		float top = hisint>herint?hisint:herint;
  		float down = hisint>herint?herint:hisint;
  		
  		float percentage = (down/top)*100;
  		String love="";

  		if((int)percentage==100)
  		{
  			love = "100 % Love , You both are filled with emotions for each other right now , KISS NOW";
  		}
  		else if((int)percentage>90 && (int)percentage<100 )
  		{
  			love = "This moment is made for you, enjoy your love , This is the moment people talk about in movies";
  		}
  		else if((int)percentage>80 && (int)percentage<90 )
  		{
  			love = "Hold your hands tightly and close eyes , feel your emotions , A strong force of love between you two";
  		}
  		else if((int)percentage>70 && (int)percentage<80 )
  		{
  			love = "Love is in the music , make it better , solve your issues right now";
  		}
  		else if((int)percentage>60 && (int)percentage<70 )
  		{
  			love = "Ohh , I can feel the heat inside here , no fight today !";
  		}
  		else if((int)percentage<60 )
  		{
  			love = "Dont panic , things can be better, you can make things back to normal , you both need to talk right now";
  		}

  		showInterstitial();
  		 AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
         alertDialog.setTitle(String.valueOf((int)percentage)+"%");
         alertDialog.setMessage(love);
         
         alertDialog.setButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               // here you can add functions
            }
         });

         alertDialog.show();  //<-- See This!
  		}
  		}
  	}
  	
  	public void sharethis(View v)
  	{
  		Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/*");
        share.putExtra(Intent.EXTRA_TEXT, "Measure Your heart rate and love compatibility with your partner , Download this Android App here http://goo.gl/aqmbtc");
        startActivity(Intent.createChooser(share,"Share via"));
  	}
  	
  	 @Override
     public void onBackPressed() {
        super.onBackPressed();
        finish();
         if(HeartRateMonitor.heart!=null)
         {
        	 HeartRateMonitor.heart.finish();
         }
     }
}
