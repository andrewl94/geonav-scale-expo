package expo.modules.geonavscaleexpo


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.icomon.icdevicemanager.ICDeviceManager
import cn.icomon.icdevicemanager.ICDeviceManager.REQUEST_ACCESS_COARSE_LOCATION
import cn.icomon.icdevicemanager.ICDeviceManagerDelegate
import cn.icomon.icdevicemanager.callback.ICScanDeviceDelegate
import cn.icomon.icdevicemanager.model.data.ICCoordData
import cn.icomon.icdevicemanager.model.data.ICKitchenScaleData
import cn.icomon.icdevicemanager.model.data.ICRulerData
import cn.icomon.icdevicemanager.model.data.ICSkipData
import cn.icomon.icdevicemanager.model.data.ICWeightCenterData
import cn.icomon.icdevicemanager.model.data.ICWeightData
import cn.icomon.icdevicemanager.model.data.ICWeightHistoryData
import cn.icomon.icdevicemanager.model.device.ICDevice
import cn.icomon.icdevicemanager.model.device.ICDeviceInfo
import cn.icomon.icdevicemanager.model.device.ICScanDeviceInfo
import cn.icomon.icdevicemanager.model.device.ICUserInfo
import cn.icomon.icdevicemanager.model.other.ICConstant
import cn.icomon.icdevicemanager.model.other.ICDeviceManagerConfig


class GeonavSDK: Activity(), ICScanDeviceDelegate, ICDeviceManagerDelegate, EventMsg.Event {

    private lateinit var deviceInfo: ICScanDeviceInfo
    private var unitIndex: Int? = 1
    private lateinit var device: ICDevice
    init {
        if (!checkBlePermission(this.baseContext)) {
            requestBlePermission(this);
        } else {
            initSDK();
        }
    }

    private fun initSDK(){
        val config = ICDeviceManagerConfig()

        val userInfo = ICUserInfo()
        userInfo.kitchenUnit = ICConstant.ICKitchenScaleUnit.ICKitchenScaleUnitG;
        userInfo.rulerUnit = ICConstant.ICRulerUnit.ICRulerUnitCM;
        userInfo.age = 29;
        userInfo.weight = 96.0;
        userInfo.height = 175;
        userInfo.sex = ICConstant.ICSexType.ICSexTypeMale;
        userInfo.userIndex = 1;
        userInfo.peopleType = ICConstant.ICPeopleType.ICPeopleTypeNormal;

        ICDeviceManager.shared().delegate = this;

        ICDeviceManager.shared().updateUserInfo(userInfo);
        ICDeviceManager.shared().initMgrWithConfig(config);
    }

    fun startScan(){
        ICDeviceManager.shared().scanDevice(this);
    }

    private fun requestBlePermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            val bPermission: Boolean = checkBlePermission(activity.applicationContext)
            if (!bPermission) {
                ActivityCompat.requestPermissions(activity, arrayOf<String>(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_ACCESS_COARSE_LOCATION)
            }
        }
    }

    private fun checkBlePermission(context: Context?): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val bPermission = ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            if (bPermission) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            return bPermission
        }
        return true
    }

    private fun addLog(data:Any?){
        print("GeonavScaleExpo Log:")
        println(data);
    }
    override fun onCallBack(obj: Any?) {
        deviceInfo = obj as ICScanDeviceInfo

        unitIndex = if (deviceInfo.getType() === ICConstant.ICDeviceType.ICDeviceTypeRuler) {
            2
        } else if (deviceInfo.getType() === ICConstant.ICDeviceType.ICDeviceTypeKitchenScale) {
            1
        } else if (deviceInfo.getType() === ICConstant.ICDeviceType.ICDeviceTypeFatScale
                || deviceInfo.getType() === ICConstant.ICDeviceType.ICDeviceTypeFatScaleWithTemperature) {
            0
        } else {
            -1
        }
        if (device == null) device = ICDevice()
        device.setMacAddr(deviceInfo.getMacAddr())

        ICDeviceManager.shared().addDevice(device) { device, _ -> addLog(device) }
    }
    override fun onScanResult(deviceInfo: ICScanDeviceInfo?) {
        addLog(deviceInfo)
    }

    override fun onInitFinish(bSuccess: Boolean) {
        addLog("On Init Finish $bSuccess")
    }

    override fun onBleState(state: ICConstant.ICBleState) {
        addLog("onBleState $state")
    }

    override fun onDeviceConnectionChanged(device: ICDevice?, state: ICConstant.ICDeviceConnectState?) {
        addLog("onDeviceConnectionChanged")
        addLog(device)
    }

    override fun onNodeConnectionChanged(device: ICDevice?, nodeId: Int, state: ICConstant.ICDeviceConnectState?) {
        addLog("onNodeConnectionChanged $nodeId")
    }

    override fun onReceiveWeightData(device: ICDevice?, data: ICWeightData?) {
        addLog("onNodeConnectionChanged")
        addLog(data)
    }

    override fun onReceiveKitchenScaleData(device: ICDevice?, data: ICKitchenScaleData?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveKitchenScaleUnitChanged(device: ICDevice?, unit: ICConstant.ICKitchenScaleUnit?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveCoordData(device: ICDevice?, data: ICCoordData?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveRulerData(device: ICDevice?, data: ICRulerData?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveRulerHistoryData(device: ICDevice?, data: ICRulerData?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveWeightCenterData(device: ICDevice?, data: ICWeightCenterData?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveWeightUnitChanged(device: ICDevice?, unit: ICConstant.ICWeightUnit?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveRulerUnitChanged(device: ICDevice?, unit: ICConstant.ICRulerUnit?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveRulerMeasureModeChanged(device: ICDevice?, mode: ICConstant.ICRulerMeasureMode?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveMeasureStepData(device: ICDevice?, step: ICConstant.ICMeasureStep?, data: Any?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveWeightHistoryData(device: ICDevice?, data: ICWeightHistoryData?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveSkipData(device: ICDevice?, data: ICSkipData?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveHistorySkipData(device: ICDevice?, data: ICSkipData?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveSkipBattery(device: ICDevice?, battery: Int) {
        TODO("Not yet implemented")
    }

    override fun onReceiveBattery(device: ICDevice?, battery: Int) {
        TODO("Not yet implemented")
    }

    override fun onReceiveUpgradePercent(device: ICDevice?, status: ICConstant.ICUpgradeStatus?, percent: Int) {
        TODO("Not yet implemented")
    }

    override fun onReceiveDeviceInfo(device: ICDevice?, deviceInfo: ICDeviceInfo?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveDebugData(device: ICDevice?, type: Int, obj: Any?) {
        TODO("Not yet implemented")
    }

    override fun onReceiveConfigWifiResult(device: ICDevice?, state: ICConstant.ICConfigWifiState?) {
        TODO("Not yet implemented")
    }


}