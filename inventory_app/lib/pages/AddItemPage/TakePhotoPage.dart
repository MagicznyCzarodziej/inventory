import 'package:camera/camera.dart';
import 'package:flutter/material.dart';

class TakePhotoPage extends StatefulWidget {
  const TakePhotoPage({super.key, required this.camera});

  final CameraDescription camera;

  @override
  State<TakePhotoPage> createState() => _TakePhotoPageState();
}

class _TakePhotoPageState extends State<TakePhotoPage> {
  late CameraController _controller;
  late Future<void> _initializeControllerFuture;

  @override
  void initState() {
    super.initState();
    _controller = CameraController(
      widget.camera,
      ResolutionPreset.medium,
      enableAudio: false,
    );

    _initializeControllerFuture = _controller.initialize();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder<void>(
        future: _initializeControllerFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            return CameraPreview(_controller);
          } else {
            return const Center(child: CircularProgressIndicator());
          }
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          try {
            // Ensure that the camera is initialized.
            await _initializeControllerFuture;

            final image = await _controller.takePicture();

            if (!mounted) return;
            Navigator.pop(context, image.path);
          } catch (e) {
            print(e);
          }
        },
        child: const Icon(Icons.camera_alt),
      ),
    );
  }
}
