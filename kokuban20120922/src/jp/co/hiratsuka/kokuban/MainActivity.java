package jp.co.hiratsuka.kokuban;

//import jp.Adlantis.Android.AdlantisView;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import jo.co.hiratsuka.kokuban.conf.CodeDefine;
import jp.co.cayto.appc.sdk.android.LinearFloatView;
import jp.co.hiratsuka.kokuban.utils.AndroidUtils;
import jp.co.hiratsuka.kokuban.view.KokubanView;

//import com.google.ads.AdRequest;
//import com.google.ads.AdSize;
//import com.google.ads.AdView;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;

public class MainActivity extends Activity {

	 /** Called when the activity is first created. */
		private static final String TAG = "KokubanActivity";
		public KokubanView kokuban_view;
		
		public LinearFloatView lView;
		
		private final String siteId = "2494";
		private final String locationId = "2861";
		private Uri mImageUri;
		private Intent imgIntent = null;
		private SharedPreferences sp;

//		private AdView mAdView;
//		private AdlantisView aView;
		
		private int dispwitdth;
	    private int dispheight;
	    
	    private Bitmap bitmap = null;
	    private Uri resultUri = null;
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState); 
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_main);
	        lView = (LinearFloatView)findViewById(R.id.floatview_1);	
//	        aView = (AdlantisView)findViewById(R.id.adView);
//
////	        AdMaker = (libAdMaker)findViewById(R.id.admakerview);
//	        //日本語圏じゃない場合は、ショット、シフトは表示しない
	        String language = feedLocale();
	        if(!"jp".equals(language)){
	        	lView.setVisibility(View.INVISIBLE);
//	        	AdMaker.setVisibility(View.INVISIBLE);
	        	RelativeLayout layout = (RelativeLayout)findViewById(R.id.top);
	              
	 
	            AdView adView = new AdView(this, AdSize.BANNER, "a14e347bef1960d");
	          
	            // Add the adView to it
	            layout.addView(adView);
	             
	            // Initiate a generic request to load it with an ad
	            AdRequest request = new AdRequest();
	            //request.setTesting(true);

	            adView.loadAd(request);         
	               
	        }else{
	        	lView.setVisibility(View.VISIBLE);
//	        	AdMaker.setVisibility(View.VISIBLE);
//	            startAdmamker();
	        }
//	        aView.setVisibility(View.INVISIBLE);
//	    	AdMaker.setVisibility(View.INVISIBLE);
	    	RelativeLayout layout = (RelativeLayout)findViewById(R.id.top);
	          

//	        AdView adView = new AdView(this, AdSize.BANNER, "a14e347bef1960d");
//	      
//	        // Add the adView to it
//	        layout.addView(adView);
//	         
//	        // Initiate a generic request to load it with an ad
//	        AdRequest request = new AdRequest();
//	        //request.setTesting(true);
	//
