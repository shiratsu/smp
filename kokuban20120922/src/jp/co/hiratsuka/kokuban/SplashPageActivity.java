package jp.co.hiratsuka.kokuban;


import java.util.Random;

import jp.co.hiratsuka.kokuban.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashPageActivity extends Activity {
	
	private ImageView splashImage;
	
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
	
		splashImage = (ImageView)this.findViewById(R.id.splash_image);
		splashImage.setImageResource(R.drawable.default0);
        
		
		Handler hdl = new Handler();
		hdl.postDelayed(new splashHandler(), 3000);
		/*
		WindowManager wm = (WindowManager)getSystemService(this.WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		int width = disp.getWidth();
		int height = disp.getHeight();
		
		//送信失敗という旨のメッセージを表示
		
		Toast.makeText(SplashPageActivity.this,
                "width:"+width+",height:"+height, Toast.LENGTH_SHORT)
                .show();
		*/
	}
	class splashHandler implements Runnable {
		public void run() {
			Intent i = new Intent(getApplication(), MainActivity.class);
			startActivity(i);
			SplashPageActivity.this.finish();
		}
	}
}
