import { initialize, requestPermissions } from "geonav-scale-expo";
import { useState } from "react";
import {
  SafeAreaView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";

// GeonavScaleExpo.initialize();
export default function App() {
  const [isPermissionAllowed, setIsPermissionAllowed] = useState(false);

  const requestPermissionsRN = () => {
    requestPermissions();
    setIsPermissionAllowed(true);
  };

  const scanDevices = () => {
    //TODO: Scan Devices
    initialize()
    console.log("Scan Devices FE requestd");
  };

  const stopTracking = () => {
    console.log("stopTracking FE requestd");
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.mainContent}>
        {isPermissionAllowed ? (
          <>
            <Text style={styles.stepsTitle}>Permissions loaded</Text>
          </>
        ) : (
          <Text style={styles.requestFont}>Please Enable Permissions</Text>
        )}
      </View>
      <TouchableOpacity
        style={styles.ctaButton}
        onPress={isPermissionAllowed ? scanDevices : requestPermissionsRN}
      >
        <Text style={styles.ctaButtonText}>
          {isPermissionAllowed ? "Start Tracking" : "Request Permissions"}
        </Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.ctaButton} onPress={stopTracking}>
        <Text style={styles.ctaButtonText}>Stop Tracking</Text>
      </TouchableOpacity>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
  },
  mainContent: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    marginHorizontal: 25,
  },
  requestFont: {
    fontWeight: "bold",
    fontSize: 25,
    textAlign: "center",
  },
  stepsFont: {
    fontSize: 224,
    fontWeight: "300",
  },
  stepsTitle: {
    fontSize: 50,
    fontWeight: "bold",
  },
  ctaButton: {
    height: 60,
    borderRadius: 8,
    backgroundColor: "purple",
    justifyContent: "center",
    alignItems: "center",
    marginHorizontal: 25,
    marginBottom: 10,
  },
  ctaButtonText: {
    color: "white",
    fontSize: 18,
    fontWeight: "bold",
  },
});
