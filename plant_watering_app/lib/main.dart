import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'firebase_options.dart';
import 'home/home.dart';
import 'my_plant/my_plant.dart';
import 'profile/profile.dart';
import 'provider/my_plants_provider.dart';
import 'provider/user_provider.dart';
import 'services/auth_check.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(const PlantWateringApp());
}

class PlantWateringApp extends StatelessWidget {
  const PlantWateringApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
            create: (_) => UserProvider()),
        ChangeNotifierProvider(
            create: (_) => MyPlantsProvider()),
      ],
      child: MaterialApp(
        title: 'Plant Watering App',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(
            seedColor: const Color.fromARGB(255, 49, 119, 115),
          ),
          useMaterial3: true,
          scaffoldBackgroundColor: const Color.fromARGB(
              255, 240, 234, 214),
          appBarTheme: const AppBarTheme(
            backgroundColor:
                Color.fromARGB(255, 49, 119, 115),
            centerTitle: true,
            titleTextStyle: TextStyle(
              color: Color.fromARGB(255, 249, 248, 246),
              fontSize: 28,
              fontWeight: FontWeight.bold,
              shadows: [
                Shadow(
                  blurRadius: 5,
                  color: Color.fromARGB(255, 90, 88, 88),
                  offset:
                      Offset(0, 2),
                ),
              ],
            ),
          ),
        ),
        initialRoute: '/auth',
        routes: {
          '/auth': (context) => const AuthCheck(),
          '/home': (context) =>
              const HomeScreen(),
          '/profile': (context) =>
              const ProfileScreen(),
          '/myplants': (context) =>
              const MyPlantsPage(),
        },
      ),
    );
  }
}
