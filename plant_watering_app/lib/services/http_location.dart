import 'dart:async';
import 'dart:convert';

import 'package:http/http.dart' as http;

Future<String> getLocation() async {
  final client = http.Client();
  const timeout = Duration(seconds: 6);

  try {
    final url = Uri.parse('https://ipinfo.io/json');

    final response = await client.get(url).timeout(timeout);

    if (response.statusCode == 200) {
      Map<String, dynamic> data = json.decode(response.body);

      String city = data['city'];
      if (city.isNotEmpty) {
        return city;
      } else {
        throw Exception('City data not found in the response');
      }
    } else {
      throw Exception('Failed to load location data');
    }
  } on TimeoutException {
    throw Exception('Location request timed out');
  } on Exception catch (e) {
    throw Exception('Location request failed: $e');
  } finally {
    client.close();
  }
}
