package expo.modules.geonavscaleexpo

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat.startActivity
import cn.icomon.icdevicemanager.ICDeviceManager
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
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition


const val kRequestCode = 78455321

private lateinit var deviceInfo: ICScanDeviceInfo
private var unitIndex: Int? = 1
private lateinit var device: ICDevice

private var loaded = false

private var scanning = false

class GeonavScaleExpoModule : Module(), ICDeviceManagerDelegate, EventMsg.Event, ICScanDeviceDelegate {
  override fun definition() = ModuleDefinition {
    Name("GeonavScaleExpo")

    Events("onChange")

    Events("foundDevices")

    Function("hello") {
      "Hello world! 👋"
    }

    Function("initialize") {
//      GeonavSDK(appContext)
//        println("Initialization requested")
//        var clazz = ICDeviceManager::class.java
//        println(clazz)
//        val packageName = clazz.`package`?.name
//        println("Package Path: $packageName")
//        ICLoggerHandler.logInfo("ICDMGR", "deinit sdk", *arrayOfNulls(0))
//        GeonavSDK()

        initSDK()

    }

    Function("startScan") {
      startScan()
    }

    Function("stopScan") {
      stopScan()

    }

    AsyncFunction("scannerResponse") { value: String ->
      sendEvent("foundDevices", mapOf(
              "value" to value
      ))
    }

    AsyncFunction("setValueAsync") { value: String ->
      sendEvent("onChange", mapOf(
        "value" to value
      ))
    }

  }


    private fun initSDK(){
        addLog("Init SDK Start")
        EventMsg().addEvent("SCAN", this);

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

        ICDeviceManager.shared().setDelegate(this);
        config.setContext(appContext.currentActivity)
        ICDeviceManager.shared().updateUserInfo(userInfo);
        ICDeviceManager.shared().initMgrWithConfig(config);


        addLog("Init SDK Finalized")

    }

