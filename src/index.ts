import { PermissionsAndroid, Platform } from "react-native";

import GeonavScaleExpoModule from "./GeonavScaleExpoModule";
export function hello(): string {
  return GeonavScaleExpoModule.hello();
}

export function initialize() {
  GeonavScaleExpoModule.initialize();
}

export async function requestPermissions() {
  const result = await PermissionsAndroid.requestMultiple([
    PermissionsAndroid.PERMISSIONS.BLUETOOTH_SCAN,
    PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT,
    PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
    PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
    PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
    PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
  ]);
  console.log(result)
  // return GeonavScaleExpoModule.requestPermissions();
}

export async function setValueAsync(value: string) {
  return await GeonavScaleExpoModule.setValueAsync(value);
}
