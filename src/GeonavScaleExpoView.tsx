import { requireNativeViewManager } from 'expo-modules-core';
import * as React from 'react';

import { GeonavScaleExpoViewProps } from './GeonavScaleExpo.types';

const NativeView: React.ComponentType<GeonavScaleExpoViewProps> =
  requireNativeViewManager('GeonavScaleExpo');

export default function GeonavScaleExpoView(props: GeonavScaleExpoViewProps) {
  return <NativeView {...props} />;
}
