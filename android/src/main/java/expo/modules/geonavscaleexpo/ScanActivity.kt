package expo.modules.geonavscaleexpo

import android.R
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import cn.icomon.icdevicemanager.ICDeviceManager
import cn.icomon.icdevicemanager.callback.ICScanDeviceDelegate
import cn.icomon.icdevicemanager.model.device.ICScanDeviceInfo

class ScanActivity : Activity(), ICScanDeviceDelegate {
//    var adapter: ArrayAdapter<String>? = null
//    var devices = ArrayList<ICScanDeviceInfo>()
//    private val data = ArrayList<String>()

    init {
        EventMsg().addLog("ScanActivity init")
        ICDeviceManager.shared().scanDevice(this)
    }

    @Throws(Throwable::class)
    protected fun finalize() {
        println("finalize")
        EventMsg().addLog("finalize")
        ICDeviceManager.shared().stopScan()
        super.finish()

    }

    override fun onScanResult(deviceInfo: ICScanDeviceInfo) {
        println("onScanResult")
        println(deviceInfo)

        EventMsg().addLog("onScanResult")
        EventMsg().addLog(deviceInfo)
//        var isE = false
//        for (deviceInfo1 in _devices) {
//            if (deviceInfo1.getMacAddr().equals(deviceInfo.getMacAddr(), ignoreCase = true)) {
//                deviceInfo1.setRssi(deviceInfo.getRssi())
//                isE = true
//                break
//            }
//        }
//        if (!isE) {
//            _devices.add(deviceInfo)
//        }
//        data.clear()
//        for (deviceInfo1 in _devices) {
//            val str = deviceInfo1.getName() + "   " + deviceInfo1.getMacAddr() + "   " + deviceInfo1.getRssi()
//            data.add(str)
//        }
//        adapter!!.notifyDataSetChanged()
    }
}
