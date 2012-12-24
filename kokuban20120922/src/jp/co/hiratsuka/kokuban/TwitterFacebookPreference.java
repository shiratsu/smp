package jp.co.hiratsuka.kokuban;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import jp.co.hiratsuka.kokuban.facebook.BaseRequestListener;
import jp.co.hiratsuka.kokuban.facebook.SessionStore;
import jp.co.hiratsuka.kokuban.facebook.Utility;


import org.json.JSONException;
import org.json.JSONObject;






import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;

import com.facebook.android.R;
import com.facebook.android.Facebook.DialogListener;

import com.facebook.android.FacebookError;
//import com.google.ads.AdRequest;
//import com.google.ads.AdSize;
//import com.google.ads.AdView;

public class TwitterFacebookPreference extends Activity implements DialogListener{


    private Button topButton;
    private Button faceBookButton;;


    private static final String[] permissions = {"publish_stream","photo_upload","offline_access"};

    private String twitterFlag;
    private String twitterId;
    private String twitterPassword;

    private CheckBox twitterPreference;

    private EditText twitterIdText;
    private EditText twitterPassText;
    private Handler mHandler;
    private TextView mText;
    private ImageView mUserPic;
    
    private String FACEBOOK_APP_ID = "229799000412269";
    private static final String twitter_consumer_key = "ycwLejracx4aChmINat1Q";
	private static final String twitter_secret_key = "T7sBuUJzAM5SRXtajiqzA495hAmNLhzJGCHWZs78";

//	private AdView mAdView;
//	private AdlantisView aView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.twitter_facebook_preference);
        
//        aView = (AdlantisView)findViewById(R.id.adView);
//
//
//        String language = feedLocale();
//        if(!"jp".equals(language)){
//        	aView.setVisibility(View.INVISIBLE);
// 
//        	RelativeLayout layout = (RelativeLayout)findViewById(R.id.top);
//              
// 
//            AdView adView = new AdView(this, AdSize.BANNER, "a14e347bef1960d");
//          
//            // Add the adView to it
//            layout.addView(adView);
//             
//            // Initiate a generic request to load it with an ad
//            AdRequest request = new AdRequest();
//            //request.setTesting(true);
//
//            adView.loadAd(request);         
//               
//        }else{
//        	aView.setVisibility(View.VISIBLE);
////        	AdMaker.setVisibility(View.VISIBLE);
////          startAdmamker();
//        }
        
//        twitterPreference = (CheckBox) findViewById(R.id.twitterPreference);
//
//        twitterIdText = (EditText) this.findViewById(R.id.twitterIdText);
//        twitterPassText = (EditText) this.findViewById(R.id.twitterPassword);
        mText = (TextView) this.findViewById(R.id.txt);
        mUserPic = (ImageView)this.findViewById(R.id.user_pic);

        

        String accessToken = null;
        Long expires;

        mHandler = new Handler();
        Utility.mFacebook = new Facebook(FACEBOOK_APP_ID);

        SharedPreferences sp= getSharedPreferences("externalPreference",MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);
        accessToken = sp.getString("accessToken", null);
        expires = sp.getLong("accessExpires", -1);


        if (accessToken != null && expires != -1)
        {
            Utility.mFacebook.setAccessToken(accessToken);
            Utility.mFacebook.setAccessExpires(expires);

        }


        Utility.mAsyncRunner = new AsyncFacebookRunner(Utility.mFacebook);
        faceBookButton = (Button) findViewById(R.id.facebookLogin);

             
        if (Utility.mFacebook.isSessionValid()){
            requestUserData();
            faceBookButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.logout_button));
        }else{
            faceBookButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_button));
        }


        faceBookButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.mFacebook.isSessionValid()){

                    logout();
                }else{
                    login();

                }
            }
        });

    }

    protected void login() {
        // TODO Auto-generated method stub
        Utility.mFacebook.authorize(this, permissions, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Utility.mFacebook.authorizeCallback(requestCode, resultCode, data);
        
        
    }

    protected void logout() {
        // TODO Auto-generated method stub
        try {
            Utility.mFacebook.logout(this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        refreshButtonLabel();
    }

    private void refreshButtonLabel() {
        // TODO Auto-generated method stub
        faceBookButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_button));
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        
//        AdMaker.destroy(); 
//    	AdMaker = null;
        
        super.onDestroy();
    }


    

    @Override
    public void onComplete(Bundle values) {
        // TODO Auto-generated method stub

        //繝茨ｿｽ?繧ｯ繝ｳ繧剃ｿ晢ｿｽ?        
    	SessionStore.save(Utility.mFacebook, TwitterFacebookPreference.this);
        String accessToken = Utility.mFacebook.getAccessToken();
        long accessExpires = Utility.mFacebook.getAccessExpires();

        SharedPreferences sp= getSharedPreferences("externalPreference",MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);

        Editor e = sp.edit();
        e.putString("accessToken", accessToken);
        e.putLong("accessExpires", accessExpires);
        e.commit();
        requestUserData();
        faceBookButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.logout_button));
    }

    @Override
    public void onFacebookError(FacebookError e) {
        // TODO Auto-generated method stub
        Log.d("error","loginerror");
    }

    @Override
    public void onError(DialogError e) {
        // TODO Auto-generated method stub
        Log.d("error","loginerror");
    }

    @Override
    public void onCancel() {
        // TODO Auto-generated method stub
        Log.d("error","loginerror");
    }

    /*
     * Callback for fetching current user's name, picture, uid.
     */
    public class UserRequestListener extends BaseRequestListener {

        public void onComplete(final String response, final Object state) {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);

                final String picURL = jsonObject.getString("picture");
                final String name = jsonObject.getString("name");
                Utility.userUID = jsonObject.getString("id");

                mHandler.post(new Runnable() {
                    public void run() {
                        mText.setText("Welcome " + name + "!");
                        mUserPic.setImageBitmap(Utility.getBitmap(picURL));
                    }
                });

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void onFacebookError(FacebookError e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onFileNotFoundException(FileNotFoundException e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onIOException(IOException e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onMalformedURLException(MalformedURLException e) {
            // TODO Auto-generated method stub

        }

    }
    /*
     * Request user name, and picture to show on the main screen.
     */
    public void requestUserData() {
        mText.setText("Fetching user name, profile pic...");
        Bundle params = new Bundle();
           params.putString("fields", "name, picture");
        Utility.mAsyncRunner.request("me", params, new UserRequestListener());
    }

    /**
     * �ｽ�ｽ�ｽ�ｽ�ｽ謫ｾ
     * @return
     */
    protected String feedLocale(){
        TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return telManager.getSimCountryIso();
        //return "en";
    }
    
    

	@Override
	protected void onPause () {
		super.onPause();
//		AdMaker.stop();
		//mmv.stop();
			
	}
	
    

	
	@Override
	public void onRestart() {
		super.onRestart();
		

	}


}
