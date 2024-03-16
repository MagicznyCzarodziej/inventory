import 'package:dio/dio.dart';
import 'package:cookie_jar/cookie_jar.dart';
import 'package:dio_cookie_manager/dio_cookie_manager.dart';
import 'package:inventory_app/api/Auth.dart';

// const API_URL = 'http://192.168.0.66:8080';
const String API_URL = "http://inventory-api.przemyslawpitus.pl";

class HttpClient {
  final client = _createDio();
  static final cookieJar = CookieJar();

  HttpClient._internal();

  static final _singleton = HttpClient._internal();

  factory HttpClient() => _singleton;

  static Dio _createDio() {
    var dio = Dio(
      BaseOptions(
        baseUrl: API_URL,
      ),
    );

    dio.interceptors.add(CookieManager(cookieJar));
    dio.interceptors.add(InterceptorsWrapper(onError: (DioException exception, handler) async {
      if (exception.response?.statusCode == 403 && !exception.requestOptions.path.startsWith("/auth")) {
        await refreshToken();
        return handler.resolve(await HttpClient().client.fetch(exception.requestOptions));
      }
      return handler.next(exception);
    }));

    return dio;
  }
}
