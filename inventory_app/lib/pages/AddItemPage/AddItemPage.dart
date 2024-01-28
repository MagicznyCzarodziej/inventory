import 'package:flutter/material.dart';

class AddItemPage extends StatefulWidget {
  const AddItemPage({super.key});

  @override
  State<AddItemPage> createState() => _AddItemPageState();
}
class _AddItemPageState extends State<AddItemPage> {
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
          title: const Text("XD"),
        ),
        body: Form(
          key: _formKey,
          child: Column(
            children: [
              const Text("Xd"),
              TextFormField(
                validator: (value) {
                  return "Alo";
                },
              ),
              TextFormField(
                validator: (value) {
                  return "Alo";
                },
              ),
              ElevatedButton(
                  onPressed: () {
                    _formKey.currentState!.validate();
                  },
                  child: const Text("Yup")
              )
            ],
          ),
        )
    );
  }
}
