package cn.com.newland.cloudiiapidemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.newland.cloudiiapidemo.util.DataCache;
import cn.com.newland.nle_sdk.postDataSet.PostEnum;
import cn.com.newland.nle_sdk.requestEntity.DeviceHistory;
import cn.com.newland.nle_sdk.requestEntity.Page;
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
import cn.com.newland.nle_sdk.responseEntity.base.BasePager;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static cn.com.newland.nle_sdk.postDataSet.PostEnum.CONTROL_TARGET_ACTUATOR;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_ACTUATOR_INFO;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_CAMERA_INFO;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_GATEWAY_HISTORY_PAGER_ONOFF_LINE;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_SENSOR_INFO;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_TARGET_ACTUATOR_HISTORY_DATA;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_TARGET_ACTUATOR_NEWDATA;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_TARGET_ACTUATOR_PARGER_HISTORY_DATA;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_TARGET_SENSOR_HISTORY_DATA;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_TARGET_SENSOR_NEWDATA;
import static cn.com.newland.nle_sdk.postDataSet.PostEnum.GET_TARGET_SENSOR_PAGER_HISTORY_DATA;


public class DetailActivity extends BaseActivity {
    private EditText etGateWayTag;
    private EditText etApiTag;
    private EditText etMethod;
    private EditText etTimeAgo;
    private EditText etStartTime;
    private EditText etEndTime;
    private EditText etPageIndex;
    private EditText etPageSize;
    private EditText etDeviceData;

    private TextView tvRequestUrl;
    private TextView tvPostResult;
    private PostEnum postEnum;
    private List<PostEnum> includeApiTagEnum = new ArrayList<>();

    @Override
    protected void onFirst(Bundle saveInstanceState) {
        super.onFirst(saveInstanceState);
        postEnum = (PostEnum) getIntent().getSerializableExtra("apiItem");
        includeApiTagEnum.add(GET_SENSOR_INFO);
        includeApiTagEnum.add(GET_ACTUATOR_INFO);
        includeApiTagEnum.add(GET_CAMERA_INFO);
        includeApiTagEnum.add(GET_TARGET_SENSOR_NEWDATA);
        includeApiTagEnum.add(GET_TARGET_SENSOR_HISTORY_DATA);
        includeApiTagEnum.add(GET_TARGET_SENSOR_PAGER_HISTORY_DATA);
        includeApiTagEnum.add(GET_TARGET_ACTUATOR_NEWDATA);
        includeApiTagEnum.add(GET_TARGET_ACTUATOR_HISTORY_DATA);
        includeApiTagEnum.add(GET_TARGET_ACTUATOR_PARGER_HISTORY_DATA);
        includeApiTagEnum.add(CONTROL_TARGET_ACTUATOR);

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_detail;
    }

    @Override
    protected String setTitle() {
        return postEnum.getTitle();
    }

