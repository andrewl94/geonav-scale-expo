package expo.modules.geonavscaleexpo

class EventMsg {
    var events = HashMap<String, Event>()

    interface Event {
        fun onCallBack(obj: Any?)
    }

    fun addEvent(name: String, event: Event) {
        events[name] = event
    }

    fun post(name: String, obj: Any?) {
        if (events.containsKey(name)) {
            events[name]!!.onCallBack(obj)
        }
    }

    fun addLog(data:Any?){
        print("GeonavScaleExpoEvent: ");
        println(data);
    }
}