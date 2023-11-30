import { StyleSheet, Text, View } from 'react-native';

import * as GeonavScaleExpo from 'geonav-scale-expo';

export default function App() {
  return (
    <View style={styles.container}>
      <Text>{GeonavScaleExpo.hello()}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