    private fun startScan(){
        addLog("startScan")

        if(!isSdkReady() || scanning)
            return
//        val scanner = ScanActivity()


//        scanner.onCreate(bundleOf(appContext.currentActivity.applicationContext))
        val intent = Intent(appContext.currentActivity, ScanActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        appContext.currentActivity?.let { startActivity(it.applicationContext,intent,null) }
        scanning = true

//        device = ICDevice()

//        device.setMacAddr("DC:23:4E:71:BD:22")

//        ICDeviceManager.shared().addDevice(device) { device, _ -> addLog(device) }

//        ICDeviceManager.shared().scanDevice(this);
    }

    private fun stopScan(){
        addLog("stopScan")

        if(!isSdkReady() || !scanning)
            return
        scanning = false
        ICDeviceManager.shared().stopScan();
    }

    private fun addLog(data:Any?){
        print("GeonavScaleExpoEvent: ");
        println(data);
    }
    override fun onCallBack(obj: Any?) {
        addLog("onCallBack scan")
        addLog(obj)
        deviceInfo = obj as ICScanDeviceInfo
        addLog(deviceInfo)
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

        addLog(device)

        ICDeviceManager.shared().addDevice(device) { device, _ -> addLog(device) }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        addLog("onRequestPermissionsResult")
        addLog(permissions)
    }
    init {
        addLog("Module Init")
    }

    private fun isSdkReady() : Boolean{
        if(!loaded){
            addLog("SDK Not Ready")
        }
        return loaded
    }

    override fun onScanResult(deviceInfo: ICScanDeviceInfo?) {
        addLog("On Scan Result")
        addLog(deviceInfo)
    }

    override fun onInitFinish(bSuccess: Boolean) {
        addLog("On Init Finish $bSuccess")
        loaded = true
    }

    override fun onBleState(state: ICConstant.ICBleState) {
        addLog("onBleState $state")
        addLog( state)

    }

    override fun onDeviceConnectionChanged(device: ICDevice?, state: ICConstant.ICDeviceConnectState?) {
        addLog("onDeviceConnectionChanged")
        addLog(device)
    }

    override fun onNodeConnectionChanged(device: ICDevice?, nodeId: Int, state: ICConstant.ICDeviceConnectState?) {
        addLog("onNodeConnectionChanged $nodeId")
    }

    override fun onReceiveWeightData(device: ICDevice?, data: ICWeightData?) {
        addLog("onReceiveWeightData")
        addLog(data)
    }

    override fun onReceiveKitchenScaleData(device: ICDevice?, data: ICKitchenScaleData?) {
        addLog("onReceiveKitchenScaleData")
        addLog(device)
    }

    override fun onReceiveKitchenScaleUnitChanged(device: ICDevice?, unit: ICConstant.ICKitchenScaleUnit?) {
        addLog("onReceiveKitchenScaleUnitChanged")
        addLog(device)
    }

    override fun onReceiveCoordData(device: ICDevice?, data: ICCoordData?) {
        addLog("onReceiveCoordData")
        addLog(device)
    }

    override fun onReceiveRulerData(device: ICDevice?, data: ICRulerData?) {
        addLog("onReceiveRulerData")
        addLog(device)
    }

    override fun onReceiveRulerHistoryData(device: ICDevice?, data: ICRulerData?) {
        addLog("onReceiveRulerHistoryData")
        addLog(device)
    }

    override fun onReceiveWeightCenterData(device: ICDevice?, data: ICWeightCenterData?) {
        addLog("onReceiveWeightCenterData")
        addLog(device)
    }

    override fun onReceiveWeightUnitChanged(device: ICDevice?, unit: ICConstant.ICWeightUnit?) {
        addLog("onReceiveWeightUnitChanged")
        addLog(device)
    }

    override fun onReceiveRulerUnitChanged(device: ICDevice?, unit: ICConstant.ICRulerUnit?) {
        addLog("onReceiveRulerUnitChanged")
        addLog(device)
    }

    override fun onReceiveRulerMeasureModeChanged(device: ICDevice?, mode: ICConstant.ICRulerMeasureMode?) {
        addLog("onReceiveRulerMeasureModeChanged")
        addLog(device)
    }

    override fun onReceiveMeasureStepData(device: ICDevice?, step: ICConstant.ICMeasureStep?, data: Any?) {
        addLog("onReceiveMeasureStepData")
        addLog(device)
    }
    override fun onReceiveWeightHistoryData(device: ICDevice?, data: ICWeightHistoryData?) {
        addLog("onReceiveWeightHistoryData")
        addLog(device)
    }

    override fun onReceiveSkipData(device: ICDevice?, data: ICSkipData?) {
        addLog("onReceiveSkipData")
        addLog(device)
    }

    override fun onReceiveHistorySkipData(device: ICDevice?, data: ICSkipData?) {
        addLog("onReceiveHistorySkipData")
        addLog(device)
    }

    override fun onReceiveSkipBattery(device: ICDevice?, battery: Int) {
        addLog("onReceiveSkipBattery")
        addLog(device)
    }

    override fun onReceiveBattery(device: ICDevice?, battery: Int) {
        addLog("onReceiveBattery")
        addLog(device)
    }

    override fun onReceiveUpgradePercent(device: ICDevice?, status: ICConstant.ICUpgradeStatus?, percent: Int) {
        addLog("onReceiveUpgradePercent")
        addLog(device)
    }

    override fun onReceiveDeviceInfo(device: ICDevice?, deviceInfo: ICDeviceInfo?) {
        addLog("onReceiveDeviceInfo")
        addLog(device)
    }

    override fun onReceiveDebugData(device: ICDevice?, type: Int, obj: Any?) {
        addLog("onReceiveDebugData")
        addLog(device)
    }

    override fun onReceiveConfigWifiResult(device: ICDevice?, state: ICConstant.ICConfigWifiState?) {
        addLog("onReceiveConfigWifiResult")
        addLog(device)
    }


    @Throws(Throwable::class)
    protected fun finalize() {
        ICDeviceManager.shared().stopScan()
    }

}
