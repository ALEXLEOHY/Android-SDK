package cn.com.newland.nle_sdk.requestEntity;

/**
 * Created by marco on 2017/8/21.
 * 登陆实体
 */

public class SignIn {
    private String Account;
    private String Password;

    public SignIn(String account, String password) {
        Account = account;
        Password = password;
    }
}
