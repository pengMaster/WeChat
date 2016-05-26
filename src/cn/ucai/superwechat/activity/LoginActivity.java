/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ucai.superwechat.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ucai.superwechat.Constant;
import cn.ucai.superwechat.DemoHXSDKHelper;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.applib.controller.HXSDKHelper;
import cn.ucai.superwechat.bean.User;
import cn.ucai.superwechat.data.GsonRequest;
import cn.ucai.superwechat.db.EMUserDao;
import cn.ucai.superwechat.db.UserDao;
import cn.ucai.superwechat.domain.EMUser;
import cn.ucai.superwechat.task.DownloadAllGroupTask;
import cn.ucai.superwechat.task.DownloadContactListTask;
import cn.ucai.superwechat.task.DownloadPublicGroupTask;
import cn.ucai.superwechat.utils.CommonUtils;
import cn.ucai.superwechat.utils.MD5;
import cn.ucai.superwechat.utils.Utils;


/**
 * 登陆页面
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    public static final int REQUEST_CODE_SETNICK = 1;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button mbtnLogin;
    private Button mbtnRegister;
    private Button mbtnSetIP;

    private boolean progressShow;
    private boolean autoLogin = false;

    private String currentUsername;
    private String currentPassword;

    private Context mContext;
    ProgressDialog pd;
    private UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 如果用户名密码都有，直接进入主页面
        if (DemoHXSDKHelper.getInstance().isLogined()) {
            autoLogin = true;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

            return;
        }
        setContentView(R.layout.activity_login);
        mContext = this;
        initView();
        setListener();
    }

    private void setListener() {
        // 如果用户名改变，清空密码
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (SuperWeChatApplication.getInstance().getUserName() != null) {
            usernameEditText.setText(SuperWeChatApplication.getInstance().getUserName());
        }

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoginListener(v);
            }
        });

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRegisterListener(v);
            }
        });

        mbtnSetIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIp(v);
            }
        });
    }

    private void initView() {
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        mbtnLogin = (Button) findViewById(R.id.btnLoginLogin);
        mbtnRegister = (Button) findViewById(R.id.btnLoginRegister);
        mbtnSetIP = (Button) findViewById(R.id.btnSetIP);
    }

    /**
     * 登录
     *
     * @param view
     */
    private void setLoginListener(View view) {
        if (!CommonUtils.isNetWorkConnected(mContext)) {
            Toast.makeText(mContext, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        currentUsername = usernameEditText.getText().toString().trim();
        currentPassword = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(mContext, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(mContext, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressshow();

        final long start = System.currentTimeMillis();
        // 调用sdk登陆方法登陆聊天服务器
        EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                if (!progressShow) {
                    return;
                }
                loginAppServer();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void loginSucess() {
        // 登陆成功，保存用户名密码
//        SuperwechatApplication.getInstance().setUserName(currentUsername);
//        SuperwechatApplication.getInstance().setPassword(currentPassword);
        try {
            // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
            // ** manually load all local groups and
            EMGroupManager.getInstance().loadAllGroups();
            EMChatManager.getInstance().loadAllConversations();
            // 处理好友和群组
            initializeContacts();
            //下载头像
            downLoadAvatar();
            //下载好友列表，下载群组列表
            new DownloadAllGroupTask(mContext, currentUsername).execute();
            new DownloadPublicGroupTask(mContext, currentUsername,0,5).execute();
            new DownloadContactListTask(mContext, currentUsername).execute();
        } catch (Exception e) {
            e.printStackTrace();
            // 取好友或者群聊失败，不让进入主页面
            runOnUiThread(new Runnable() {
                public void run() {
                    pd.dismiss();
                    DemoHXSDKHelper.getInstance().logout(true, null);
                    Toast.makeText(getApplicationContext(), R.string.login_failure_failed, Toast.LENGTH_LONG).show();
                }
            });
            return;
        }
        // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
        boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
                SuperWeChatApplication.currentUserNick.trim());
        if (!updatenick) {
            Log.e("LoginActivity", "update current user nick fail");
        }

        if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
            pd.dismiss();
        }
        // 进入主页面
        Intent intent = new Intent(LoginActivity.this,
                MainActivity.class);
        startActivity(intent);

        finish();
    }

    private void downLoadAvatar() {
        //http://10.0.2.2:8080/SuperWeChatServer/Server?request=download_avatar&avatarType=
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(SuperWeChatApplication.SERVER_ROOT + "?" + I.KEY_REQUEST + "=" + I.REQUEST_DOWNLOAD_AVATAR + "&" + I.AVATAR_TYPE + "=" +
                currentUsername)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                InputStream is = response.body().byteStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = new File(path, "/" + currentUsername + ".jpg");
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                int len;
                byte[] buffer = new byte[1024 * 4];
                while ((len = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
                bis.close();
                bos.close();
            }
        });
    }

    private void loginAppServer() {
        dao = new UserDao(mContext);
        User user = dao.findUserByUserName(currentUsername);
        if (user != null) {

            if (user.equals(MD5.getData(currentPassword))) {
                //本地有正确的用户账号密码信息，调用环信的登录成功模块
                saveUser(user);
                loginSucess();
            } else {
                //本地没有正确的用户账号密码信息，需登录远端服务器
                loginMyServer();
            }
        } else {
            //本地没有用户信息，需登录远端服务器
            loginMyServer();

        }
    }

    private void loginMyServer() {
        String url = null;
        try {
            url = SuperWeChatApplication.SERVER_ROOT + "?" + I.KEY_REQUEST + "=" + I.REQUEST_LOGIN + "&"
                    + I.User.USER_NAME + "=" + currentUsername + "&"
                    + I.User.PASSWORD + "=" + currentPassword;
        } catch (Exception e) {
            e.printStackTrace();
        }
        executeRequest(new GsonRequest<>(url, User.class, new Response.Listener<User>() {
            @Override
            public void onResponse(User user) {
                if (user.isResult()) {
                    //登录成功，设置当前用户，本地数据库更新,调用环信登录成功模块
                    saveUser(user);
                    user.setMUserPassword(MD5.getData(user.getMUserPassword()));
                    UserDao dao = new UserDao(mContext);
                    dao.addUser(user);
                    loginSucess();
                } else {
                    pd.dismiss();
                    Utils.showToast(mContext, user.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        }, errorListener()));
    }

    private void saveUser(User user) {
        SuperWeChatApplication instance = SuperWeChatApplication.getInstance();
        instance.setUserName(currentUsername);
        instance.setPassword(currentPassword);
        SuperWeChatApplication.currentUserNick = user.getMUserNick();
    }

    private void progressshow() {
        progressShow = true;
        pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();
    }

    private void initializeContacts() {
        Map<String, EMUser> userlist = new HashMap<String, EMUser>();
        // 添加user"申请与通知"
        EMUser newFriends = new EMUser();
        newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
        String strChat = getResources().getString(
                R.string.Application_and_notify);
        newFriends.setNick(strChat);

        userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
        // 添加"群聊"
        EMUser groupUser = new EMUser();
        String strGroup = getResources().getString(R.string.group_chat);
        groupUser.setUsername(Constant.GROUP_USERNAME);
        groupUser.setNick(strGroup);
        groupUser.setHeader("");
        userlist.put(Constant.GROUP_USERNAME, groupUser);

        // 添加"Robot"
//        EMUser robotUser = new EMUser();
//        String strRobot = getResources().getString(R.string.robot_chat);
//        robotUser.setUsername(Constant.CHAT_ROBOT);
//        robotUser.setNick(strRobot);
//        robotUser.setHeader("");
//        userlist.put(Constant.CHAT_ROBOT, robotUser);

        // 存入内存
        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).setContactList(userlist);
        // 存入db
        EMUserDao dao = new EMUserDao(LoginActivity.this);
        List<EMUser> users = new ArrayList<EMUser>(userlist.values());
        dao.saveContactList(users);
    }

    /**
     * 注册
     *
     * @param view
     */
    private void setRegisterListener(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }

    private void setIp(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);

        final View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        dialogView.findViewById(R.id.edit).setVisibility(View.VISIBLE);
        dialogView.findViewById(R.id.btn_ok).setVisibility(View.GONE);
        ((TextView) dialogView.findViewById(R.id.title)).setText("设置IP");
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(dialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String IP = ((EditText) dialogView.findViewById(R.id.edit)).getText().toString();
                        if (IP.matches("[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}")) {
                            String[] ipArr = IP.split("\\.");
                            boolean isRightIp=true;
                            for (String i : ipArr) {
                                if (0 < Integer.parseInt(i) && Integer.parseInt(i) < 256) {
                                } else {
                                    isRightIp=false;
                                }
                            }
                            if (isRightIp) {
                                SharedPreferences sp = getSharedPreferences("IP", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("IP", IP);
                                editor.commit();
                                SuperWeChatApplication.SERVER_ROOT = "http://" + IP + ":8080/SuperWeChatServer/Server";
                                Toast.makeText(LoginActivity.this, "IP已设为" + IP, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "IP不合法，使用默认IP：10.0.2.2", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "IP不合法，使用默认IP：10.0.2.2", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create().show();
    }
}
