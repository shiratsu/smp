package jp.co.hiratsuka.kokuban;




import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import jp.co.hiratsuka.kokuban.facebook.BaseRequestListener;
import jp.co.hiratsuka.kokuban.facebook.Utility;
import jp.co.hiratsuka.kokuban.utils.AndroidUtils;


import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;



public class ExternalTask extends AsyncTask<Object, Integer, Integer> {

	private String FACEBOOK_APP_ID = "229799000412269";
	private static final String twitter_consumer_key = "dpxJkpIHRjlXtYQRfStKLw";
	private static final String twitter_secret_key = "UUqpW23RwLYxRBSpcAqqNQFdUwAEqwf4d7oeztko6vY";
	private String oauth_access_token = "106246800-KCLbSdcJWhbCySqBlSOY1uu5f4ALD0DaQSXWdWdX";
	private String oauth_access_token_secret = "xoMiZ36ZytmpSbpUjcnyZf14hCArDdiGaAL28O5VGxg";
	
    
    private ProgressDialog mProgressDialog;

    private Context context;
    private int NOTFICATION_ID;
    private String urlPath;
    private Bitmap bitmap;
    private String url;
    

    public ExternalTask(Context context2) {

        this.context = context2;
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void onPreExecute() {
    	// バックグラウンドの処理前にUIスレッドでダイアログ表示
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage(context.getText(R.string.now_sending).toString());
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.show();
    }



    @Override
    protected Integer doInBackground(Object... params) {
        // TODO Auto-generated method stub
        int returnValue = 0;
        bitmap = (Bitmap) params[0];
        String twitterAccount = (String) params[1];
        String twitterPassword = (String) params[2];
        String twitterFlag = (String) params[3];
        String accessToken = (String) params[4];
        Long expires = (Long) params[5];
        
        String content = (String) params[6];
        String urlStr = (String) params[7];

//        if("1".equals(twitterFlag)){
//            prepareTwitter(twitterAccount, twitterPassword);
//            String filePath = AndroidUtils.feedFilePath(context,urlStr);
//            File fileObj = new File(filePath);
//            
//				try {
//					twitPicUpload(fileObj);
//					sendTweet(content);
//				} catch (TwitterException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					returnValue = 1;
//				}
//				
//			
//            
//        }
       
	        
	
	        
        Utility.mFacebook = new Facebook(FACEBOOK_APP_ID);
        if (accessToken != null && expires != -1)
        {
            Utility.mFacebook.setAccessToken(accessToken);
            Utility.mFacebook.setAccessExpires(expires);

        }
        
        if (Utility.mFacebook.isSessionValid()){
        	try {
        		Log.d("accessToken", accessToken);
				String response = Utility.mFacebook.request("me");
				Bundle parameters = new Bundle();
	            byte[] data = AndroidUtils.chngBmpToData(bitmap, Bitmap.CompressFormat.JPEG,80);
	        	parameters.putByteArray("photo",data);
	            parameters.putByteArray("caption", content.getBytes());
	            parameters.putByteArray(Facebook.TOKEN, accessToken.getBytes());
	            parameters.putByteArray("method", "photos.upload".getBytes());
	            parameters.putByteArray("message", "".getBytes());
	            parameters.putByteArray("format", null);
	            response = Utility.mFacebook.request("me/photos", parameters, "POST");
	            if (response == null || response.equals("") || 
                        response.equals("false")) {
                   Log.v("Error", "Blank response");
                }
//	            Utility.mAsyncRunner = new AsyncFacebookRunner(Utility.mFacebook);
//	            Utility.mAsyncRunner.request("me/photos", params, "POST", new WallPostRequestListener());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(returnValue == 0){
					returnValue = 2;
				}else{
					returnValue = 3;
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(returnValue == 0){
					returnValue = 2;
				}else{
					returnValue = 3;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(returnValue == 0){
					returnValue = 2;
				}else{
					returnValue = 3;
				}	
			}
            
        }
        

        return returnValue;
    }

    
    

 // メインスレッド上で実行される
	protected void onStopExecute() {
		mProgressDialog.dismiss();
//		Toast.makeText(context,
//				context.getText(R.string.sending_complete).toString(), Toast.LENGTH_SHORT)
//                .show();
	}

	// メインスレッド上で実行される
	@Override
	protected void onPostExecute(Integer id) {
		mProgressDialog.dismiss();
		switch (id) {
			case 0:
				Toast.makeText(context,
						context.getText(R.string.sending_complete).toString(), Toast.LENGTH_SHORT)
		                .show();
				break;
			case 1:
				Toast.makeText(context,
						context.getText(R.string.twitter_error).toString(), Toast.LENGTH_SHORT)
		                .show();
				break;
			case 2:
				Toast.makeText(context,
						context.getText(R.string.facebook_error).toString(), Toast.LENGTH_SHORT)
		                .show();
				break;
			case 3:
				Toast.makeText(context,
						context.getText(R.string.twitterfacebook_error).toString(), Toast.LENGTH_SHORT)
		                .show();
				break;	
			default:
				break;
		}
		
	}

	public class WallPostRequestListener extends BaseRequestListener {
		@Override
        public void onComplete(final String response) {
            Log.d("Facebook-Example", "Got response: " + response);
            String message = "<empty>";
            try {
                JSONObject json = Util.parseJson(response);
                message = json.getString("message");
            } catch (JSONException e) {
                Log.w("Facebook-Example", "JSON Error in response");
            } catch (FacebookError e) {
                Log.w("Facebook-Example", "Facebook Error: " + e.getMessage());
            }
            //final String text = "Your Wall Post: " + message;
            
        }

		
		public void onComplete(String response, Object state) {
			// TODO Auto-generated method stub
			
		}
    }
	
}
