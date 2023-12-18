import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../component/bottom_navigation_bar.dart';
import '../login/login.dart';
import '../provider/user_provider.dart';
import '../services/http_location.dart';
import '../services/http_weather.dart';

// the profile page shows the information of the user. like email
// also it aquire the current weather , which is very useful for watering the plant


class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});
  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  late Future<Map<String, dynamic>> futureWeatherData;

  @override
  void initState() {
    super.initState();
    futureWeatherData = _initializeData();
  }

  Future<Map<String, dynamic>> _initializeData() async {
    try {
      String location = await getLocation();
      print(location);
      return fetchWeatherData(location);
    } catch (e) {
      print('Error: $e');
      return {};
    }
  }

  Future<void> _handleSignOut(BuildContext context) async {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    final navigator = Navigator.of(context);
    await FirebaseAuth.instance.signOut();

    userProvider
        .setCurrentUser(null);
    navigator.pushReplacement(MaterialPageRoute(
      builder: (context) => const LoginScreen(),
    ));
  }

  @override
  Widget build(BuildContext context) {
    final userProvider = Provider.of<UserProvider>(context);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Profile'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Icon(Icons.account_circle, size: 200),
            const SizedBox(height: 40),
            Text(
                'You are now signed in with ${userProvider.currentUser?.email}'),

            // Weather Data FutureBuilder code here
            FutureBuilder<Map<String, dynamic>>(
              future: futureWeatherData,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const CircularProgressIndicator();
                } else if (snapshot.hasError) {
                  return Text('Error: ${snapshot.error}');
                } else {
                  // Call getLocation to retrieve the city name
                  return FutureBuilder<String>(
                    future: getLocation(),
                    builder: (context, locationSnapshot) {
                      if (locationSnapshot.connectionState ==
                          ConnectionState.waiting) {
                        return const CircularProgressIndicator();
                      } else if (locationSnapshot.hasError) {
                        return Text(
                            'Error getting location: ${locationSnapshot.error}');
                      } else {
                        // Extract the required data from snapshot.data and locationSnapshot.data
                        var cityName = locationSnapshot.data;
                        var currentTemp = snapshot.data?['current']['temp_c'];
                        var currentCondition =
                            snapshot.data?['current']['condition']['text'];

                        return Column(
                          children: [
                            Text('Your City: ${cityName ?? "N/A"}'),
                            Text(
                                'Current Temperature: ${currentTemp ?? "N/A"}°C'),
                            Text(
                                'Current Condition: ${currentCondition ?? "N/A"}'),
                          ],
                        );
                      }
                    },
                  );
                }
              },
            ),

            const SizedBox(height: 40),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                minimumSize: const Size(
                    200, 60),
              ),
              child: const Text(
                "Sign out",
                style: TextStyle(fontSize: 24),
              ),
              onPressed: () {
                _handleSignOut(context);
              },
            ),
          ],
        ),
      ),
      bottomNavigationBar: BottomNavigationBarWidget(
        currentIndex: 2,
        onTap: (index) {
          if (index == 0) {
            Navigator.of(context).pushReplacementNamed('/home');
          } else if (index == 1) {
            Navigator.of(context).pushReplacementNamed('/myplants');
          }
        },
      ),
    );
  }
}
