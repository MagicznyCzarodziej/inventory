import 'package:flutter/material.dart';

PageRoute<T> simpleRoute<T>(Widget route) {
  return PageRouteBuilder<T>(
    pageBuilder: (_, __, ___) => route,
    transitionDuration: Duration.zero,
    reverseTransitionDuration: Duration.zero,
  );
}
