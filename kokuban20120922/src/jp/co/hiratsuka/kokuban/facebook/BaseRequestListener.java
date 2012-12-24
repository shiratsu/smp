package jp.co.hiratsuka.kokuban.facebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.util.Log;

import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookError;

/**
 * Skeleton base class for RequestListeners, providing default error
 * handling. Applications should handle these error conditions.
 *
 */
public abstract class BaseRequestListener implements RequestListener {

    public void onFacebookError(FacebookError e, final Object state) {
        Log.e("Facebook", e.getMessage());
        e.printStackTrace();
    }

    public void onFileNotFoundException(FileNotFoundException e,
                                        final Object state) {
        Log.e("Facebook", e.getMessage());
        e.printStackTrace();
    }

    public void onIOException(IOException e, final Object state) {
        Log.e("Facebook", e.getMessage());
        e.printStackTrace();
    }

    public void onMalformedURLException(MalformedURLException e,
                                        final Object state) {
        Log.e("Facebook", e.getMessage());
        e.printStackTrace();
    }

	public void onFacebookError(FacebookError e) {
		// TODO Auto-generated method stub
		
	}

	public void onFileNotFoundException(FileNotFoundException e) {
		// TODO Auto-generated method stub
		
	}

	public void onIOException(IOException e) {
		// TODO Auto-generated method stub
		
	}

	public void onMalformedURLException(MalformedURLException e) {
		// TODO Auto-generated method stub
		
	}

	public void onComplete(String response) {
		// TODO Auto-generated method stub
		
	}

}
