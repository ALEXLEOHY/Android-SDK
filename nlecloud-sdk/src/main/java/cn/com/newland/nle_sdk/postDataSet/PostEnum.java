package cn.com.newland.nle_sdk.postDataSet;

/**
 * Created by marco on 2017/8/28.
 * 请求枚举
 */

public enum PostEnum {
    GET_GATEWAY_INFO("v2/Gateway/{gatewayTag}", "获取某个网关的信息"),
    GET_GATEWAY_SENSOR_LIST("v2/Gateway/{gatewayTag}/SensorList","获取某个网关的传感器列表"),
    GET_SENSOR_INFO("v2/Gateway/{gatewayTag}/Sensor/{apiTag}","获取某个传感器的信息"),
    GET_GATEWAY_ACTUATOR_LIST("v2/Gateway/{gatewayTag}/ActuatorList","获取某个网关的执行器列表"),
    GET_ACTUATOR_INFO("v2/Gateway/{gatewayTag}/Actuator/{apiTag}","获取某个执行器的信息"),
    GET_GATEWAY_CAMERA_LIST("v2/Gateway/{gatewayTag}/CameraList","获取某个网关的摄像头列表"),
    GET_CAMERA_INFO("v2/Gateway/{gatewayTag}/Camera/{apiTag}","获取某个摄像头的信息"),
    GET_GATEWAY_ONOFF_LINE("v2/Gateway/{gatewayTag}/OnOffline","获取某个网关的当前在/离线状态"),
    GET_GATEWAY_HISTORY_PAGER_ONOFF_LINE("v2/Gateway/{gatewayTag}/HistoryPagerOnOffline","获取某个网关的历史分页在/离线状态"),
    GET_GATEWAY_ENABLE("v2/Gateway/{gatewayTag}/Status", "获取某个网关的当前启/禁用状态"),
    GET_GATEWAY_NEWSDATAS("v2/Gateway/{gatewayTag}/NewestDatas", "获取某个网关的所有传感器、执行器最新值"),
    GET_TARGET_SENSOR_NEWDATA("v2/Gateway/{gatewayTag}/Sensor/{apiTag}/NewestData", "获取某个传感器的最新值"),
    GET_TARGET_SENSOR_HISTORY_DATA("v2/Gateway/{gatewayTag}/Sensor/{apiTag}/HistoryData", "获取某个传感器的历史数据"),
    GET_TARGET_SENSOR_PAGER_HISTORY_DATA("v2/Gateway/{gatewayTag}/Sensor/{apiTag}/HistoryPagerData", "获取某个传感器的历史分页数据"),
    GET_TARGET_ACTUATOR_NEWDATA("v2/Gateway/{gatewayTag}/Actuator/{apiTag}/NewestData", "获取某个控制器的最新值"),
    GET_TARGET_ACTUATOR_HISTORY_DATA("v2/Gateway/{gatewayTag}/Actuator/{apiTag}/HistoryData", "获取某个控制器的历史数据"),
    GET_TARGET_ACTUATOR_PARGER_HISTORY_DATA("v2/Gateway/{gatewayTag}/Actuator/{apiTag}/HistoryPagerData", "获取某个控制器的历史分页数据"),
    CONTROL_TARGET_ACTUATOR("v2/Gateway/{gatewayTag}/actuator/{apiTag}/Control?data={data}", "控制某个执行器");
    private String url;
    private String title;

    PostEnum(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
