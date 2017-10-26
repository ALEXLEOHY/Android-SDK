package cn.com.newland.nle_sdk.responseEntity.base;

import java.util.List;

/**
 * Created by marco on 2017/8/31.
 * 基础分页
 */

public class BasePager<T> {
    private List<T> PageSet;

    List<T> getPageSet() {
        return PageSet;
    }
}
