import { NativeModulesProxy, EventEmitter, Subscription } from 'expo-modules-core';

// Import the native module. On web, it will be resolved to GeonavScaleExpo.web.ts
// and on native platforms to GeonavScaleExpo.ts
import GeonavScaleExpoModule from './GeonavScaleExpoModule';
import GeonavScaleExpoView from './GeonavScaleExpoView';
import { ChangeEventPayload, GeonavScaleExpoViewProps } from './GeonavScaleExpo.types';

// Get the native constant value.
export const PI = GeonavScaleExpoModule.PI;

export function hello(): string {
  return GeonavScaleExpoModule.hello();
}

export async function setValueAsync(value: string) {
  return await GeonavScaleExpoModule.setValueAsync(value);
}

const emitter = new EventEmitter(GeonavScaleExpoModule ?? NativeModulesProxy.GeonavScaleExpo);

export function addChangeListener(listener: (event: ChangeEventPayload) => void): Subscription {
  return emitter.addListener<ChangeEventPayload>('onChange', listener);
}

export { GeonavScaleExpoView, GeonavScaleExpoViewProps, ChangeEventPayload };