//	        adView.loadAd(request);
	        
	        
	      //ディスプレイ関連のオブジェクト
			 Display disp = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).
			 getDefaultDisplay();
			 
			 //幅
			 dispwitdth = disp.getWidth();
			 
			 //高さ
			 dispheight = disp.getHeight();
	        
	        kokuban_view = (KokubanView) findViewById(R.id.kokuban_view);
	        
	        Button elaseButton = (Button) this.findViewById(R.id.elaseButton);
	        Button whiteButton = (Button) this.findViewById(R.id.whiteButton);
	        Button redButton = (Button) this.findViewById(R.id.redButton);
	        Button blueButton = (Button) this.findViewById(R.id.blueButton);
	        Button yellowButton = (Button) this.findViewById(R.id.yellowButton);
	        Button greenButton = (Button) this.findViewById(R.id.greenButton);
	        
	        elaseButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	            	//kokuban_view.setElase();
	            	
	            	
	            	AlertDialog.Builder AlertDlgBldr = new AlertDialog.Builder(MainActivity.this);   
	                AlertDlgBldr.setTitle(getText(R.string.elase_mode).toString());  
	                AlertDlgBldr.setMessage(getText(R.string.select_elase).toString());  
	                AlertDlgBldr.setPositiveButton(getText(R.string.default_elase).toString(), new DialogInterface.OnClickListener() {  
	          
	                    @Override  
	                    public void onClick(DialogInterface dialog, int which) {  
	                    	kokuban_view.setDefault();
	                    }  
	                      
	                });
	                AlertDlgBldr.setNeutralButton(getText(R.string.all_elase).toString(), new DialogInterface.OnClickListener() {  
	                    
	                    @Override  
	                    public void onClick(DialogInterface dialog, int which) {  
//	                    	kokuban_view.setElase();
	                    	if(resultUri != null){



	                            // ビットマップ画像を取得
	                           try {

	                               String filePath = AndroidUtils.feedFilePath(MainActivity.this, resultUri.toString());

	                               //圧縮(端末によって、メモリーアウトで落ちることがあるため)
	                               //表示用
	                               bitmap = AndroidUtils.compressImage(filePath,dispwitdth,dispheight);
	                               bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
	                               kokuban_view.setAnotherBitmap(bitmap);

	                           } catch (Exception e) {
	                               e.printStackTrace();
	                               AndroidUtils.toastMessage(getApplicationContext(), getText(R.string.image_get_error).toString());

	                           }
	                       }else{
	                           kokuban_view.setElase();
	                       }
	                    }  
	                      
	                });
	                AlertDlgBldr.setNegativeButton(getText(R.string.yubi_elase).toString(), new DialogInterface.OnClickListener() {  
	          
	                    @Override  
	                    public void onClick(DialogInterface dialog, int which) {  
	                    	kokuban_view.setBlackColor();
	                    }  
	                      
	                });
	                  
	                AlertDialog AlertDlg = AlertDlgBldr.create();  
	                AlertDlg.show();
	                
	            }

				

	        });
	        
	        whiteButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Toast.makeText(MainActivity.this, getText(R.string.white).toString(), Toast.LENGTH_SHORT).show(); 
	            	kokuban_view.setWhiteColor();
	            }

	        });
	        redButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Toast.makeText(MainActivity.this, getText(R.string.red).toString(), Toast.LENGTH_SHORT).show(); 
	            	kokuban_view.setRedColor();
	            }

	        });
	        blueButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Toast.makeText(MainActivity.this, getText(R.string.blue).toString(), Toast.LENGTH_SHORT).show(); 
	            	kokuban_view.setBlueColor();
	            }

	        });
	        yellowButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Toast.makeText(MainActivity.this, getText(R.string.yellow).toString(), Toast.LENGTH_SHORT).show(); 
	            	kokuban_view.setYellowColor();
	            }

	        });
	        greenButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Toast.makeText(MainActivity.this, getText(R.string.green).toString(), Toast.LENGTH_SHORT).show(); 
	            	kokuban_view.setGreenColor();
	            }

	        });
	        
	        kokuban_view.setWhiteColor();
	        
	         
	    }
	    @Override
	    public boolean onCreateOptionsMenu(android.view.Menu menu) {
	    	super.onCreateOptionsMenu(menu);
	    	//メニューインフレーターを取得
	    	MenuInflater inflater = getMenuInflater();
	    	//xmlのリソースファイルを使用してメニューにアイテムを追加
	    	inflater.inflate(R.menu.activity_main, menu);
	    	//できたらtrueを返す
	    	return true;
	    }
	    
	    /** メニューがクリックされた時のイベント */  
	    @Override  
	    public boolean onOptionsItemSelected(MenuItem item) {  
		    switch ( item.getItemId() ) {  
		    	case R.id.item1:  
		    		handleGallery();  
		    		break;
			    case R.id.item2:  
			    	kokuban_view.saveToFile();  
				    break;  
			    case R.id.item3:  
				    finish();  
				    break;
			   
		    }  
		    return true;  
	    }  
	    
	    @Override
	    public void onResume() {
	    	
			super.onResume();
			
			//mmv.start();
	    }
	    
	    @Override
	    public void onDestroy() {
//	    	AdMaker.destroy(); 
//	    	AdMaker = null;
			super.onDestroy();
	    }

		@Override
		protected void onPause () {
			super.onPause();
//			AdMaker.stop();
			//mmv.stop();
				
		}
		
	    
		
		
		@Override
		public void onRestart() {
			super.onRestart();
			
//			AdMaker.start(); //復帰後に広告をリフレッシュします
		}
		
		private static String createName(long dateTaken) {  
	        return DateFormat.format("yyyy-MM-dd_kk.mm.ss", dateTaken).toString();  
		} 
	    /**
	     * 
	     */
	    @SuppressLint("NewApi")
		private void selectElaseMode() {
			// TODO Auto-generated method stub
			
		}
		
		/**
	     * 国を取得
	     * @return
	     */
	    protected String feedLocale(){
	        TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	        return telManager.getSimCountryIso();
	        //return "en";
	    }
