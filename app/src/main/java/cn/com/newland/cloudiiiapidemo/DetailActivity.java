package cn.com.newland.cloudiiiapidemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.com.newland.cloudiiiapidemo.util.DataCache;
import cn.com.newland.nle_sdk.postDataSet.PostEnum;
import cn.com.newland.nle_sdk.requestEntity.DeviceData;
import cn.com.newland.nle_sdk.requestEntity.DeviceElement;
import cn.com.newland.nle_sdk.responseEntity.Device;
import cn.com.newland.nle_sdk.responseEntity.DeviceState;
import cn.com.newland.nle_sdk.responseEntity.ListItemOfDevice;
import cn.com.newland.nle_sdk.responseEntity.ProjectInfo;
import cn.com.newland.nle_sdk.responseEntity.TargetSensorInfo;
import cn.com.newland.nle_sdk.responseEntity.base.BasePager;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import cn.com.newland.nle_sdk.util.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends BaseActivity {
    private EditText etApiTag;
    private EditText etProjectTag;
    private EditText etMethod;
    private EditText etTimeAgo;
    private EditText etStartTime;
    private EditText etEndTime;
    private EditText etPageIndex;
    private EditText etPageSize;
    private EditText etData;
    private EditText etProjectId;
    private EditText etDeviceId;
    private EditText etKeyWord;
    private EditText etProtocol;
    private EditText etProjectIdOrTag;
    private EditText etDeviceName;
    private EditText etDeviceTag;
    private EditText etDeviceCoordinate;
    private EditText etDeviceDeviceJImg;
    private EditText etSensorName;
    private EditText etTransType;
    private EditText etDataType;
    private EditText etTypeAttrs;
    private EditText etUnit;
    private EditText etPrecision;
    private EditText etOperType;
    private EditText etOperTypeAttrs;
    private EditText etSerialNumber;
    private EditText etHttpIp;
    private EditText etHttpPort;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etValue;
    private EditText etRecordTime;
    private EditText etSort;
    private EditText etNetWorkKind;

    private CheckBox cbOnLine;
    private CheckBox cbShare;
    private CheckBox cbIsTrans;

    private RadioGroup rgDeviceType;

    private TextView tvRequestUrl;
    private TextView tvPostResult;
    private PostEnum postEnum;

    @Override
    protected void onFirst(Bundle saveInstanceState) {
        super.onFirst(saveInstanceState);
        postEnum = (PostEnum) getIntent().getSerializableExtra("apiItem");

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
        etApiTag = findViewById(R.id.apiTag);
        etProjectTag = findViewById(R.id.project_tag);
        etMethod = findViewById(R.id.method);
        etTimeAgo = findViewById(R.id.timeAgo);
        etStartTime = findViewById(R.id.startTime);
        etEndTime = findViewById(R.id.endTime);
        etDeviceId = findViewById(R.id.device_id);
        etPageIndex = findViewById(R.id.PageIndex);
        etPageSize = findViewById(R.id.PageSize);
        etData = findViewById(R.id.Data);
        etKeyWord = findViewById(R.id.key_word);
        etProtocol = findViewById(R.id.Protocol);
        etProjectIdOrTag = findViewById(R.id.ProjectIdOrTag);
        etDeviceName = findViewById(R.id.device_name);
        etDeviceTag = findViewById(R.id.device_tag);
        etDeviceCoordinate = findViewById(R.id.device_Coordinate);
        etDeviceDeviceJImg = findViewById(R.id.device_DeviceImg);
        etSensorName = findViewById(R.id.sensor_name);
        etTransType = findViewById(R.id.TransType);
        etDataType = findViewById(R.id.DataType);
        etTypeAttrs = findViewById(R.id.TypeAttrs);
        etUnit = findViewById(R.id.Unit);
        etPrecision = findViewById(R.id.Precision);
        etOperType = findViewById(R.id.OperType);
        etOperTypeAttrs = findViewById(R.id.OperTypeAttrs);
        etSerialNumber = findViewById(R.id.SerialNumber);
        etHttpIp = findViewById(R.id.HttpIp);
        etHttpPort = findViewById(R.id.HttpPort);
        etUserName = findViewById(R.id.UserName);
        etPassword = findViewById(R.id.Password);
        etValue = findViewById(R.id.Value);
        etRecordTime = findViewById(R.id.RecordTime);
        etSort = findViewById(R.id.Sort);
        etNetWorkKind = findViewById(R.id.NetWorkKind);

        cbOnLine = findViewById(R.id.cb_on_line);
        cbShare = findViewById(R.id.cb_is_share);
        cbIsTrans = findViewById(R.id.cb_IsTrans);

        rgDeviceType = findViewById(R.id.device_type);

        etProjectId = findViewById(R.id.project_id);
        tvRequestUrl = findViewById(R.id.requestUrl);
        tvPostResult = findViewById(R.id.postResult);
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        tvRequestUrl.setText(String.format("请求的url地址为: %s%s", DataCache.getBaseUrl(getApplicationContext()), postEnum.getUrl()));
        switch (postEnum) {
            case GET_PROJECT:
                etProjectId.setVisibility(View.VISIBLE);
                break;
            case GET_PROJECTS:
                etKeyWord.setVisibility(View.VISIBLE);
                etProjectTag.setVisibility(View.VISIBLE);
                etNetWorkKind.setVisibility(View.VISIBLE);
                etPageSize.setVisibility(View.VISIBLE);
                etStartTime.setVisibility(View.VISIBLE);
                etEndTime.setVisibility(View.VISIBLE);
                etPageIndex.setVisibility(View.VISIBLE);
                break;
            case GET_SENSORS:
                etProjectId.setVisibility(View.VISIBLE);
                break;


            case GET_DEVICE_NEWS_DATA:
                etDeviceId.setVisibility(View.VISIBLE);
                break;
            case GET_DEVICE_STATES:
                etDeviceId.setVisibility(View.VISIBLE);
                break;
            case GET_DEVICE:
                etDeviceId.setVisibility(View.VISIBLE);
                break;
            case GET_DEVICES:
                etKeyWord.setVisibility(View.VISIBLE);
                etDeviceId.setVisibility(View.VISIBLE);
                etDeviceTag.setVisibility(View.VISIBLE);
                cbShare.setVisibility(View.VISIBLE);
                cbOnLine.setVisibility(View.VISIBLE);
                etPageIndex.setVisibility(View.VISIBLE);
                etPageSize.setVisibility(View.VISIBLE);
                etStartTime.setVisibility(View.VISIBLE);
                etEndTime.setVisibility(View.VISIBLE);
                break;
            case POST_ADD_DEVICE:
                etProtocol.setVisibility(View.VISIBLE);
                cbIsTrans.setVisibility(View.VISIBLE);
                etProjectIdOrTag.setVisibility(View.VISIBLE);
                etDeviceName.setVisibility(View.VISIBLE);
                etDeviceTag.setVisibility(View.VISIBLE);
                etDeviceCoordinate.setVisibility(View.VISIBLE);
                etDeviceDeviceJImg.setVisibility(View.VISIBLE);
                cbShare.setVisibility(View.VISIBLE);
                break;
            case PUT_UPDATE_DEVICE:
                etDeviceId.setVisibility(View.VISIBLE);
                etProtocol.setVisibility(View.VISIBLE);
                cbIsTrans.setVisibility(View.VISIBLE);
                etProjectIdOrTag.setVisibility(View.VISIBLE);
                etDeviceName.setVisibility(View.VISIBLE);
                etDeviceTag.setVisibility(View.VISIBLE);
                etDeviceCoordinate.setVisibility(View.VISIBLE);
                etDeviceDeviceJImg.setVisibility(View.VISIBLE);
                cbShare.setVisibility(View.VISIBLE);
                break;
            case DELETE_DEVICE:
                etDeviceId.setVisibility(View.VISIBLE);
                break;

            case GET_SENSOR:
                etDeviceId.setVisibility(View.VISIBLE);
                etApiTag.setVisibility(View.VISIBLE);
                break;
            case GET_SENSORS_BY_DEVICE:
                etDeviceId.setVisibility(View.VISIBLE);
                etApiTag.setVisibility(View.VISIBLE);
                break;
            case POST_ADD_SENSOR:
                rgDeviceType.setVisibility(View.VISIBLE);

                etDeviceId.setVisibility(View.VISIBLE);
                etSensorName.setVisibility(View.VISIBLE);
                etApiTag.setVisibility(View.VISIBLE);
                etTransType.setVisibility(View.VISIBLE);
                etDataType.setVisibility(View.VISIBLE);
                etTypeAttrs.setVisibility(View.VISIBLE);
                etUnit.setVisibility(View.VISIBLE);
                etPrecision.setVisibility(View.VISIBLE);
                etOperType.setVisibility(View.VISIBLE);
                etOperTypeAttrs.setVisibility(View.VISIBLE);
                etSerialNumber.setVisibility(View.VISIBLE);
                etHttpIp.setVisibility(View.VISIBLE);
                etHttpPort.setVisibility(View.VISIBLE);
                etUserName.setVisibility(View.VISIBLE);
                etPassword.setVisibility(View.VISIBLE);
                break;
            case PUT_UPDATE_SENSOR:
                rgDeviceType.setVisibility(View.VISIBLE);

                etDeviceId.setVisibility(View.VISIBLE);
                etApiTag.setVisibility(View.VISIBLE);
                etSensorName.setVisibility(View.VISIBLE);
                etTransType.setVisibility(View.VISIBLE);
                etDataType.setVisibility(View.VISIBLE);
                etTypeAttrs.setVisibility(View.VISIBLE);

                etUnit.setVisibility(View.VISIBLE);
                etPrecision.setVisibility(View.VISIBLE);

                etOperType.setVisibility(View.VISIBLE);
                etOperTypeAttrs.setVisibility(View.VISIBLE);
                etSerialNumber.setVisibility(View.VISIBLE);

                etHttpIp.setVisibility(View.VISIBLE);
                etHttpPort.setVisibility(View.VISIBLE);
                etUserName.setVisibility(View.VISIBLE);
                etPassword.setVisibility(View.VISIBLE);
                break;
            case DELETE_SENSOR:
                etDeviceId.setVisibility(View.VISIBLE);
                etApiTag.setVisibility(View.VISIBLE);
                break;

            case POST_ADD_SENSOR_DATA:
                etDeviceId.setVisibility(View.VISIBLE);
                etValue.setVisibility(View.VISIBLE);
                etApiTag.setVisibility(View.VISIBLE);
                etRecordTime.setVisibility(View.VISIBLE);
                break;
            case GET_SENSOR_DATA:
                etDeviceId.setVisibility(View.VISIBLE);
                etApiTag.setVisibility(View.VISIBLE);
                etMethod.setVisibility(View.VISIBLE);
                etTimeAgo.setVisibility(View.VISIBLE);
                etStartTime.setVisibility(View.VISIBLE);
                etEndTime.setVisibility(View.VISIBLE);
                etSort.setVisibility(View.VISIBLE);
                etPageSize.setVisibility(View.VISIBLE);
                etPageIndex.setVisibility(View.VISIBLE);

                break;


            case POST_CONTROL:
                etDeviceId.setVisibility(View.VISIBLE);
                etApiTag.setVisibility(View.VISIBLE);
                etData.setVisibility(View.VISIBLE);
                break;


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
        String projectId = etProjectId.getText().toString();
        String deviceId = etDeviceId.getText().toString();
        String keyword = etKeyWord.getText().toString();
        String projectTag = etProjectTag.getText().toString();
        String deviceTag = etDeviceTag.getText().toString();
        String Protocol = etProtocol.getText().toString();
        String projectIdOrTag = etProjectIdOrTag.getText().toString();
        String networkKind = etNetWorkKind.getText().toString();
        String deviceName = etDeviceName.getText().toString();
        String Coordinate = etDeviceCoordinate.getText().toString();
        String deviceElementName = etSensorName.getText().toString();
        String TransType = etTransType.getText().toString();
        String DataType = etDataType.getText().toString();
        String TypeAttrs = etTypeAttrs.getText().toString();
        String Unit = etUnit.getText().toString();
        String Precision = etPrecision.getText().toString();
        String OperType = etOperType.getText().toString();
        String OperTypeAttrs = etOperTypeAttrs.getText().toString();
        String SerialNumber = etSerialNumber.getText().toString();
        String HttpIp = etHttpIp.getText().toString();
        String HttpPort = etHttpPort.getText().toString();
        String UserName = etUserName.getText().toString();
        String Password = etPassword.getText().toString();
        String value = etValue.getText().toString();
        String recordTime = etRecordTime.getText().toString();
        String DeviceImg = etDeviceDeviceJImg.getText().toString();
        String apiTag = etApiTag.getText().toString();
        String method = etMethod.getText().toString();
        String sort = etSort.getText().toString();
        String timeAgo = etTimeAgo.getText().toString();
        String startTime = etStartTime.getText().toString();
        String endTime = etEndTime.getText().toString();
        String pageIndex = etPageIndex.getText().toString();
        String pageSize = etPageSize.getText().toString();

        final Gson gson = new Gson();
        switch (postEnum) {
            case GET_PROJECT:
                netWorkBusiness.getProject(projectId, new Callback<BaseResponseEntity<ProjectInfo>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<ProjectInfo>> call, @NonNull Response<BaseResponseEntity<ProjectInfo>> response) {
                        BaseResponseEntity<ProjectInfo> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<ProjectInfo>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_PROJECTS:
                netWorkBusiness.getProjects(keyword, projectTag, networkKind, pageSize, startTime, endTime, pageIndex, new Callback<BaseResponseEntity<BasePager<ProjectInfo>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<BasePager<ProjectInfo>>> call, @NonNull Response<BaseResponseEntity<BasePager<ProjectInfo>>> response) {
                        BaseResponseEntity<BasePager<ProjectInfo>> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<BasePager<ProjectInfo>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_SENSORS:
                netWorkBusiness.getAllSensors(projectId, new Callback<BaseResponseEntity<List<TargetSensorInfo>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<List<TargetSensorInfo>>> call, @NonNull Response<BaseResponseEntity<List<TargetSensorInfo>>> response) {
                        BaseResponseEntity<List<TargetSensorInfo>> baseResponseEntity = response.body();
                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<List<TargetSensorInfo>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_DEVICE_NEWS_DATA:
                netWorkBusiness.getDevicesDatas(deviceId, new Callback<BaseResponseEntity<List<ListItemOfDevice>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<List<ListItemOfDevice>>> call, @NonNull Response<BaseResponseEntity<List<ListItemOfDevice>>> response) {
                        BaseResponseEntity<List<ListItemOfDevice>> baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<List<ListItemOfDevice>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_DEVICE_STATES:
                netWorkBusiness.getBatchOnLine(deviceId, new Callback<BaseResponseEntity<List<DeviceState>>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<List<DeviceState>>> call, @NonNull Response<BaseResponseEntity<List<DeviceState>>> response) {
                        BaseResponseEntity<List<DeviceState>> baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<List<DeviceState>>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_DEVICE:
                netWorkBusiness.getDeviceInfo(deviceId, new Callback<BaseResponseEntity<Device>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<Device>> call, @NonNull Response<BaseResponseEntity<Device>> response) {
                        BaseResponseEntity<Device> baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<Device>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_DEVICES:
                netWorkBusiness.getDeviceFuzzy(keyword, deviceId, deviceTag, cbOnLine.isChecked() + "", cbShare.isChecked() + "", "", pageSize, startTime, endTime, pageIndex,
                                               new Callback<BaseResponseEntity<BasePager<Device>>>() {
                                                   @Override
                                                   public void onResponse(@NonNull Call<BaseResponseEntity<BasePager<Device>>> call, @NonNull
                                                           Response<BaseResponseEntity<BasePager<Device>>> response) {
                                                       BaseResponseEntity<BasePager<Device>> baseResponseEntity = response.body();

                                                       if (baseResponseEntity != null) {
                                                           Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                                                       } else {
                                                           tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                                                       }

                                                   }

                                                   @Override
                                                   public void onFailure(@NonNull Call<BaseResponseEntity<BasePager<Device>>> call, @NonNull Throwable t) {
                                                       tvPostResult.setText("请求出错 : \n" + t.getMessage());
                                                   }
                                               });
                break;
            case POST_ADD_DEVICE:
                Device device = new Device(deviceName, deviceTag, Protocol, Coordinate, cbShare.isChecked() + "", cbIsTrans.isChecked() + "", "", cbOnLine.isChecked() + "",
                                           projectIdOrTag, DeviceImg);
                netWorkBusiness.postAddDevice(device, new Callback<BaseResponseEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                        BaseResponseEntity<BasePager> baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case PUT_UPDATE_DEVICE:
                Device device1 = new Device(deviceName, deviceTag, Protocol, Coordinate, cbShare.isChecked() + "", cbIsTrans.isChecked() + "", "", cbShare.isChecked() + "",
                                            projectIdOrTag, DeviceImg);
                netWorkBusiness.updateDevice(deviceId, device1, new Callback<BaseResponseEntity<Object>>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity<Object>> call, @NonNull Response<BaseResponseEntity<Object>> response) {
                        BaseResponseEntity baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity<Object>> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case DELETE_DEVICE:
                netWorkBusiness.deleteDevice(deviceId, new Callback<BaseResponseEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                        BaseResponseEntity baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_SENSOR:
                netWorkBusiness.getSensor(deviceId, apiTag, new Callback<BaseResponseEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                        BaseResponseEntity baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_SENSORS_BY_DEVICE:
                netWorkBusiness.getSensors(deviceId, apiTag, new Callback<BaseResponseEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                        BaseResponseEntity baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case POST_ADD_SENSOR:
                DeviceElement deviceElement = null;
                switch (rgDeviceType.getCheckedRadioButtonId()) {
                    case R.id.rb_sensor:
                        deviceElement = new DeviceElement.SensorDeviceElement(deviceElementName, TransType, DataType, TypeAttrs, Unit, Precision);
                        break;
                    case R.id.rb_actuator:
                        deviceElement = new DeviceElement.ActuatorDeviceElement(deviceElementName, TransType, DataType, TypeAttrs, OperType, OperTypeAttrs, SerialNumber);
                        break;
                    case R.id.rb_camera:
                        deviceElement = new DeviceElement.CameraDeviceElement(deviceElementName, TransType, DataType, TypeAttrs, HttpIp, HttpPort, UserName, Password);
                        break;
                }


                netWorkBusiness.addSensor(deviceId, deviceElement, new Callback<BaseResponseEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                        BaseResponseEntity baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case PUT_UPDATE_SENSOR:
                switch (rgDeviceType.getCheckedRadioButtonId()) {
                    case R.id.rb_sensor:
                        DeviceElement.SensorDeviceElement sensorDeviceElement = new DeviceElement.SensorDeviceElement(deviceElementName, TransType, DataType, TypeAttrs, Unit,
                                                                                                                      Precision);
                        netWorkBusiness.updateSensor(deviceId, apiTag, sensorDeviceElement, new Callback<BaseResponseEntity>() {
                            @Override
                            public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                                BaseResponseEntity baseResponseEntity = response.body();

                                if (baseResponseEntity != null) {
                                    Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                                } else {
                                    tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                                tvPostResult.setText("请求出错 : \n" + t.getMessage());
                            }
                        });
                        break;
                    case R.id.rb_actuator:
                        DeviceElement.ActuatorDeviceElement actuatorDeviceElement = new DeviceElement.ActuatorDeviceElement(deviceElementName, TransType, DataType, TypeAttrs,
                                                                                                                            OperType, OperTypeAttrs, SerialNumber);

                        netWorkBusiness.updateActuator(deviceId, apiTag, actuatorDeviceElement, new Callback<BaseResponseEntity>() {
                            @Override
                            public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                                BaseResponseEntity baseResponseEntity = response.body();

                                if (baseResponseEntity != null) {
                                    Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                                } else {
                                    tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                                tvPostResult.setText("请求出错 : \n" + t.getMessage());
                            }
                        });
                        break;
                    case R.id.rb_camera:
                        DeviceElement.CameraDeviceElement cameraDeviceElement = new DeviceElement.CameraDeviceElement(deviceElementName, TransType, DataType, TypeAttrs, HttpIp,
                                                                                                                      HttpPort, UserName, Password);
                        netWorkBusiness.updateCamera(deviceId, apiTag, cameraDeviceElement, new Callback<BaseResponseEntity>() {
                            @Override
                            public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                                BaseResponseEntity baseResponseEntity = response.body();

                                if (baseResponseEntity != null) {
                                    Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                                } else {
                                    tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                                tvPostResult.setText("请求出错 : \n" + t.getMessage());
                            }
                        });
                        break;
                }
                break;

            case DELETE_SENSOR:
                netWorkBusiness.deleteDeviceElement(deviceId, apiTag, new Callback<BaseResponseEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                        BaseResponseEntity baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case POST_ADD_SENSOR_DATA:
                List<DeviceData.DatasDTO> datasDTOS = new ArrayList<>();
                List<DeviceData.PointDTO> pointDTOS = new ArrayList<>();
                pointDTOS.add(new DeviceData.PointDTO(value, recordTime));
                datasDTOS.add(new DeviceData.DatasDTO(apiTag, pointDTOS));
                DeviceData deviceData = new DeviceData(datasDTOS);
                netWorkBusiness.addSensorData(deviceId, deviceData, new Callback<BaseResponseEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                        BaseResponseEntity baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case GET_SENSOR_DATA:
                netWorkBusiness.getSensorData(deviceId, apiTag, method, timeAgo, startTime, endTime, sort, pageSize, pageIndex, new Callback<BaseResponseEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                        BaseResponseEntity baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
            case POST_CONTROL:
                netWorkBusiness.control(deviceId, apiTag, value, new Callback<BaseResponseEntity>() {
                    @Override
                    public void onResponse(@NonNull Call<BaseResponseEntity> call, @NonNull Response<BaseResponseEntity> response) {
                        BaseResponseEntity baseResponseEntity = response.body();

                        if (baseResponseEntity != null) {
                            Tools.printJson(tvPostResult, gson.toJson(baseResponseEntity));
                        } else {
                            tvPostResult.setText("请求出错 : 请求参数不合法或者服务出错");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<BaseResponseEntity> call, @NonNull Throwable t) {
                        tvPostResult.setText("请求出错 : \n" + t.getMessage());
                    }
                });
                break;
        }
    }
}