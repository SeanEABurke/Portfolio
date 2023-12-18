import 'dart:convert';
import 'package:http/http.dart' as http;

Future<Map<String, dynamic>> fetchWeatherData(city) async {
  const String baseUrl = 'https://weatherapi-com.p.rapidapi.com/current.json';
  const String path = '';
  final Uri url = Uri.parse(baseUrl + path);

  final Map<String, String> headers = {
    'X-Rapidapi-Key': '583815c838mshabe56640802fee0p121003jsnace8528c9895',
    'X-Rapidapi-Host': 'weatherapi-com.p.rapidapi.com',
  };

  final Map<String, String> queryParameters = {
    'q': city, // Add the 'q' parameter for the city
  };

  final http.Response response = await http
      .get(url.replace(queryParameters: queryParameters), headers: headers);

  if (response.statusCode == 200) {
    return jsonDecode(response.body);
  } else {
    throw Exception('Failed to load weather data');
  }
}
