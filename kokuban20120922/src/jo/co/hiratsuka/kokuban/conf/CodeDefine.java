package jo.co.hiratsuka.kokuban.conf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;


public class CodeDefine
{
    private CodeDefine() {
    }

    /**
     * サービスのリリース日を持っておく
     */
    public static final String DOWNLOAD_DEFAULT_DATE = "1990-10-01 00:00:00";

    /**
     * パスワード暗号化�?解析キー
     */
    public static final String PASSWORD_SALT = "indival";

    public static final int MESSAGE_WHAT_RESET_END = 1;
    public static final int MESSAGE_WHAT_RESET_ERROR = -1;

    public static final int MESSAGE_WHAT_SEARCH_END = 1;
    public static final int MESSAGE_WHAT_SEARCH_ERROR = -1;
    public static final int MESSAGE_WHAT_INSERT_END = 2;
    public static final int MESSAGE_WHAT_INSERT_ERROR = -2;
    public static final int MESSAGE_WHAT_EDIT_END = 3;
    public static final int MESSAGE_WHAT_EDIT_ERROR = -3;
    public static final int MESSAGE_WHAT_DELETE_END = 4;
    public static final int MESSAGE_WHAT_DELETE_ERROR = -4;
    public static final int MESSAGE_WHAT_SQLITE_ERROR = -5;
    public static final int MESSAGE_WHAT_SQLITE_PRE_ERROR = -5;
    public static final int MESSAGE_WHAT_ADD_HOLDER_END = 6;
    public static final int MESSAGE_WHAT_ADD_HOLDER_ERROR = -6;

    public static final int MESSAGE_WHAT_EXIT_END = 99;
    public static final int MESSAGE_WHAT_EXIT_ERROR = -99;

    /**
     * 共有�?メ�?��ージコードをセ�?��
     */
    public static final int MESSAGE_WHAT_SHARED_END = 9;
    public static final int MESSAGE_WHAT_SHARED_ERROR = -9;

    /**
     * キャ�?��ュクリア処�?     */
    public static final int MESSAGE_WHAT_CACHE_CLEAR_END = 10;
    public static final int MESSAGE_WHAT_CACHE_CLEAR_ERROR = -10;

    /**
     * マスター更新
     */
    public static final int MESSAGE_WHAT_MASTER_UPDATE_END = 99;
    public static final int MESSAGE_WHAT_MASTER_UPDATE_ERROR = -99;

    /**
     * 画�?     */
    public static final int MESSAGE_WHAT_IMAGE_SELECT_END = 22;
    public static final int MESSAGE_WHAT_IMAGE_SELECT_ERROR = -21;

    public static final int MESSAGE_WHAT_NOT_FOUND_ERROR = -1;
    public static final int MESSAGE_WHAT_BAD_ACCESS_ERROR = -2;

    public static final int ACTION_LIST = 1;
    public static final int ACTION_ALL_HISTORY_LIST = 10;
    public static final int ACTION_DETAIL = 2;
    public static final int ACTION_MAP_LIST = 3;

    public static final int ACTION_PHOTO = 0;
    public static final int ACTION_MOVIE = 1;


    public static final int REQUEST_PICK_CONTACT = 1;
    public static final int REQUEST_PICK_CONTACT_MOVIE = 2;
    public static final int REQUEST_PICK_CONTACT_PEOPLE = 3;

    public static final int REQUEST_IMAGE_CAPTURED = 999;
    public final static int REQUEST_VIDEO_CAPTURED = 1000;


    /**
     * プログレスバ�?の�?��値
     */
    public static final int MAX_PROGRESS = 100;

    public static final int LIST_PAGING_COUNT = 10;
    public static final int IMAGELIST_PAGING_COUNT = 60;
    /**
     * カメラか�?真アルバム
     *
     */
    public static final int ACTION_CAMERA = 0;
    public static final int ACTION_GALLERY = 1;

    public static final int listImageWidth = 120;
    public static final int listImageHeight = 90;

    public static final int gridImageWidth = 150;
    public static final int gridImageHeight = 110;

    public static final int detailImageWidth = 160;
    public static final int detailImageHeight = 120;

    public static final int slidePortraitImageWidth = 420;
    public static final int slidePortraitImageHeight = 700;
    public static final int slideLandscapeImageWidth = 700;
    public static final int slideLandscapeImageHeight = 420;

    public static final int defaultImageWidth = 0;
    public static final int defaultImageHeight = 0;

    /**
     * シーケンスで取りに�?��際�?、IDのア�??かダウン�?     */
    public static final int IMAGE_NEXT = 1;
    public static final int IMAGE_PREV = -1;

    /**
     * バリ�??ション定義
     */
    public static final int VALID_REQUIRED = 0;
    public static final int VALID_MAIL_ADDRESS = 1;
    public static final int VALID_HANKAKU = 2;
    public static final int VALID_HANKAKU_NUM = 3;
    public static final int VALID_LENGTH = 99;