    @Override
    protected void instantiateView() {
        super.instantiateView();
        etGateWayTag = findViewById(R.id.gateWayTag);
        etApiTag = findViewById(R.id.apiTag);
        etMethod = findViewById(R.id.method);
        etTimeAgo = findViewById(R.id.timeAgo);
        etStartTime = findViewById(R.id.startTime);
        etEndTime = findViewById(R.id.endTime);
        etPageIndex = findViewById(R.id.PageIndex);
        etPageSize = findViewById(R.id.PageSize);
        etDeviceData = findViewById(R.id.deviceData);

        tvRequestUrl = findViewById(R.id.requestUrl);
        tvPostResult = findViewById(R.id.postResult);
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        etGateWayTag.setText(DataCache.getGateWayTag(getApplicationContext()));
        tvRequestUrl.setText(String.format("请求的url地址为: %s%s", DataCache.getBaseUrl(getApplicationContext()), postEnum.getUrl()));

        if (!includeApiTagEnum.contains(postEnum)) {
            etApiTag.setVisibility(View.GONE);
        }
        if (postEnum == CONTROL_TARGET_ACTUATOR) {
            etDeviceData.setVisibility(View.VISIBLE);
        }
        if (postEnum == GET_GATEWAY_HISTORY_PAGER_ONOFF_LINE || postEnum == GET_TARGET_SENSOR_PAGER_HISTORY_DATA || postEnum == GET_TARGET_ACTUATOR_PARGER_HISTORY_DATA) {
            etStartTime.setVisibility(View.VISIBLE);
            etEndTime.setVisibility(View.VISIBLE);
            etPageIndex.setVisibility(View.VISIBLE);
            etPageSize.setVisibility(View.VISIBLE);
        }
        if (postEnum == GET_TARGET_SENSOR_HISTORY_DATA || postEnum == GET_TARGET_ACTUATOR_HISTORY_DATA) {
            etMethod.setVisibility(View.VISIBLE);
            etTimeAgo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void registerListener() {
        super.registerListener();
        findViewById(R.id.launch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void launch() {
        NetWorkBusiness netWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(getApplicationContext()), DataCache.getBaseUrl(getApplicationContext()));
        String gateWayTag = etGateWayTag.getText().toString();
        String apiTag = etApiTag.getText().toString();
        String method = etMethod.getText().toString();
        String timeAgo = etTimeAgo.getText().toString();
        String startTime = etStartTime.getText().toString();
        String endTime = etEndTime.getText().toString();
        String pageIndex = etPageIndex.getText().toString();
        String pageSize = etPageSize.getText().toString();
        String controlValue = etDeviceData.getText().toString();
        switch (postEnum) {
            case GET_GATEWAY_INFO:
                netWorkBusiness.getGateWayInfo(gateWayTag, new Callback<BaseResponseEntity<GateWayInfo>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<GateWayInfo>> call, @NonNull Response<BaseResponseEntity<GateWayInfo>> response) {
                        BaseResponseEntity<GateWayInfo> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<GateWayInfo>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });

                break;
            case GET_GATEWAY_SENSOR_LIST:
                netWorkBusiness.getGateWaySensorList(gateWayTag, new Callback<BaseResponseEntity<List<ListItemOfSensor>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<List<ListItemOfSensor>>> call, @NonNull Response<BaseResponseEntity<List<ListItemOfSensor>>> response) {
                        BaseResponseEntity<List<ListItemOfSensor>> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());

                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<List<ListItemOfSensor>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_SENSOR_INFO:
                netWorkBusiness.getSensorInfo(gateWayTag, apiTag, new Callback<BaseResponseEntity<TargetSensorInfo>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<TargetSensorInfo>> call, @NonNull Response<BaseResponseEntity<TargetSensorInfo>> response) {
                        BaseResponseEntity<TargetSensorInfo> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());

                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<TargetSensorInfo>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_GATEWAY_ACTUATOR_LIST:
                netWorkBusiness.getGateWayActuatorList(gateWayTag, new Callback<BaseResponseEntity<List<ListItemOfActuator>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<List<ListItemOfActuator>>> call, @NonNull Response<BaseResponseEntity<List<ListItemOfActuator>>>
                            response) {
                        BaseResponseEntity<List<ListItemOfActuator>> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());

                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<List<ListItemOfActuator>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_ACTUATOR_INFO:
                netWorkBusiness.getActuatorInfo(gateWayTag, apiTag, new Callback<BaseResponseEntity<TargetActuatorInfo>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<TargetActuatorInfo>> call, @NonNull Response<BaseResponseEntity<TargetActuatorInfo>> response) {
                        BaseResponseEntity<TargetActuatorInfo> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<TargetActuatorInfo>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_GATEWAY_CAMERA_LIST:
                netWorkBusiness.getGateWayCameraList(gateWayTag, new Callback<BaseResponseEntity<List<ListItemOfCamera>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<List<ListItemOfCamera>>> call, @NonNull Response<BaseResponseEntity<List<ListItemOfCamera>>> response) {
                        BaseResponseEntity<List<ListItemOfCamera>> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<List<ListItemOfCamera>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_CAMERA_INFO:
                netWorkBusiness.getCameraInfo(gateWayTag, apiTag, new Callback<BaseResponseEntity<TargetCameraInfo>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<TargetCameraInfo>> call, @NonNull Response<BaseResponseEntity<TargetCameraInfo>> response) {
                        BaseResponseEntity<TargetCameraInfo> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<TargetCameraInfo>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_GATEWAY_ONOFF_LINE:
                netWorkBusiness.getGateWayState(gateWayTag, new Callback<BaseResponseEntity<GateWayState>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<GateWayState>> call, @NonNull Response<BaseResponseEntity<GateWayState>> response) {
                        BaseResponseEntity<GateWayState> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());

                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<GateWayState>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_GATEWAY_HISTORY_PAGER_ONOFF_LINE:
                netWorkBusiness
                        .getOnOffHistory(gateWayTag, new Page(startTime, endTime, pageIndex, pageSize), new Callback<BaseResponseEntity<BasePager<PagerItemGateWayOnOff>>>() {
                            @Override
                            public void onResponse(@NonNull Call<BaseResponseEntity<BasePager<PagerItemGateWayOnOff>>> call, @NonNull
                                    Response<BaseResponseEntity<BasePager<PagerItemGateWayOnOff>>> response) {
                                BaseResponseEntity<BasePager<PagerItemGateWayOnOff>> baseResponseEntity = response.body();
                                if (baseResponseEntity != null) {
                                    tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                                } else {
                                    tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<BaseResponseEntity<BasePager<PagerItemGateWayOnOff>>> call, @NonNull Throwable t) {
                                tvPostResult.setText("请求出错 : \n" + t.getMessage());
                            }
                        });
                break;
            case GET_GATEWAY_ENABLE:
                netWorkBusiness.getGateWayEnable(gateWayTag, new Callback<BaseResponseEntity<Boolean>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<Boolean>> call, @NonNull Response<BaseResponseEntity<Boolean>> response) {
                        BaseResponseEntity<Boolean> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<Boolean>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_GATEWAY_NEWSDATAS:
                netWorkBusiness.getAllDeviceNewestData(gateWayTag, new Callback<BaseResponseEntity<List<ListItemOfNewestData>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<List<ListItemOfNewestData>>> call, @NonNull Response<BaseResponseEntity<List<ListItemOfNewestData>>>
                            response) {
                        BaseResponseEntity<List<ListItemOfNewestData>> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<List<ListItemOfNewestData>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });

                break;
            case GET_TARGET_SENSOR_NEWDATA:
                netWorkBusiness.getSensorNewestData(gateWayTag, apiTag, new Callback<BaseResponseEntity<SensorNewestData>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<SensorNewestData>> call, @NonNull Response<BaseResponseEntity<SensorNewestData>> response) {
                        BaseResponseEntity<SensorNewestData> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<SensorNewestData>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_TARGET_SENSOR_HISTORY_DATA:
                netWorkBusiness.getSensorHistoryData(gateWayTag, apiTag, new DeviceHistory(method, timeAgo), new Callback<BaseResponseEntity<List<ListItemOfSensorHistory>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<List<ListItemOfSensorHistory>>> call, @NonNull
                            Response<BaseResponseEntity<List<ListItemOfSensorHistory>>> response) {
                        BaseResponseEntity<List<ListItemOfSensorHistory>> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<List<ListItemOfSensorHistory>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_TARGET_SENSOR_PAGER_HISTORY_DATA:
                netWorkBusiness
                        .getPageSensorData(gateWayTag, apiTag, new Page(startTime, endTime, pageIndex, pageSize), new Callback<BaseResponseEntity<BasePager<PagerItemSensor>>>() {
                            @Override
                            public void onResponse(@NonNull Call<BaseResponseEntity<BasePager<PagerItemSensor>>> call, @NonNull
                                    Response<BaseResponseEntity<BasePager<PagerItemSensor>>> response) {
                                BaseResponseEntity<BasePager<PagerItemSensor>> baseResponseEntity = response.body();
                                if (baseResponseEntity != null) {
                                    tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                                } else {
                                    tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<BaseResponseEntity<BasePager<PagerItemSensor>>> call, @NonNull Throwable t) {
                                tvPostResult.setText("请求出错 : \n" + t.getMessage());
                            }
                        });
                break;
            case GET_TARGET_ACTUATOR_NEWDATA:
                netWorkBusiness.getActuatorNewestData(gateWayTag, apiTag, new Callback<BaseResponseEntity<ActuatorNewestData>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<ActuatorNewestData>> call, @NonNull Response<BaseResponseEntity<ActuatorNewestData>> response) {
                        BaseResponseEntity<ActuatorNewestData> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<ActuatorNewestData>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_TARGET_ACTUATOR_HISTORY_DATA:
                netWorkBusiness.getActuatorHistoryData(gateWayTag, apiTag, new DeviceHistory(method, timeAgo), new Callback<BaseResponseEntity<List<ListItemOfActuatorHistory>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<List<ListItemOfActuatorHistory>>> call, @NonNull
                            Response<BaseResponseEntity<List<ListItemOfActuatorHistory>>> response) {
                        BaseResponseEntity<List<ListItemOfActuatorHistory>> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<List<ListItemOfActuatorHistory>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_TARGET_ACTUATOR_PARGER_HISTORY_DATA:
                netWorkBusiness.getPageActuatorData(gateWayTag, apiTag, new Page(startTime, endTime, pageIndex, pageSize),
                        new Callback<BaseResponseEntity<BasePager<PagerItemActuator>>>() {
                            @Override
                            public void onResponse(@NonNull Call<BaseResponseEntity<BasePager<PagerItemActuator>>> call, @NonNull
                                    Response<BaseResponseEntity<BasePager<PagerItemActuator>>> response) {
                                BaseResponseEntity<BasePager<PagerItemActuator>> baseResponseEntity = response.body();
                                if (baseResponseEntity != null) {
                                    tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                                } else {
                                    tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<BaseResponseEntity<BasePager<PagerItemActuator>>> call, @NonNull Throwable t) {
                                tvPostResult.setText("请求出错 : \n" + t.getMessage());
                            }
                        });
                break;
            case CONTROL_TARGET_ACTUATOR:
                netWorkBusiness.controlActuator(gateWayTag, apiTag, controlValue, new Callback<BaseResponseEntity<String>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<String>> call, @NonNull Response<BaseResponseEntity<String>> response) {
                        BaseResponseEntity<String> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            tvPostResult.setText("请求结果 : \n" + baseResponseEntity.toString());
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<String>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;

        }
    }
}