//	    @SuppressLint("NewApi")
//		public void onFocusChange(View v, boolean hasFocus) {
//
//	        if(hasFocus == false){
//	            InputMethodManager imm =
//	                                     (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//	            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
//	        }
//
//	    }
		
	    
	    /**
	     * ギャラリーを扱う
	     * @access private
	     * @return void
	     */
	    @SuppressLint("NewApi")
		protected void handleGallery() {

	        imgIntent = new Intent();
	        imgIntent.setType("image/*");
	        imgIntent.setAction(Intent.ACTION_PICK);
	        startActivityForResult(imgIntent, CodeDefine.REQUEST_PICK_CONTACT);
	    }
	    
	    /**
	     * ギャラリーから戻ってきたときの処理
	     * @access protected
	     * @return void
	     */
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	        if(requestCode != CodeDefine.FACEBOOK_REQUEST_CODE){
	            super.onActivityResult(requestCode, resultCode, data);
	            boolean movieFlag = false;
	            
	            switch (requestCode) {
	                case CodeDefine.REQUEST_PICK_CONTACT:

	                    if(data != null){
	                        resultUri = data.getData();
	                    }
	                    break;
	                case CodeDefine.REQUEST_PICK_CONTACT_MOVIE:
	                    movieFlag = true;
	                    if(data != null){
	                        resultUri = data.getData();
	                    }
	                    break;
	                case CodeDefine.REQUEST_IMAGE_CAPTURED:
	                    sp = getSharedPreferences("picture",MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);

	                    //ここは端末によって、データっていうオブジェクトにファイルパスが入ってることがあるので、そのため
	                    if(data != null){
	                        resultUri = data.getData();

	                    }
	                    if(resultUri == null){
	                        String pictureUri = sp.getString("pictureUri", null);
	                        resultUri = Uri.parse(pictureUri);
	                    }
	                    break;
	                case CodeDefine.REQUEST_VIDEO_CAPTURED:
	                    movieFlag = true;
	                    if(data != null){
	                        resultUri = data.getData();
	                    }
	                    break;


	            }

	            if(resultUri != null){



	                 // ビットマップ画像を取得
	                try {

	                	


	                    //
	                    String filePath = AndroidUtils.feedFilePath(MainActivity.this, resultUri.toString());

	                    //圧縮(端末によって、メモリーアウトで落ちることがあるため)
	                    //表示用
	                    bitmap = AndroidUtils.compressImage(filePath,dispwitdth,dispheight);
	                    bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
	                    kokuban_view.setAnotherBitmap(bitmap);

	                } catch (Exception e) {
	                    e.printStackTrace();
	                    AndroidUtils.toastMessage(getApplicationContext(), getText(R.string.image_get_error).toString());

	                }
	            }else{
	                AndroidUtils.toastMessage(getApplicationContext(), getText(R.string.image_get_error).toString());
	            }
	        }
	    }

    
}
