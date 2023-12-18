import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

import '../component/bottom_navigation_bar.dart';
import '../models/plant.dart';
import '../provider/my_plants_provider.dart';
import '../provider/user_provider.dart';

/// The MyPlantsPage have 3 parts. 1) an plant adding from. 2) a list of the plats 3) the buttom navi bar
/// user can add plant with thr form .

class MyPlantsPage extends StatefulWidget {
  const MyPlantsPage({super.key});

  @override
  State<MyPlantsPage> createState() => _MyPlantsPageState();
}

class _MyPlantsPageState extends State<MyPlantsPage> {
  final TextEditingController _plantNameController = TextEditingController();
  final TextEditingController _wateringFrequencyController =
      TextEditingController();
  late DateTime _lastWateredDate;

  @override
  void initState() {
    super.initState();
    _lastWateredDate =
        DateTime.now();
  }

  // use a canlander for choose a date. user friendly.
  // also defalut date is today. and user cannot choose the date in the future.
  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: _lastWateredDate,
      firstDate: DateTime(2000),
      lastDate: DateTime.now(),
    );
    if (picked != null && picked != _lastWateredDate) {
      setState(() {
        _lastWateredDate =
            picked;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    final userProvider = Provider.of<UserProvider>(context);
    final plantProvider = Provider.of<MyPlantsProvider>(context);

    final User? currentUser = userProvider.currentUser;

    return Scaffold(
      appBar: AppBar(
        title: const Text('My Plants'),
      ),
      body: FutureBuilder<List<Plant>>(
        future: plantProvider.getUserPlants(currentUser!.uid),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          }

          final List<Plant> userPlants = snapshot.data ?? [];

          return Column(
            children: [
              _buildPlantForm(context),
              const SizedBox(height: 16),
              _buildPlantList(userPlants),
            ],
          );
        },
      ),
      bottomNavigationBar: BottomNavigationBarWidget(
        currentIndex: 1,
        onTap: (index) {
          if (index == 0) {
            Navigator.of(context).pushReplacementNamed('/home');
          } else if (index == 2) {
            Navigator.of(context).pushReplacementNamed('/profile');
          }
        },
      ),
    );
  }


  // teh adding plant form
  Widget _buildPlantForm(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(16),
      child: Form(
        child: Column(
          children: [
            TextFormField(
              controller: _plantNameController,
              decoration: const InputDecoration(labelText: 'Plant Name'),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Please enter a plant name';
                }
                return null;
              },
            ),
            TextFormField(
              controller: _wateringFrequencyController,
              decoration:
                  const InputDecoration(labelText: 'Watering Frequency (days)'),
              keyboardType: TextInputType.number,
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Please enter a watering frequency';
                }
                return null;
              },
            ),
            TextFormField(
              readOnly: true,
              onTap: () {
                _selectDate(context);
              },
              decoration: const InputDecoration(
                labelText: 'Last Watered Date',
                suffixIcon: Icon(Icons.calendar_today),
              ),
              initialValue: DateFormat('yyyy-MM-dd').format(_lastWateredDate),
            ),
            Padding(
              padding: const EdgeInsets.only(top: 15.0),
              child: ElevatedButton(
                onPressed: () {
                  _addPlant(
                    context,
                    _plantNameController,
                    _wateringFrequencyController,
                    _lastWateredDate,
                  );
                },
                style: ElevatedButton.styleFrom(
                  minimumSize: const Size(
                      200, 60),
                ),
                child: const Text(
                  "Add Plant",
                  style: TextStyle(fontSize: 24),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }


  // the plant list. using the CARD style.
  Widget _buildPlantList(List<Plant> userPlants) {
    return Expanded(
      child: ListView.builder(
        itemCount: userPlants.length,
        itemBuilder: (context, index) {
          final plant = userPlants[index];

          return Card(
            elevation: 3,
            margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
            child: ListTile(
              title: Text(
                plant.name,
                style: const TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                  color: Color.fromARGB(255, 0, 128, 128),
                ),
              ),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text('Water every ${plant.wateringFrequency} days'),
                  Text(
                      'Last Watered: ${DateFormat('dd-MM-yyyy').format(plant.lastWateredAt)}'),
                ],
              ),
              trailing: IconButton(
                icon: const Icon(Icons.delete),
                onPressed: () {
                  _deletePlant(context, plant);
                },
              ),
            ),
          );
        },
      ),
    );
  }

  void _deletePlant(BuildContext context, Plant plant) {
    final myPlantsProvider =
        Provider.of<MyPlantsProvider>(context, listen: false);
    myPlantsProvider.deletePlant(plant.id ?? "");
  }

  void _addPlant(
    BuildContext context,
    TextEditingController plantNameController,
    TextEditingController wateringFrequencyController,
    DateTime lastWateredDate,
  ) {
    final String plantName = plantNameController.text.trim();
    final String wateringFrequencyText =
        wateringFrequencyController.text.trim();

    if (plantName.isNotEmpty && wateringFrequencyText.isNotEmpty) {
      final int? wateringFrequency = int.tryParse(wateringFrequencyText);

      if (wateringFrequency != null && wateringFrequency > 0) {
        final Plant newPlant = Plant(
          userId: Provider.of<UserProvider>(context, listen: false)
              .currentUser!
              .uid,
          name: plantName,
          wateringFrequency: wateringFrequency,
          createdAt: DateTime.now(),
          lastWateredAt: lastWateredDate,
        );

        Provider.of<MyPlantsProvider>(context, listen: false)
            .addPlant(newPlant);

        plantNameController.clear();
        wateringFrequencyController.clear();
      }
    }
  }
}
