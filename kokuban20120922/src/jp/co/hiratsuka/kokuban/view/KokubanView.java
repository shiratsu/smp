package jp.co.hiratsuka.kokuban.view;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 黒板描画に使うクラス
 * @author shiratsu
 *
 */
public class KokubanView extends View {

	ArrayList<Point> draw_list = new ArrayList<Point>();  
 
	private Bitmap  mBitmap;
	
    private Canvas  mCanvas;
    
    /**
     * 描画に使うオブジェクト(http://dev.classmethod.jp/smartphone/android-tips-1-view/)
     */
    private Path    mPath;
    private Paint   mBitmapPaint;
    private int ALPHA;
	private int RED = 255;
	private int BLUE = 255;
	private int GREEN = 255;
	private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private Paint paint;
    private Activity _context;
    private int dispwitdth;
    private int dispheight;
	
	// main.xml使用時はこっちが呼ばれる
    /**
     * コンストラクタ（定義関連がまとめられる）
     */
	public KokubanView(Context context, AttributeSet attrs) {
		 super(context,attrs);
		 
		 //ディスプレイ関連のオブジェクト
		 Display disp = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).
		 getDefaultDisplay();
		 
		 //幅
		 dispwitdth = disp.getWidth();
		 
		 //高さ
		 dispheight = disp.getHeight();
		 
		 _context = (Activity)context;
		 
		 //描画領域初期化
		 mBitmap = Bitmap.createBitmap(dispwitdth, dispheight, Bitmap.Config.ARGB_8888);
		 mCanvas = new Canvas(mBitmap);
         mPath = new Path();
         mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	 }
	
	 public Bitmap getmBitmap() {
		return mBitmap;
	}

	public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}
	
	public void setAnotherBitmap(Bitmap mBitmap){
		
		this.mBitmap = mBitmap;
		mCanvas = new Canvas(this.mBitmap);
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        invalidate();
	}

	/**
	 * 保存処理（多分SDカードでギャラリーに保存のはず）
	 */
	public void saveToFile(){  
		 if(!sdcardWriteReady()){  
			 Toast.makeText(_context, "SDCARDが認識されません。", Toast.LENGTH_SHORT).show();  
			 return;  
		 }
		 

		//ギャラリーに保存
		 long dateTaken = System.currentTimeMillis();  
		 String name = createName(dateTaken) + ".jpg";  
		 String uriStr = MediaStore.Images.Media.insertImage(_context.getContentResolver(), mBitmap, name,  
                null);
		 if(uriStr != null){
			 Toast.makeText(_context, "保存されました。", Toast.LENGTH_SHORT).show(); 
		 }else{
			 Toast.makeText(_context, "保存に失敗しました", Toast.LENGTH_SHORT).show();
		 }
	 }
	
	/**
	 * 現在日時を取得
	 * @param dateTaken
	 * @return
	 */
	 private static String createName(long dateTaken) {  
	        return DateFormat.format("yyyy-MM-dd_kk.mm.ss", dateTaken).toString();  
	 } 
	
	 /**
	 * 描画する
	 */
	public void onDraw(Canvas canvas) {  


		paint = new Paint(Paint.DITHER_FLAG);  
		paint.setColor(Color.argb(ALPHA,RED, GREEN, BLUE));  
		//グラフィックへのアンチエイリアス（ピクセルのギザギザを滑らかに。）
		paint.setAntiAlias(true);  
		paint.setStyle(Paint.Style.STROKE);  
		
		//太さ
		paint.setStrokeWidth(6);  
		
		//
		paint.setStrokeCap(Paint.Cap.ROUND);  
		paint.setStrokeJoin(Paint.Join.ROUND);
		
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

        canvas.drawPath(mPath, paint);
      
        
	}

	/**
	 * 画面にタッチした際に呼ばれる
	 * @param x
	 * @param y
	 */
	private void touch_start(float x, float y) {
		//初期化
		mPath.reset();
        
		//moveTo()メソッドでパスの開始地点を決め、lineTo()メソッドで描画するポイントを追加していきます。
		mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
	
	
	/**
	 * 指が動いてる間の処理
	 * http://androside.com/page_contents/page_android_fingerDrawOverlay.html
	 * http://sunfl0w3r.blog77.fc2.com/blog-entry-53.html
	 * @param x
	 * @param y
	 */
    private void touch_move(float x, float y) {
        
    	//この辺は定型文みたいなもん。こう書けばいいと覚えとく
    	float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    
    /**
     * 指が離れたら呼ばれる
     */
    private void touch_up() {
        
    	//moveTo()メソッドでパスの開始地点を決め、lineTo()メソッドで描画するポイントを追加していきます。
    	mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, paint);
        // kill this so we don't double draw
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            //画面に指が触れたら
        	case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            //画面に指で描いたら
        	case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            //画面から指が離れたら
        	case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }
    

    /**
     * 白
     */
	public void setWhiteColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 255;
		BLUE = 255;
		GREEN = 255;
	}

	/**
	 * 赤
	 */
	public void setRedColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 246;
		BLUE = 171;
		GREEN = 171;
	}

	/**
	 * 青
	 */
	public void setBlueColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 171;
		BLUE = 244;
		GREEN = 177;
	}

	/**
	 * 黄色
	 */
	public void setYellowColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 238;
		BLUE = 174;
		GREEN = 241;
	}

	/**
	 * 初期化
	 */
	public void setDefault() {
		
		
		// TODO Auto-generated method stub
		mBitmap = Bitmap.createBitmap(dispwitdth, dispheight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        invalidate();
		
	}
	
	/**
	 * 消しゴム
	 */
	public void setElase() {
		 
		
		// TODO Auto-generated method stub
		mBitmap = Bitmap.createBitmap(dispwitdth, dispheight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        invalidate();
		
	}
	
	
	private boolean sdcardWriteReady(){  
		 String state = Environment.getExternalStorageState();  
		 return (Environment.MEDIA_MOUNTED.equals(state));  
	}

	public void setBlackColor() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 20;
		BLUE = 20;
		GREEN = 20;
	}

	/**
	 * 緑
	 */
	public void setGreenColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 171;
		BLUE = 177;
		GREEN = 244;
	} 
	
	
}
