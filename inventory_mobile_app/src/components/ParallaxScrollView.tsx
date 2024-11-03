import React, { PropsWithChildren } from 'react';
import Animated, {
  interpolate,
  useAnimatedRef,
  useAnimatedStyle,
  useScrollViewOffset,
} from 'react-native-reanimated'

interface Props {
  parallaxHeaderContent: React.ReactNode
  parallaxHeaderHeight: number
}

export const ParallaxScrollView = (props: PropsWithChildren<Props>) => {
  const { parallaxHeaderContent, parallaxHeaderHeight, children } = props

  const scrollRef = useAnimatedRef<Animated.ScrollView>()
  const scrollOffset = useScrollViewOffset(scrollRef)

  const imageAnimatedStyle = useAnimatedStyle(() => {
    return {
      transform: [
        {
          translateY: interpolate(
            scrollOffset.value,
            [-parallaxHeaderHeight, 0, parallaxHeaderHeight],
            [-parallaxHeaderHeight / 2, 0, parallaxHeaderHeight * 0.75]
          ),
        },
        {
          scale: interpolate(
            scrollOffset.value,
            [-parallaxHeaderHeight, 0, parallaxHeaderHeight],
            [2, 1, 1]
          ),
        },
      ],
    }
  })

  return (
    <Animated.ScrollView
      ref={scrollRef}
      scrollEventThrottle={16}
      showsVerticalScrollIndicator={false}
      keyboardShouldPersistTaps="always" // Allows pressing buttons without unfocusing the input
    >
      <Animated.View style={[imageAnimatedStyle]}>
        {parallaxHeaderContent}
      </Animated.View>
      {children}
    </Animated.ScrollView>
  )
}
