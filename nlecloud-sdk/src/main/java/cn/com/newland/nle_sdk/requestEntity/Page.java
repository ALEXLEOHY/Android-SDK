package cn.com.newland.nle_sdk.requestEntity;

/**
 * Created by marco on 2017/8/22.
 * 分页请求体
 */

public class Page {
    private String StartDate;
    private String EndDate;
    private String  PageIndex;
    private String  PageSize;

    public Page(String startDate, String endDate, String pageIndex, String pageSize) {
        StartDate = startDate;
        EndDate = endDate;
        PageIndex = pageIndex;
        PageSize = pageSize;
    }
}
