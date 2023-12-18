import 'dart:convert';
import 'package:http/http.dart';

class HttpService {
  final String postsURL = "https://zenquotes.io/api/today";

  Future<Map<String, dynamic>> getQuote() async {
    Response res = await get(Uri.parse(postsURL));

    if (res.statusCode == 200) {
      List<dynamic> dataList = jsonDecode(res.body);

      if (dataList.isNotEmpty) {
        Map<String, dynamic> firstItem = dataList[0];
        return {
          'q': firstItem['q'],
          'a': firstItem['a'],
        };
      } else {
        throw "No data found in the JSON response.";
      }
    } else {
      throw "Unable to retrieve the quote.";
    }
  }
}
