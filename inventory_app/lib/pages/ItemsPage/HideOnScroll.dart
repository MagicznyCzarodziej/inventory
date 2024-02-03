import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class HideOnScroll extends StatefulWidget {
  const HideOnScroll({
    super.key,
    required this.child,
    required this.scrollController,
    required this.height,
  });

  final Widget child;
  final double height;
  final ScrollController scrollController;
  final Duration duration = const Duration(milliseconds: 100);

  @override
  State<HideOnScroll> createState() => _HideOnScrollState();
}

class _HideOnScrollState extends State<HideOnScroll> {
  bool isVisible = true;

  @override
  void initState() {
    widget.scrollController.addListener(scrollListener);
    super.initState();
  }

  @override
  void dispose() {
    widget.scrollController.removeListener(scrollListener);
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedContainer(
      height: isVisible ? widget.height : 0,
      duration: widget.duration,
      child: Wrap(
        children: [
          widget.child,
        ],
      ),
    );
  }

  void show() {
    if (!isVisible && mounted) {
      setState(() {
        isVisible = true;
      });
    }
  }

  void hide() {
    if (isVisible && mounted) {
      setState(() {
        isVisible = false;
      });
    }
  }

  void scrollListener() {
    final direction = widget.scrollController.position.userScrollDirection;
    if (direction == ScrollDirection.forward) {
      show();
    } else if (direction == ScrollDirection.reverse) {
      hide();
    }
  }
}
