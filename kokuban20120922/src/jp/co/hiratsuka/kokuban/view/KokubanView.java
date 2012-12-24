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
 * ���`��Ɏg���N���X
 * @author shiratsu
 *
 */
public class KokubanView extends View {

	ArrayList<Point> draw_list = new ArrayList<Point>();  
 
	private Bitmap  mBitmap;
	
    private Canvas  mCanvas;
    
    /**
     * �`��Ɏg���I�u�W�F�N�g(http://dev.classmethod.jp/smartphone/android-tips-1-view/)
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
	
	// main.xml�g�p���͂��������Ă΂��
    /**
     * �R���X�g���N�^�i��`�֘A���܂Ƃ߂���j
     */
	public KokubanView(Context context, AttributeSet attrs) {
		 super(context,attrs);
		 
		 //�f�B�X�v���C�֘A�̃I�u�W�F�N�g
		 Display disp = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).
		 getDefaultDisplay();
		 
		 //��
		 dispwitdth = disp.getWidth();
		 
		 //����
		 dispheight = disp.getHeight();
		 
		 _context = (Activity)context;
		 
		 //�`��̈揉����
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
	 * �ۑ������i����SD�J�[�h�ŃM�������[�ɕۑ��̂͂��j
	 */
	public void saveToFile(){  
		 if(!sdcardWriteReady()){  
			 Toast.makeText(_context, "SDCARD���F������܂���B", Toast.LENGTH_SHORT).show();  
			 return;  
		 }
		 

		//�M�������[�ɕۑ�
		 long dateTaken = System.currentTimeMillis();  
		 String name = createName(dateTaken) + ".jpg";  
		 String uriStr = MediaStore.Images.Media.insertImage(_context.getContentResolver(), mBitmap, name,  
                null);
		 if(uriStr != null){
			 Toast.makeText(_context, "�ۑ�����܂����B", Toast.LENGTH_SHORT).show(); 
		 }else{
			 Toast.makeText(_context, "�ۑ��Ɏ��s���܂���", Toast.LENGTH_SHORT).show();
		 }
	 }
	
	/**
	 * ���ݓ������擾
	 * @param dateTaken
	 * @return
	 */
	 private static String createName(long dateTaken) {  
	        return DateFormat.format("yyyy-MM-dd_kk.mm.ss", dateTaken).toString();  
	 } 
	
	 /**
	 * �`�悷��
	 */
	public void onDraw(Canvas canvas) {  


		paint = new Paint(Paint.DITHER_FLAG);  
		paint.setColor(Color.argb(ALPHA,RED, GREEN, BLUE));  
		//�O���t�B�b�N�ւ̃A���`�G�C���A�X�i�s�N�Z���̃M�U�M�U�����炩�ɁB�j
		paint.setAntiAlias(true);  
		paint.setStyle(Paint.Style.STROKE);  
		
		//����
		paint.setStrokeWidth(6);  
		
		//
		paint.setStrokeCap(Paint.Cap.ROUND);  
		paint.setStrokeJoin(Paint.Join.ROUND);
		
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

        canvas.drawPath(mPath, paint);
      
        
	}

	/**
	 * ��ʂɃ^�b�`�����ۂɌĂ΂��
	 * @param x
	 * @param y
	 */
	private void touch_start(float x, float y) {
		//������
		mPath.reset();
        
		//moveTo()���\�b�h�Ńp�X�̊J�n�n�_�����߁AlineTo()���\�b�h�ŕ`�悷��|�C���g��ǉ����Ă����܂��B
		mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
	
	
	/**
	 * �w�������Ă�Ԃ̏���
	 * http://androside.com/page_contents/page_android_fingerDrawOverlay.html
	 * http://sunfl0w3r.blog77.fc2.com/blog-entry-53.html
	 * @param x
	 * @param y
	 */
    private void touch_move(float x, float y) {
        
    	//���̕ӂ͒�^���݂����Ȃ���B���������΂����Ɗo���Ƃ�
    	float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    
    /**
     * �w�����ꂽ��Ă΂��
     */
    private void touch_up() {
        
    	//moveTo()���\�b�h�Ńp�X�̊J�n�n�_�����߁AlineTo()���\�b�h�ŕ`�悷��|�C���g��ǉ����Ă����܂��B
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
            //��ʂɎw���G�ꂽ��
        	case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            //��ʂɎw�ŕ`������
        	case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            //��ʂ���w�����ꂽ��
        	case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }
    

    /**
     * ��
     */
	public void setWhiteColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 255;
		BLUE = 255;
		GREEN = 255;
	}

	/**
	 * ��
	 */
	public void setRedColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 246;
		BLUE = 171;
		GREEN = 171;
	}

	/**
	 * ��
	 */
	public void setBlueColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 171;
		BLUE = 244;
		GREEN = 177;
	}

	/**
	 * ���F
	 */
	public void setYellowColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 238;
		BLUE = 174;
		GREEN = 241;
	}

	/**
	 * ������
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
	 * �����S��
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
	 * ��
	 */
	public void setGreenColor() {
		// TODO Auto-generated method stub
		ALPHA = 255;
		RED = 171;
		BLUE = 177;
		GREEN = 244;
	} 
	
	
}
