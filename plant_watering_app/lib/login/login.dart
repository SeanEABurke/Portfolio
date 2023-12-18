import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:provider/provider.dart';

import '../home/home.dart';
import '../provider/user_provider.dart';
import '../signup/signup.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginScreen> {
  final FirebaseAuth _auth = FirebaseAuth.instance;
  final GoogleSignIn _googleSignIn = GoogleSignIn();

  String _email = "";
  String _password = "";
  bool _rememberMe = true;

  Future<User?> _handleGoogleSignIn() async {
    try {
      final GoogleSignInAccount? googleUser = await _googleSignIn.signIn();
      if (googleUser == null) {
        return null;
      }

      final GoogleSignInAuthentication googleAuth =
          await googleUser.authentication;
      final AuthCredential credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );

      final UserCredential authResult =
          await _auth.signInWithCredential(credential);
      return authResult.user;
    } catch (error) {
      print("Google Sign-In Error: $error");
      return null;
    }
  }

  Future<void> _handleEmailSignIn() async {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    final navigator = Navigator.of(context);
    try {
      UserCredential authResult = await _auth.signInWithEmailAndPassword(
        email: _email,
        password: _password,
      );
      User? user = authResult.user;
      if (user != null) {
        userProvider.setCurrentUser(user);
        // Navigate to the main app page after successful sign-in
        navigator.pushReplacement(MaterialPageRoute(
          builder: (context) => const HomeScreen(),
        ));
      }
    } catch (error) {
      print("Email Sign-In Error: $error");
      Future.microtask(() {
        showDialog(
          context: context,
          builder: (context) {
            return const AlertDialog(
              content: Text("Invalid Email or Password"),
            );
          },
        );
      });
    }
  }

  Future<void> _handleAnonymousSignIn() async {
    final userProvider = Provider.of<UserProvider>(context, listen: false);
    final navigator = Navigator.of(context);

    try {
      UserCredential authResult = await _auth.signInAnonymously();
      User? user = authResult.user;

      if (user != null) {
        userProvider.setCurrentUser(user);
        // User is signed in anonymously
        navigator.pushReplacement(MaterialPageRoute(
          builder: (context) => const HomeScreen(),
        ));
      }
    } catch (error) {
      print('Anonymous Sign-In Error: $error');
    }
  }

  @override
  Widget build(BuildContext context) {
    final userProvider = Provider.of<UserProvider>(context);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Login Page'),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(Icons.energy_savings_leaf,
                  color: Theme.of(context).primaryColor, size: 200),
              Text(
                "Hose Job",
                style: TextStyle(
                  fontSize: 30, // Adjust to the desired headline size
                  color: Theme.of(context)
                      .primaryColor, // Use the primary color from the theme
                ),
              ),
              const SizedBox(height: 20),
              Padding(
                padding: const EdgeInsets.only(left: 20.0, right: 20.0),
                child: TextField(
                  onChanged: (value) {
                    setState(() {
                      _email = value;
                    });
                  },
                  decoration: const InputDecoration(
                    labelText: 'Email',
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(20.0),
                child: TextField(
                  onChanged: (value) {
                    setState(() {
                      _password = value;
                    });
                  },
                  obscureText: true,
                  decoration: const InputDecoration(
                    labelText: 'Password',
                  ),
                ),
              ),
              ElevatedButton(
                onPressed: () async {
                  // Handle Email Sign-In
                  _handleEmailSignIn();
                },
                child: const Text('Sign in with Email'),
              ),
              ElevatedButton(
                onPressed: () async {
                  final navigator = Navigator.of(context);
                  // Handle Google Sign-In
                  final user = await _handleGoogleSignIn();

                  if (user != null) {
                    userProvider.setCurrentUser(user);
                    // Navigate to the main app page after successful sign-in
                    navigator.pushReplacement(
                      MaterialPageRoute(
                        builder: (context) => const HomeScreen(),
                      ),
                    );
                  }
                },
                child: const Text('Sign in with Google'),
              ),
              ElevatedButton(
                onPressed: () async {
                  // Handle Anonymous Sign-In
                  _handleAnonymousSignIn();
                },
                child: const Text('Sign in anonymously'),
              ),
              const SizedBox(height: 20),
              TextButton(
                onPressed: () {
                  // Navigate to the sign-up page
                  Navigator.of(context).push(MaterialPageRoute(
                    builder: (context) => const SignUpScreen(),
                  ));
                },
                child: const Text('Don\'t have an account? Sign Up'),
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Checkbox(
                    value: _rememberMe,
                    onChanged: (value) {
                      setState(() {
                        _rememberMe = value!;
                      });
                    },
                  ),
                  const Text('Remember Me'),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
