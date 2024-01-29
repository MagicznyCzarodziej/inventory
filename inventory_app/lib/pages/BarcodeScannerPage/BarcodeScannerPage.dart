import 'package:flutter/material.dart';
import 'package:inventory_app/pages/BarcodeScannerPage/ScannerOverlay.dart';
import 'package:mobile_scanner/mobile_scanner.dart';

class BarcodeScannerPage extends StatefulWidget {
  const BarcodeScannerPage({super.key});

  @override
  State<BarcodeScannerPage> createState() => _BarcodeScannerPageState();
}

class _BarcodeScannerPageState extends State<BarcodeScannerPage> {
  final MobileScannerController controller = MobileScannerController(
    autoStart: true,
    returnImage: false,
    torchEnabled: true,
    detectionSpeed: DetectionSpeed.normal,
    formats: [
      BarcodeFormat.upcA,
      BarcodeFormat.upcE,
      BarcodeFormat.ean8,
      BarcodeFormat.ean13
    ],
  );

  String? barcode;

  @override
  Widget build(BuildContext context) {
    final scanWindow = Rect.fromCenter(
      center: MediaQuery.of(context).size.center(Offset.zero),
      width: 350,
      height: 500,
    );

    return Scaffold(
      body: Column(
        children: [
          Expanded(
            child: Stack(
              children: [
                MobileScanner(
                  onDetect: (capture) {
                    final List<Barcode> barcodes = capture.barcodes;
                    for (final code in barcodes) {
                      if (code.type != BarcodeType.product) return;
                      setState(() {
                        barcode = code.rawValue;
                      });
                    }
                  },
                  controller: controller,
                  scanWindow: scanWindow,
                ),
                CustomPaint(
                  painter: ScannerOverlay(scanWindow),
                ),
                Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.end,
                    children: [
                      Text(
                        barcode ?? "",
                        style: const TextStyle(
                          fontSize: 24,
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 24, bottom: 32),
                        child: FilledButton(
                          onPressed: () {
                            Navigator.pop(context, barcode);
                          },
                          style: FilledButton.styleFrom(
                            padding: const EdgeInsets.symmetric(
                              vertical: 16,
                              horizontal: 32,
                            ),
                          ),
                          child: const Text(
                            "Zatwierdź",
                            style: TextStyle(fontSize: 24),
                          ),
                        ),
                      ),
                    ],
                  ),
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}
