package com.gdcp.yueyunku_client.utils;

import android.text.TextUtils;
import android.util.Log;

import com.gdcp.yueyunku_client.app.Common;
import com.gdcp.yueyunku_client.model.Comment;
import com.gdcp.yueyunku_client.model.Dynamic;
import com.gdcp.yueyunku_client.model.User;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Asus on 2017/5/11.
 */

public class BmobUtils {
    private static final String MODEL_NAME="悦运酷";
    private static String res="";

    /*
    * 发送验证码
    * */
    public static void sendCode(String phoneNumber,QueryListener<Integer> listener){
        BmobSMS.requestSMSCode(phoneNumber,MODEL_NAME, listener);
    }

    /*
    * 手机号注册
    * */
    public static void signByMobile(String mobile, String password,String code,SaveListener<User> listener){
        User user=new User();
        user.setMobilePhoneNumber(mobile);
        user.setPassword(password);
        user.setType(0);
        user.signOrLogin(code,listener);
    }

    /*
    * 获取当前用户状态
    * */
    public static boolean getCurrentUserState(){
        User user=BmobUser.getCurrentUser(User.class);
        if (user==null){
            return false;
        }else {
            return true;
        }
    }

    /*
    * 获取当前用户
    * */
    public static User getCurrentUser(){
        User user=BmobUser.getCurrentUser(User.class);
        return user;
    }

    /*
    * 手机号码+密码登录
    * */
    public static void login(String phoneNumber,String pwd,LogInListener<User> listener){
        BmobUser.loginByAccount(phoneNumber, pwd, listener);
    }

    /**
     *
     * 退出登录
     * */
    public static void logout(){
        User.logOut();
    }

    /*
    * 获取动态内容
    * */
    public static void getDynamicContent(FindListener<Dynamic> listener){
        BmobQuery<Dynamic> query=new BmobQuery<>();
        query.include("author");//加入关联性的才能查到该语句
        query.setLimit(10);
        query.order("-createdAt");
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findObjects(listener);
    };


    /**
     * 下拉加载
     * */
    public static void getDynamicByPull(FindListener<Dynamic> listener){
        BmobQuery<Dynamic> query=new BmobQuery<>();
        query.include("author");//加入关联性的才能查到该语句
        query.setLimit(10);
        query.order("-createdAt");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(listener);
    }


    /**
     *  获取评论内容
     */
    public static void getCommentContent(Dynamic dynamic,FindListener<Comment> listener){
        BmobQuery<Comment> query=new BmobQuery<>();
        query.addWhereEqualTo("dynamic",dynamic);
        query.include("user,dynamic");
        query.order("-createdAt");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(listener);
    }

    /*
    * 查询当前用户的动态
    * */
    public static void getCurrentUserDynamic(FindListener<Dynamic> listener){
        BmobQuery<User> innerQuery = new BmobQuery<>();
        String[] Ids={BmobUtils.getCurrentUser().getObjectId()};
        innerQuery.addWhereContainedIn("objectId", Arrays.asList(Ids));
        BmobQuery<Dynamic> query = new BmobQuery<Dynamic>();
        query.order("-createdAt");
        query.setLimit(10);
        query.addWhereMatchesQuery("author", "_User", innerQuery);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(listener);
    }

    /*
    *
    * 上拉加载（我的动态）
    * */
    public static void loadMoreDataForMyDynamic(String lastCreateAt,FindListener<Dynamic> listener){
        List<BmobQuery<Dynamic>> bmobQueries=new ArrayList<>();


        BmobQuery<User> innerQuery = new BmobQuery<>();
        String[] Ids={BmobUtils.getCurrentUser().getObjectId()};
        innerQuery.addWhereContainedIn("objectId", Arrays.asList(Ids));
        BmobQuery<Dynamic> query1=new BmobQuery<>();
        query1.addWhereMatchesQuery("author", "_User", innerQuery);


        BmobQuery<Dynamic> query2=new BmobQuery<>();
        Date date  = null;
        if (lastCreateAt!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(lastCreateAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        query2.addWhereLessThan("createdAt",new BmobDate(date));

        bmobQueries.add(query1);
        bmobQueries.add(query2);

        BmobQuery<Dynamic> query=new BmobQuery<>();
        query.and(bmobQueries);
        query.order("-createdAt");
        query.setLimit(5);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(listener);

    }


    /*
    *
    * 上拉加载
    * */
    public static void loadMoreData(String lastCreateAt,FindListener<Dynamic> listener){
        BmobQuery<Dynamic> query=new BmobQuery<>();
        query.include("author");
        query.order("-createdAt");
        query.setLimit(10);
        Date date  = null;
        if (lastCreateAt!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(lastCreateAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        query.addWhereLessThan("createdAt",new BmobDate(date));
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(listener);
    }

}
