import 'package:flutter/material.dart';

PageRoute simpleRoute(Widget route) {
  return PageRouteBuilder(
    pageBuilder: (_, __, ___) => route,
    transitionDuration: Duration.zero,
    reverseTransitionDuration: Duration.zero,
  );
}
