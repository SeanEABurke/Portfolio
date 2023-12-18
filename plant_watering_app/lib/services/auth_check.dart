import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../home/home.dart';
import '../login/login.dart';
import '../provider/user_provider.dart';

class AuthCheck extends StatelessWidget {
  const AuthCheck({super.key});

  @override
  Widget build(BuildContext context) {
    final userProvider = Provider.of<UserProvider>(context);
    final firebaseAuth = FirebaseAuth.instance;

    final User? user = userProvider.currentUser;

    return StreamBuilder<User?>(
      stream: firebaseAuth.authStateChanges(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.active) {
          final User? authUser = snapshot.data;

          if (authUser == null) {
            // User is not signed in, navigate to login page
            return const LoginScreen();
          } else {
            // User is signed in, update the UserProvider
            userProvider.setCurrentUser(authUser);
            return const HomeScreen();
          }
        } else {
          // Waiting for the authentication state to be determined
          return const CircularProgressIndicator();
        }
      },
    );
  }
}
