import 'package:inventory_app/api/HttpClient.dart';

Future login() async {
   await HttpClient().client.post('/auth/login', data: {'username': 'deko', 'password': 'deko'});
}

Future refreshToken() async {
   await HttpClient().client.post('/auth/refresh-token');
}
