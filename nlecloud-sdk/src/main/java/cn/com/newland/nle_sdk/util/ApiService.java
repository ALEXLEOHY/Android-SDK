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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by marco on 2017/8/21.
 * api请求接口
 */

public interface ApiService {
    /**
     * 登陆
     */
    @POST("v2/Account/Login")
    Call<BaseResponseEntity<User>> signIn(@Body SignIn signIn);

    /**
     * 获取某个网关的信息
     */
    @POST("v2/Gateway/{gatewayTag}")
    Call<BaseResponseEntity<GateWayInfo>> getGateWayInfoByTag(@Path("gatewayTag") String gateWayTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个网关的传感器列表
     */
    @POST("v2/Gateway/{gatewayTag}/SensorList")
    Call<BaseResponseEntity<List<ListItemOfSensor>>> getGateWaySensorList(@Path("gatewayTag") String gateWayTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个传感器的信息
     */
    @POST("v2/Gateway/{gatewayTag}/Sensor/{apiTag}")
    Call<BaseResponseEntity<TargetSensorInfo>> getSensorInfo(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个网关的执行器列表
     */
    @POST("v2/Gateway/{gatewayTag}/ActuatorList")
    Call<BaseResponseEntity<List<ListItemOfActuator>>> getGateWayActuatorList(@Path("gatewayTag") String gateWayTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个执行器的信息
     */
    @POST("v2/Gateway/{gatewayTag}/Actuator/{apiTag}")
    Call<BaseResponseEntity<TargetActuatorInfo>> getGateWayActuatorInfo(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Header("AccessToken") String
            accessToken);

    /**
     * 获取某个网关的摄像头列表
     */
    @POST("v2/Gateway/{gatewayTag}/CameraList")
    Call<BaseResponseEntity<List<ListItemOfCamera>>> getGateWayCameraList(@Path("gatewayTag") String gateWayTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个摄像头的信息
     */
    @POST("v2/Gateway/{gatewayTag}/Camera/{apiTag}")
    Call<BaseResponseEntity<TargetCameraInfo>> getGateWayCameraInfo(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Header("AccessToken") String
            accessToken);

    /**
     * 获取某个网关的当前在/离线状态
     */
    @POST("v2/Gateway/{gatewayTag}/OnOffline")
    Call<BaseResponseEntity<GateWayState>> getGateWayState(@Path("gatewayTag") String gateWayTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个网关的历史分页在/离线状态
     */
    @POST("v2/Gateway/{gatewayTag}/HistoryPagerOnOffline")
    Call<BaseResponseEntity<BasePager<PagerItemGateWayOnOff>>> getOnOffHistory(@Path("gatewayTag") String gateWayTag, @Body Page page, @Header("AccessToken") String accessToken);

    /**
     * 获取某个网关的当前启/禁状态
     */
    @POST("v2/Gateway/{gatewayTag}/Status")
    Call<BaseResponseEntity<Boolean>> getGateWayEnable(@Path("gatewayTag") String gateWayTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个网关的所有传感器、执行器最新值
     */
    @POST("v2/Gateway/{gatewayTag}/NewestDatas")
    Call<BaseResponseEntity<List<ListItemOfNewestData>>> getAllDeviceNewestData(@Path("gatewayTag") String gateWayTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个传感器的最新值
     */
    @POST("v2/Gateway/{gatewayTag}/Sensor/{apiTag}/NewestData")
    Call<BaseResponseEntity<SensorNewestData>> getSensorNewestData(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个传感器的历史数据
     */
    @POST("v2/Gateway/{gatewayTag}/Sensor/{apiTag}/HistoryData")
    Call<BaseResponseEntity<List<ListItemOfSensorHistory>>> getSensorHistoryData(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Body DeviceHistory
            deviceHistory, @Header("AccessToken") String accessToken);

    /**
     * 获取某个传感器的历史分页数据
     */
    @POST("v2/Gateway/{gatewayTag}/Sensor/{apiTag}/HistoryPagerData")
    Call<BaseResponseEntity<BasePager<PagerItemSensor>>> getPageSensorData(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Body Page page, @Header
            ("AccessToken") String accessToken);

    /**
     * 获取某个控制器的最新值
     */
    @POST("v2/Gateway/{gatewayTag}/Actuator/{apiTag}/NewestData")
    Call<BaseResponseEntity<ActuatorNewestData>> getActuatorNewestData(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Header("AccessToken") String accessToken);

    /**
     * 获取某个控制器的历史数据
     */
    @POST("v2/Gateway/{gatewayTag}/Actuator/{apiTag}/HistoryData")
    Call<BaseResponseEntity<List<ListItemOfActuatorHistory>>> getActuatorHistoryData(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Body DeviceHistory
            deviceHistory, @Header("AccessToken") String accessToken);

    /**
     * 获取某个控制器的历史分页数据
     */
    @POST("v2/Gateway/{gatewayTag}/Actuator/{apiTag}/HistoryPagerData")
    Call<BaseResponseEntity<BasePager<PagerItemActuator>>> getPageActuatorData(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Body Page page, @Header
            ("AccessToken") String accessToken);

    /**
     * 控制某个执行器
     */
    @POST("v2/Gateway/{gatewayTag}/actuator/{apiTag}/Control?data={data}")
    Call<BaseResponseEntity<String>> controlActuator(@Path("gatewayTag") String gateWayTag, @Path("apiTag") String apiTag, @Path("data") String data, @Header("AccessToken")
            String accessToken);

}
