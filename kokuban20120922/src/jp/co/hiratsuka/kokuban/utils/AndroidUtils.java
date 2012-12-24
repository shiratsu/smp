package jp.co.hiratsuka.kokuban.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.widget.Toast;

public class AndroidUtils {


    /**
     * ファイルからビットマップ
     * @param path
     * @param maxW
     * @param maxH
     * @accss private
     * @return
     */
    public static Bitmap file2bmp(String path,int maxW,int maxH) {
        BitmapFactory.Options options;
        InputStream in=null;
        try {
            //           
        	options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            in=new FileInputStream(path);
            BitmapFactory.decodeStream(in,null,options);
            in.close();
            int scaleW=options.outWidth/maxW+1;
            int scaleH=options.outHeight/maxH+1;
            int scale =Math.max(scaleW,scaleH);

            //逕ｻ蜒上?隱ｭ縺ｿ霎ｼ縺ｿ
            options=new BitmapFactory.Options();
            options.inJustDecodeBounds=false;
            options.inSampleSize=scale;
            in=new FileInputStream(path);
            Bitmap bmp=BitmapFactory.decodeStream(in,null,options);
            in.close();
            return bmp;
        } catch (Exception e) {
            try {
                if (in!=null) in.close();
            } catch (Exception e2) {
            }
            return null;
        }
    }

    /**
     * データからファイルへ
     * @param data
     * @param path
     * @access public
     * @return void
     */
    public static void data2file(byte[] data, String path)  throws Exception {
        FileOutputStream out=null;
        try {
            out=new FileOutputStream(path);
            out.write(data);
            out.close();
        } catch (Exception e) {
            if (out!=null) out.close();
            throw e;
        }
        // TODO Auto-generated method stub

    }

    /**
     * Bitmap to Data
     * @param src      Bitmap
     * @param format   Bitmap.CompressFormat
     * @param quality   int
     * @return         byte[]
     */
    public static byte[] chngBmpToData(Bitmap src, Bitmap.CompressFormat format, int quality) {
       ByteArrayOutputStream output = new ByteArrayOutputStream();
       src.compress(format, quality, output);
       return output.toByteArray();

    }
    /**
     * 保存 
     * @param data   byte[]   逕ｻ蜒上ョ繝ｼ繧ｿ
     * @param dataName   String   菫晏ｭ倥ヱ繧ｹ
     * @return   boolean
     * @throws Exception
     */
    public static void saveDataToStorage(byte[] data, String dataName) throws Exception {
       FileOutputStream fileOutputStream = null;
       try {
          // 謖?ｮ壻ｿ晏ｭ伜?縺ｫ菫晏ｭ倥☆繧?          
    	   fileOutputStream = new FileOutputStream(dataName);
          fileOutputStream.write(data);
       } catch (Exception e) {
       } finally {
          if (fileOutputStream != null) {
             fileOutputStream.close();
             fileOutputStream = null;
          }
       }
    }

    /**
     * リサイズ
     */
    public static Bitmap resizeBitMap(Bitmap baseBmp, int w,
            int i) {
        // TODO Auto-generated method stub
        int width = baseBmp.getWidth();
        int height = baseBmp.getHeight();
        int newWidth = w;

        //蟷?ｒ蝓ｺ貅悶↓繝ｪ繧ｵ繧､繧ｺ
        // calculate the scale - in this case = 0.4f
        float scale = ((float) newWidth) / width;

        // createa matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scale, scale);


        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(baseBmp, 0, 0,
                          width, height, matrix, true);
        return resizedBitmap;
    }


