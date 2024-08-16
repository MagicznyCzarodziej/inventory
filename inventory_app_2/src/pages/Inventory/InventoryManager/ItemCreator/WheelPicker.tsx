import React from 'react';
import Wheely from 'react-native-wheely';

interface Props {
  selectedIndex: number;
  onChange: (index: number) => void;
}

export const WheelPicker = (props: Props) => {
  const { selectedIndex, onChange } = props;

  return <Wheely
    flatListProps={{
      nestedScrollEnabled: true,
    }}
    visibleRest={0}
    selectedIndex={selectedIndex}
    options={['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10']}
    onChange={onChange}
    rotationFunction={() => 1.1}
    scaleFunction={x => 1 - 0.5 * x}
    itemTextStyle={{ fontSize: 24 }}
    itemHeight={80}
    itemStyle={{ width: 80 }}
  />
}