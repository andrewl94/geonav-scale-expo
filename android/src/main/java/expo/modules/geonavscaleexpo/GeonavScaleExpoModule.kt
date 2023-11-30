package expo.modules.geonavscaleexpo

import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
class GeonavScaleExpoModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("GeonavScaleExpo")

    Events("onChange")

    Events("foundDevices")

    Function("hello") {
      "Hello world! 👋"
    }

    Function("init") {
      TODO("Init SDK")
    }

    Function("scan") {
      TODO("Scan SDK")
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
}