    /**
     * Toastを表示
     * @param string
     */
    public static void toastMessage(Context context,String string) {
        // TODO Auto-generated method stub
        Toast.makeText(context,
                string, Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * アラートを表示
     * @param errorTitle
     * @param errorStr
     * @param context
     */
    public static void showAlert(Context context,String title, String str) {
        // TODO Auto-generated method stub
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // 繧ｿ繧､繝医Ν繧定ｨｭ螳?        alertDialogBuilder.setTitle(title);
        // 繝｡繝?そ繝ｼ繧ｸ繧定ｨｭ螳?        alertDialogBuilder.setMessage(str);
        // 繧｢繧､繧ｳ繝ｳ繧定ｨｭ螳?        alertDialogBuilder.setIcon(R.drawable.icon72);
        // Positive繝懊ち繝ｳ縺ｨ繝ｪ繧ｹ繝翫ｒ險ｭ螳?
        alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        // 繧｢繝ｩ繝ｼ繝医ム繧､繧｢繝ｭ繧ｰ繧定｡ｨ遉ｺ縺励∪縺?        
        alertDialog.show();
    }


    /**
     * 現在日時を返す  
     * * @return
     */
    public static List<String> feedNowDate() {
        Date date1 = new Date();  //(1)Date繧ｪ繝悶ず繧ｧ繧ｯ繝医ｒ逕滓?
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf3 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf4 = new SimpleDateFormat("dd");

        String yyyymmdd = sdf1.format(date1);
        String yyyy = sdf2.format(date1);
        String mm = sdf3.format(date1);
        String dd = sdf4.format(date1);

        List<String> dateAry = new ArrayList<String>();
        dateAry.add(yyyymmdd);
        dateAry.add(yyyy);
        dateAry.add(mm);
        dateAry.add(dd);
        return dateAry;
    }

    /**
     * 逕ｻ蜒上ｒ蝨ｧ邵ｮ(OutOfMemory蟇ｾ遲?
     * @param defaultimageheight
     * @param defaultimagewidth
     * @param string
     * @return
     */
    public static Bitmap compressImage(String path, int defaultimagewidth, int defaultimageheight) {
        // TODO Auto-generated method stub
        //隱ｭ縺ｿ霎ｼ縺ｿ逕ｨ縺ｮ繧ｪ繝励す繝ｧ繝ｳ繧ｪ繝悶ず繧ｧ繧ｯ繝医ｒ逕滓?

        BitmapFactory.Options options = new BitmapFactory.Options();
        
        options.inJustDecodeBounds = true;
        
        BitmapFactory.decodeFile(path, options);

        
        int scaleW = 0;
        int scaleH = 0;

        if(defaultimagewidth > 0 && defaultimageheight > 0){
            scaleW = options.outWidth / defaultimagewidth + 1;
            scaleH = options.outHeight / defaultimageheight + 1;
        }else{
            scaleW = 1;
            scaleH = 1;
        }

        
        int scale = Math.max(scaleW, scaleH);

              
        options.inJustDecodeBounds = false;
        
        options.inSampleSize = scale;

        
        Bitmap image = BitmapFactory.decodeFile(path, options);

        return image;

    }

    /**
     * outof memory対策
     * @param defaultimageheight
     * @param defaultimagewidth
     * @param string
     * @return
     * @throws FileNotFoundException
     */
    public static Bitmap compressImageForList(File f) throws FileNotFoundException {
        // TODO Auto-generated method stub
        //隱ｭ縺ｿ霎ｼ縺ｿ逕ｨ縺ｮ繧ｪ繝励す繝ｧ繝ｳ繧ｪ繝悶ず繧ｧ繧ｯ繝医ｒ逕滓?

        BitmapFactory.Options options = new BitmapFactory.Options();
        //縺薙?蛟､繧稚rue縺ｫ縺吶ｋ縺ｨ螳滄圀縺ｫ縺ｯ逕ｻ蜒上ｒ隱ｭ縺ｿ霎ｼ縺ｾ縺壹?
        //逕ｻ蜒上?繧ｵ繧､繧ｺ諠??縺?￠繧貞叙蠕励☆繧九％縺ｨ縺後〒縺阪∪縺吶?
        options.inJustDecodeBounds = true;
        //逕ｻ蜒上ヵ繧｡繧､繝ｫ隱ｭ縺ｿ霎ｼ縺ｿ
        //縺薙％縺ｧ縺ｯ荳願ｨ倥?繧ｪ繝励す繝ｧ繝ｳ縺荊rue縺ｮ縺溘ａ螳滄圀縺ｮ
        //逕ｻ蜒上?隱ｭ縺ｿ霎ｼ縺ｾ繧後↑縺?〒縺吶?
        BitmapFactory.decodeStream(new FileInputStream(f), null, options);

        //隱ｭ縺ｿ霎ｼ繧薙□繧ｵ繧､繧ｺ縺ｯoptions.outWidth縺ｨoptions.outHeight縺ｫ
        //譬ｼ邏阪＆繧後ｋ縺ｮ縺ｧ縲√◎縺ｮ蛟､縺九ｉ隱ｭ縺ｿ霎ｼ繧?圀縺ｮ邵ｮ蟆ｺ繧定ｨ育ｮ励＠縺ｾ縺吶?
        //縺薙?繧ｵ繝ｳ繝励Ν縺ｧ縺ｯ縺ｩ繧薙↑螟ｧ縺阪＆縺ｮ逕ｻ蜒上〒繧?VGA縺ｫ蜿弱∪繧九し繧､繧ｺ繧?        //險育ｮ励＠縺ｦ縺?∪縺吶?
        int scaleW = 2;
        int scaleH = 2;



        //邵ｮ蟆ｺ縺ｯ謨ｴ謨ｰ蛟､縺ｧ縲?縺ｪ繧臥判蜒上?邵ｦ讓ｪ縺ｮ繝斐け繧ｻ繝ｫ謨ｰ繧?/2縺ｫ縺励◆繧ｵ繧､繧ｺ縲?        //3縺ｪ繧?/3縺ｫ縺励◆繧ｵ繧､繧ｺ縺ｧ隱ｭ縺ｿ霎ｼ縺ｾ繧後∪縺吶?
        int scale = Math.max(scaleW, scaleH);

        //莉雁ｺｦ縺ｯ逕ｻ蜒上ｒ隱ｭ縺ｿ霎ｼ縺ｿ縺溘＞縺ｮ縺ｧfalse繧呈欠螳?        
        options.inJustDecodeBounds = false;

        //蜈育ｨ玖ｨ育ｮ励＠縺溽ｸｮ蟆ｺ蛟､繧呈欠螳?       
        options.inSampleSize = scale;

        //縺薙ｌ縺ｧ謖?ｮ壹＠縺溽ｸｮ蟆ｺ縺ｧ逕ｻ蜒上ｒ隱ｭ縺ｿ霎ｼ繧√∪縺吶?
        //繧ゅ■繧阪ｓ螳ｹ驥上ｂ蟆上＆縺上↑繧九?縺ｧ謇ｱ縺?ｄ縺吶＞縺ｧ縺吶?
        Bitmap image = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

        return image;

    }

//    /**
//     * Exif諠??繧堤｢ｺ隱阪＠縺ｦ縲∝屓霆｢縺悟ｿ?ｦ√↑繧牙屓霆｢
//     * @param bitmap
//     * @param string
//     * @return
//     * @throws IOException
//     */
//    public static Bitmap checkImgOrientation(Bitmap bitmap, String path) throws IOException {
//        //exif諠??縺九ｉ蜀咏悄諠??繧貞叙蠕?        ExifInterface exifInterface = new ExifInterface(path);
//
//        String orientationStr = getExifString(exifInterface,ExifInterface.TAG_ORIENTATION);
//        int orientation = Integer.valueOf(orientationStr);
//        Matrix matrix = new Matrix();
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        switch (orientation) {
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                // createa matrix for the manipulation
//                matrix.postRotate(90);
//                //逕ｻ蜒上?繧ｵ繧､繧ｺ繧貞叙蠕?
//
//                // recreate the new Bitmap
//                bitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                                  width, height, matrix, true);
//                break;
//
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                // createa matrix for the manipulation
//
//                matrix.postRotate(180);
//                //逕ｻ蜒上?繧ｵ繧､繧ｺ繧貞叙蠕?
//                // recreate the new Bitmap
//                bitmap = Bitmap.createBitmap(bitmap, 0, 0,
//                                  width, height, matrix, true);
//                break;
//
//            default:
//                break;
//        }
//
//
//
//        return bitmap;
//    }



//    /**
//     * exif諠??縺九ｉ繝??繧ｿ繧貞叙蠕?     * @param exifInterface
//     * @param tagDatetime
//     * @return
//     */
//    public static String getExifString(ExifInterface ei, String tag) {
//        // TODO Auto-generated method stub
//        return ei.getAttribute(tag);
//    }

    /**
     * 繝輔ぃ繧､繝ｫ繝代せ繧貞叙蠕?     * @param string
     * @return
     */
    public static String feedFilePath(Context context,String string) {
        // TODO Auto-generated method stub
        ContentResolver cr = context.getContentResolver();
        String[] columns = {MediaStore.Images.Media.DATA };
        Cursor c = cr.query(Uri.parse(string), columns, null, null, null);
        c.moveToFirst();
        return c.getString(0);
    }

    /**
     * 繝輔ぃ繧､繝ｫ蜷阪ｒ菴懈?
     * @param dateTaken
     * @return
     */
    public static String createName(long dateTaken) {
        return DateFormat.format("yyyy-MM-dd_kk.mm.ss", dateTaken).toString();
    }

    public static String feedNowDateTime() {
        // TODO Auto-generated method stub
        Date date1 = new Date();  //(1)Date繧ｪ繝悶ず繧ｧ繧ｯ繝医ｒ逕滓?
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf1.format(date1);
    }

//    /**
//     * sha256縺ｮ證怜捷蛹悶く繝ｼ繧定ｿ斐☆
//     * @param password
//     * @param passwordSalt
//     * @return
//     */
//    public static String convertSha256(String password, String passwordSalt) {
//        // TODO Auto-generated method stub
//        return DigestUtils.sha256Hex(password+passwordSalt);
//    }

    /**
     * InputStream繧偵ヰ繧､繝磯?蛻励↓螟画鋤縺吶ｋ
     *
     * @param is
     * @return 繝舌う繝磯?蛻?     */
    public static byte[] getBytes(InputStream is) {
        // byte蝙九?驟榊?繧貞?蜉帛?縺ｨ縺吶ｋ繧ｯ繝ｩ繧ｹ縲?        // 騾壼ｸｸ縲√ヰ繧､繝亥?蜉帙せ繝医Μ繝ｼ繝??繝輔ぃ繧､繝ｫ繧?た繧ｱ繝?ヨ繧貞?蜉帛?縺ｨ縺吶ｋ縺後?
        // ByteArrayOutputStream繧ｯ繝ｩ繧ｹ縺ｯbyte[]螟画焚縲√▽縺ｾ繧翫Γ繝｢繝ｪ繧貞?蜉帛?縺ｨ縺吶ｋ縲?        
    	ByteArrayOutputStream b = new ByteArrayOutputStream();
        OutputStream os = new BufferedOutputStream(b);
        int c;
        try {
            while ((c = is.read()) != -1) {
                os.write(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 譖ｸ縺崎ｾｼ縺ｿ蜈医?ByteArrayOutputStream繧ｯ繝ｩ繧ｹ蜀?Κ縺ｨ縺ｪ繧九?
        // 縺薙?譖ｸ縺崎ｾｼ縺ｾ繧後◆繝舌う繝医ョ繝ｼ繧ｿ繧鍛yte蝙矩?蛻励→縺励※蜿悶ｊ蜃ｺ縺吝?蜷医↓縺ｯ縲?        // toByteArray()繝｡繧ｽ繝?ラ繧貞他縺ｳ蜃ｺ縺吶?
        return b.toByteArray();
    }



}
