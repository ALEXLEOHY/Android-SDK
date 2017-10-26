package cn.com.newland.nle_sdk.responseEntity.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.newland.nle_sdk.util.Tools;


/**
 * Created by marco on 2017/8/21.
 * 请求响应
 */

public class BaseResponseEntity<T> implements Serializable {
    private String ErrorObj;
    private int Status;
    private int StatusCode;
    private String Msg;
    private T ResultObj;

    public void setErrorObj(String errorObj) {
        ErrorObj = errorObj;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public void setResultObj(T resultObj) {
        ResultObj = resultObj;
    }

    public String getErrorObj() {
        return ErrorObj;
    }

    public int getStatus() {
        return Status;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public String getMsg() {
        return Msg;
    }

    public T getResultObj() {
        return ResultObj;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("{\n");
        result.append("\t\tErrorObj : ").append(getErrorObj()).append("\n").append("\t\tStatus : ").append(getStatus()).append("\n").append("\t\tStatusCode : ")
                .append(getStatusCode()).append("\n").append("\t\tMsg : ").append(getMsg()).append("\n");
        if (ResultObj instanceof BasePager) {//历史在线记录要做特殊处理
            BasePager basePager = (BasePager) ResultObj;
            result.append("\t\tResultObj:{\n").append("\t\t\t\tPageSet:[\n");
            for (Object item : basePager.getPageSet()) {
                result.append("\t\t\t\t\t\t\t\t{\n").append(Tools.getObjectFieldMsg(item, 10));
                result.append("\t\t\t\t\t\t\t\t}\n");
            }
            result.append("\t\t\t\t\t\t]\n").append("\t\t\t\t}\n").append("\t\t}");
        } else {
            if (ResultObj instanceof ArrayList) {
                result.append("\t\tResultObj:[\n");
                List array = (List) ResultObj;
                for (Object o : array) {
                    result.append("\t\t\t\t{\n").append(Tools.getObjectFieldMsg(o, 6));
                    result.append("\t\t\t\t}\n");
                }
                result.append("\t\t]").append("\n}");
            } else {
                result.append("\t\tResultObj:{\n").append(Tools.getObjectFieldMsg(ResultObj, 4)).append("\t\t}\n").append("}");
            }
        }
        return String.valueOf(result);
    }
}
