package cn.com.newland.cloudiiapidemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.newland.cloudiiapidemo.util.Constants;
import cn.com.newland.cloudiiapidemo.util.DataCache;
import cn.com.newland.cloudiiapidemo.util.SPHelper;
import cn.com.newland.nle_sdk.requestEntity.SignIn;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private EditText etUserName;
    private EditText etPwd;
    private TextView tvTip;

    private SPHelper spHelper;

    @Override
    protected void onFirst(Bundle saveInstanceState) {
        super.onFirst(saveInstanceState);
        spHelper = SPHelper.getInstant(getApplicationContext());
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected String setTitle() {
        return "登陆";
    }

    @Override
    protected void instantiateView() {
        super.instantiateView();
        etUserName = findViewById(R.id.userName);
        etPwd = findViewById(R.id.pwd);
        tvTip = findViewById(R.id.tip);
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        etUserName.setText(DataCache.getUserName(getApplicationContext()));
        etPwd.setText(DataCache.getPwd(getApplicationContext()));
        setTipInfo();
    }

    @Override
    protected void registerListener() {
        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), SettingActivity.class), 1);

            }
        });
        findViewById(R.id.signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setTipInfo() {
        String baseUrl = DataCache.getBaseUrl(getApplicationContext());
        if (!TextUtils.isEmpty(baseUrl)) tvTip.setText("您的登陆请求地址为:\n" + baseUrl + "v2/Account/Login");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            setTipInfo();
        }
    }

    private void signIn() {
        String platformAddress = spHelper.getStringFromSP(getApplicationContext(), Constants.SETTING_PLATFORM_ADDRESS);
        String port = spHelper.getStringFromSP(getApplicationContext(), Constants.SETTING_PORT);

        final String userName = etUserName.getText().toString();
        final String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(platformAddress) || TextUtils.isEmpty(port)) {
            Toast.makeText(getApplicationContext(), "请设置云平台信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        final NetWorkBusiness netWorkBusiness = new NetWorkBusiness( "",DataCache.getBaseUrl(getApplicationContext()));
        netWorkBusiness.signIn(new SignIn(userName, pwd), new Callback<BaseResponseEntity<User>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponseEntity<User>> call, @NonNull Response<BaseResponseEntity<User>> response) {
                BaseResponseEntity<User> baseResponseEntity = response.body();
                if (baseResponseEntity != null) {
                    if (baseResponseEntity.getStatus() == 0) {
                        DataCache.updateUserName(getApplicationContext(), userName);
                        DataCache.updatePwd(getApplicationContext(), pwd);
                        String accessToken = baseResponseEntity.getResultObj().getAccessToken();
                        DataCache.updateAccessToken(getApplicationContext(), accessToken);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userBaseResponseEntity", baseResponseEntity);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, baseResponseEntity.getMsg(), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(LoginActivity.this, "请求地址出错", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<BaseResponseEntity<User>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