    /**
     * ソート�?定義
     */
    public static final int SORT_LASTUPDATE_ASC = 1;
    public static final int SORT_LASTUPDATE_DESC = -1;

    public static final int SORT_REGISTERDATE_ASC = 2;
    public static final int SORT_REGISTERDATE_DESC = -2;

    public static final int SORT_BONJI_ASC = 2;
    public static final int SORT_BONJI_DESC = 22;


//    public static final String[] monthAry = {"01","02","03","04","05","06","07","08","09","10","11","12"};
//    public static final String[] dayAry = {"01","02","03","04","05","06","07","08","09","10","11",
//                                            "12","13","14","15","16","17","18","19","20","21","22","23",
//                                            "24","25","26","27","28","29","30","31"};

    /**
     * 更新権限ある仮名し�?     */
    public static final String AUTH_UPDATE = "1";
    public static final String NOT_AUTH_UPDATE = "0";

    /**
     * 管�??フラグがありかなしか
     *
     */
    public static final String AUTH_ADMIN = "1";
    public static final String NOT_AUTH_ADMIN = "0";

    /**
     * Holderのアクション
     */
    public static final String HOLDER_ACTION_REGIST = "regist";
    public static final String HOLDER_ACTION_ADD = "add";
    public static final String HOLDER_ACTION_UPDATE = "update";

    /**
     * スワイプ�?��を定義
     */
    public static final int SWIPE_MIN_DISTANCE = 120;
    public static final int SWIPE_MAX_OFF_PATH = 250;
    public static final int SWIPE_THRESHOLD_VELOCITY = 200;

    public static final double ERROR_DOUBLE = 10000.0;

    /**
     * 続柄
     */
    public static final String FAMILY_RELATION_ADMIN = "99";
    public static final String FAMILY_RELATION_FATHER = "1";
    public static final String FAMILY_RELATION_MOTHER = "2";
    public static final String FAMILY_RELATION_SISTER = "3";
    public static final String FAMILY_RELATION_BROTHER = "4";
    public static final String FAMILY_RELATION_COUSIIN = "5";
    public static final String FAMILY_RELATION_GRAND_FATHER = "6";
    public static final String FAMILY_RELATION_GRAND_MOTHER = "7";

    /**
     * YEARBOOKの検索モー�?     */
    public static final int SELECT_YEARBOOK_IDLIST = 0;
    public static final int SELECT_YEARBOOK_ALL = 1;
    public static final int SELECT_YEARBOOK_ID = 2;

    /**
     * 外部連携の結果コー�?     */
    public static final int TWITTER_SUCCESS = 1;
    public static final int FACEBOOK_SUCCESS = 1;
    public static final int DEFAULT_UPDATE_SUCCESS = 1;

    public static final String CONSUMER_KEY = "g7cPNp0xkBaYC9hTSi1bg";
    public static final String CONSUMER_SECRET = "jG63f4nxZuH09kTTMeOP3B16mSAxK3JbSJAL6CwtU0";
    public static final String OAUTH_ACCESS_TOKEN = "106246800-HmH6TPv25TNbWE5rERxkTlDZ1AhTsjKIp2Ym3a42";
    public static final String OAUTH_ACCESS_TOKEN_SECRET = "Bsm2N5A67ts344Kn5kufGj8Jx2pZekf5icKLQ4648";
    public static final String TWITPIC_API_KEY = "8e62216b34c1d0a44dfbcb2139b04934";



    public static final String CALLBACK_URL = "twitterapp://connect";

    public static final String FACEBOOK_APP_ID = "229799000412269";

    public static final int FACEBOOK_REQUEST_CODE = 32665;

    /**
     * progress�?��アログのメ�?��ージコー�?     *
     */
    public static final int PROGRESS_DEFAULT_DOWNLOAD = 0;
    public static final int PROGRESS_FILE_UPLOAD = 1;
    public static final int PROGRESS_POST_DATA = 2;
    public static final int PROGRESS_EDIT_DATA = 3;
    public static final int PROGRESS_DATA_LOAD = 4;
    public static final int CONFIRM_DIALOG = 5;
    public static final int LOGOUT_CONFIRM_DIALOG = 6;
    public static final int DELETE_CONFIRM_DIALOG = 7;
    public static final int YEARBOOK_CONFIRM_DIALOG = 7;
    public static final int PROGRESS_DELETE_DATA = 10;
    public static final int PROGRESS_IMAGE_LOAD = 11;


    public static final int SQLITE_COLUMN_REQUIRED 	= 1;
    public static final int SQLITE_COLUMN_AUTO 		= 9;
    public static final int SQLITE_COLUMN_OPTION 	= 0;

    /**
     * 現在日配�?を取�?     * @return
     */
    public static List<String> feedNowDate() {
        Date date1 = new Date();  //(1)Dateオブジェクトを生�?
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

    
}
