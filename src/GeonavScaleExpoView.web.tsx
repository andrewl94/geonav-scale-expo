import * as React from 'react';

import { GeonavScaleExpoViewProps } from './GeonavScaleExpo.types';

export default function GeonavScaleExpoView(props: GeonavScaleExpoViewProps) {
  return (
    <div>
      <span>{props.name}</span>
    </div>
  );
}
