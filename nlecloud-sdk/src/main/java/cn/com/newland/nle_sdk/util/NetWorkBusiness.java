package cn.com.newland.nle_sdk.util;

import java.util.List;

import cn.com.newland.nle_sdk.requestEntity.DeviceHistory;
import cn.com.newland.nle_sdk.requestEntity.Page;
import cn.com.newland.nle_sdk.requestEntity.SignIn;
import cn.com.newland.nle_sdk.responseEntity.ActuatorNewestData;
import cn.com.newland.nle_sdk.responseEntity.GateWayInfo;
import cn.com.newland.nle_sdk.responseEntity.GateWayState;
import cn.com.newland.nle_sdk.responseEntity.ListItemOfActuator;
import cn.com.newland.nle_sdk.responseEntity.ListItemOfActuatorHistory;
import cn.com.newland.nle_sdk.responseEntity.ListItemOfCamera;
import cn.com.newland.nle_sdk.responseEntity.ListItemOfNewestData;
import cn.com.newland.nle_sdk.responseEntity.ListItemOfSensor;
import cn.com.newland.nle_sdk.responseEntity.ListItemOfSensorHistory;
import cn.com.newland.nle_sdk.responseEntity.PagerItemActuator;
import cn.com.newland.nle_sdk.responseEntity.PagerItemGateWayOnOff;
import cn.com.newland.nle_sdk.responseEntity.PagerItemSensor;
import cn.com.newland.nle_sdk.responseEntity.SensorNewestData;
import cn.com.newland.nle_sdk.responseEntity.TargetActuatorInfo;
import cn.com.newland.nle_sdk.responseEntity.TargetCameraInfo;
import cn.com.newland.nle_sdk.responseEntity.TargetSensorInfo;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BasePager;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import retrofit2.Callback;

/**
 * Created by marco on 2017/8/21.
 * api调用逻辑
 */

public class NetWorkBusiness {
    private ApiService apiService;
    private String accessToken;
    public NetWorkBusiness(String accessToken,String baseUrl) {
        this.apiService = Tools.buildService(baseUrl);
        this.accessToken = accessToken;
    }

    public void signIn(SignIn signIn, Callback<BaseResponseEntity<User>> callback) {
        apiService.signIn(signIn).enqueue(callback);
    }

    public void getGateWayInfo(String gateWayTag, Callback<BaseResponseEntity<GateWayInfo>> callback) {
        apiService.getGateWayInfoByTag(gateWayTag, accessToken).enqueue(callback);
    }

    public void getGateWaySensorList(String gateWayTag, Callback<BaseResponseEntity<List<ListItemOfSensor>>> callback) {
        apiService.getGateWaySensorList(gateWayTag, accessToken).enqueue(callback);
    }

    public void getSensorInfo(String gateWayTag, String apiTag, Callback<BaseResponseEntity<TargetSensorInfo>> callback) {
        apiService.getSensorInfo(gateWayTag, apiTag, accessToken).enqueue(callback);
    }

    public void getGateWayActuatorList(String gateWayTag, Callback<BaseResponseEntity<List<ListItemOfActuator>>> callback) {
        apiService.getGateWayActuatorList(gateWayTag, accessToken).enqueue(callback);
    }

    public void getActuatorInfo(String gateWayTag, String apiTag, Callback<BaseResponseEntity<TargetActuatorInfo>> callback) {
        apiService.getGateWayActuatorInfo(gateWayTag, apiTag, accessToken).enqueue(callback);

    }

    public void getGateWayCameraList(String gateWayTag, Callback<BaseResponseEntity<List<ListItemOfCamera>>> callback) {
        apiService.getGateWayCameraList(gateWayTag, accessToken).enqueue(callback);
    }

    public void getCameraInfo(String gateWayTag, String apiTag, Callback<BaseResponseEntity<TargetCameraInfo>> callback) {
        apiService.getGateWayCameraInfo(gateWayTag, apiTag, accessToken).enqueue(callback);
    }

    public void getGateWayState(String gateWayTag, Callback<BaseResponseEntity<GateWayState>> callback) {
        apiService.getGateWayState(gateWayTag, accessToken).enqueue(callback);
    }

    public void getOnOffHistory(String gateWayTag, Page page, Callback<BaseResponseEntity<BasePager<PagerItemGateWayOnOff>>> callback) {
        apiService.getOnOffHistory(gateWayTag, page.StartDate,page.EndDate,page.PageIndex,page.PageSize, accessToken).enqueue(callback);
    }

    public void getGateWayEnable(String gateWayTag, Callback<BaseResponseEntity<Boolean>> callback) {
        apiService.getGateWayEnable(gateWayTag, accessToken).enqueue(callback);
    }

    public void getAllDeviceNewestData(String gateWayTag, Callback<BaseResponseEntity<List<ListItemOfNewestData>>> callback) {
        apiService.getAllDeviceNewestData(gateWayTag, accessToken).enqueue(callback);
    }

    public void getSensorNewestData(String gateWayTag, String apiTag, Callback<BaseResponseEntity<SensorNewestData>> callback) {
        apiService.getSensorNewestData(gateWayTag, apiTag, accessToken).enqueue(callback);
    }

    public void getSensorHistoryData(String gateWayTag, String apiTag, DeviceHistory deviceHistory, Callback<BaseResponseEntity<List<ListItemOfSensorHistory>>> callback) {
        apiService.getSensorHistoryData(gateWayTag, apiTag, deviceHistory.Method,deviceHistory.TimeAgo, accessToken).enqueue(callback);
    }

    public void getPageSensorData(String gateWayTag, String apiTag, Page page, Callback<BaseResponseEntity<BasePager<PagerItemSensor>>> callback) {
        apiService.getPageSensorData(gateWayTag, apiTag, page.StartDate,page.EndDate,page.PageIndex,page.PageSize, accessToken).enqueue(callback);
    }

    public void getActuatorNewestData(String gateWayTag, String apiTag, Callback<BaseResponseEntity<ActuatorNewestData>> callback) {
        apiService.getActuatorNewestData(gateWayTag, apiTag, accessToken).enqueue(callback);
    }

    public void getActuatorHistoryData(String gateWayTag, String apiTag, DeviceHistory deviceHistory, Callback<BaseResponseEntity<List<ListItemOfActuatorHistory>>> callback) {
        apiService.getActuatorHistoryData(gateWayTag, apiTag,deviceHistory.Method,deviceHistory.TimeAgo, accessToken).enqueue(callback);
    }

    public void getPageActuatorData(String gateWayTag, String apiTag, Page page, Callback<BaseResponseEntity<BasePager<PagerItemActuator>>> callback) {
        apiService.getPageActuatorData(gateWayTag, apiTag, page.StartDate,page.EndDate,page.PageIndex,page.PageSize, accessToken).enqueue(callback);
    }

    public void controlActuator(String gateWayTag, String apiTag, String data, Callback<BaseResponseEntity<String>> callback) {
        apiService.controlActuator(gateWayTag, apiTag, data, accessToken).enqueue(callback);
    }


}
