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
    title: 'Inventory',
    theme: ThemeData(
      textButtonTheme: TextButtonThemeData(
        style: TextButton.styleFrom(
          // foregroundColor: const Color(0xFFa9bcc7),
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
    home: ItemsPage(camera: firstCamera),
  ));
}
