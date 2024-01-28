import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/pages/ItemsPage/ItemsPage.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  // Obtain a list of the available cameras on the device.
  final cameras = await availableCameras();

  // Get a specific camera from the list of available cameras.
  final firstCamera = cameras.first;

  runApp(MaterialApp(
    title: 'Flutter Demo',
    theme: ThemeData(
      colorScheme: const ColorScheme(
        brightness: Brightness.dark,
        background: Color(0xFF11181C),
        onBackground: Colors.white,
        primary: Color(0xFF44C869),
        onPrimary: Colors.white,
        secondary: Color(0xFF44C869),
        onSecondary: Color(0xFF206d36),
        error: Color(0xFFC15959),
        onError: Color(0xFF672727),
        surface: Color(0xFF222d33),
        onSurface: Colors.white,
        surfaceVariant: Color(0xFF384b56),
        onSurfaceVariant: Color(0xFFccd7dd),
        surfaceTint: Color(0xFF222d33),
      ),
      textButtonTheme: TextButtonThemeData(
        style: TextButton.styleFrom(
          foregroundColor: const Color(0xFFa9bcc7),
          textStyle: const TextStyle(
            fontSize: 16,
          ),
          shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(
              Radius.circular(8),
            ),
          ),
        ),
      ),
      iconButtonTheme: IconButtonThemeData(
        style: IconButton.styleFrom(
          foregroundColor: const Color(0xFFa9bcc7),
          shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(
              Radius.circular(8),
            ),
          ),
        ),
      ),
      useMaterial3: true,
    ),
    home: const ItemsPage(title: 'Flutter Demo Home Page'),
  ));
}
