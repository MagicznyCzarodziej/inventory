import React, { useEffect } from 'react';
import Wheely from 'react-native-wheely';
import { LogBox } from 'react-native';

interface Props {
  selectedIndex: number;
  onChange: (index: number) => void;
}

const range = Array.from({ length: 100 }, (_, i) => `${i}`);

export const WheelPicker = (props: Props) => {
  const { selectedIndex, onChange } = props;

  // Ignore warning about using WheelPicker inside ScrollView
  useEffect(() => {
    LogBox.ignoreLogs(['VirtualizedLists should never be nested']);
  }, [])

  return <Wheely
    flatListProps={{
      nestedScrollEnabled: true,
    }}
    visibleRest={0}
    selectedIndex={selectedIndex}
    options={range}
    onChange={onChange}
    rotationFunction={() => 1.1}
    scaleFunction={x => 1 - 0.5 * x}
    itemTextStyle={{ fontSize: 24 }}
    itemHeight={80}
    itemStyle={{ width: 80 }}
  />
}