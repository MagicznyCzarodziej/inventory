import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:inventory_app/api/Auth.dart';
import 'package:inventory_app/pages/ItemsPage/ItemsPage.dart';

Future<void> main() async {

  await CameraProvider().initializeCamera();

  await login();

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
    home: ItemsPage(camera: CameraProvider().camera),
  ));
}

class CameraProvider {
  late final CameraDescription camera;

  CameraProvider._internal();

  static final _singleton = CameraProvider._internal();

  factory CameraProvider() => _singleton;

  initializeCamera() async {
    WidgetsFlutterBinding.ensureInitialized();
    final cameras = await availableCameras();
    camera = cameras.first;
  }
}