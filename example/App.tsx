import {
  initialize,
  requestPermissions,
  startScan,
  stopScan,
} from "geonav-scale-expo";
import { useEffect, useState } from "react";
import {
  SafeAreaView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";

export default function App() {
  const [isPermissionAllowed, setIsPermissionAllowed] = useState(false);

  useEffect(() => {
    console.log('init permissions')
    checkPermissions();
  }, []);

  const checkPermissions = () => {
    requestPermissions().then((response) => {
      console.log(response);
      setIsPermissionAllowed(true);
      initialize();
    });
  };

  const startScanDevices = () => {
    startScan();
  };

  const stopScanDevices = () => {
    stopScan();
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.mainContent}>
        {isPermissionAllowed ? (
          <>
            <Text style={styles.stepsTitle}>
              Clique para iniciar a sincronizacao
            </Text>
          </>
        ) : (
          <Text style={styles.requestFont}>Carregando permissoes</Text>
        )}
      </View>
      <TouchableOpacity
        style={styles.ctaButton}
        onPress={isPermissionAllowed ? startScanDevices : checkPermissions}
      >
        <Text style={styles.ctaButtonText}>
          {isPermissionAllowed ? "Procurar balancas" : "Habilitar Permissoes"}
        </Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.ctaButton} onPress={stopScanDevices}>
        <Text style={styles.ctaButtonText}>Parar de procurar balancas</Text>
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
    fontSize: 24,
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
