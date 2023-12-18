import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

import '../component/bottom_navigation_bar.dart';
import '../models/plant.dart';
import '../provider/my_plants_provider.dart';
import '../provider/user_provider.dart';
import '../services/http_service.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({
    super.key,
  });

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final HttpService httpService = HttpService();
  String quote = '';
  String author = '';

  @override
  void initState() {
    super.initState();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    fetchQuote();
  }

  void fetchQuote() async {
    try {
      final quoteData = await httpService.getQuote();
      if (quoteData.containsKey('q') && quoteData.containsKey('a')) {
        setState(() {
          quote = quoteData['q'];
          author = quoteData['a'];
        });
      } else {
        print("Quote and/or author not found in the JSON response");
      }
    } catch (e) {
      print("Error fetching quote: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    final userProvider = Provider.of<UserProvider>(context);
    final plantProvider = Provider.of<MyPlantsProvider>(context);
    final User? currentUser = userProvider.currentUser;
    final List<Plant> plantsToWater = [];

    return Scaffold(
      appBar: AppBar(
        title: const Text('Plant Watering Schedule'),
      ),
      body: FutureBuilder<List<Plant>>(
        future: plantProvider.getUserPlants(currentUser!.uid),
        builder: (context, snapshot) {
          //Get the lis tof plants from the db
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          }

          final List<Plant> userPlants = snapshot.data ?? [];

          // Sort the plants by the next watering date
          userPlants.sort((a, b) {
            DateTime nextWateringA =
                a.lastWateredAt.add(Duration(days: a.wateringFrequency));
            DateTime nextWateringB =
                b.lastWateredAt.add(Duration(days: b.wateringFrequency));
            return nextWateringA.compareTo(nextWateringB);
          });

          // Create a list of unique watering dates
          List<DateTime> uniqueWateringDates = [];
          for (var plant in userPlants) {
            DateTime nextWatering = plant.lastWateredAt
                .add(Duration(days: plant.wateringFrequency));
            if (!uniqueWateringDates.contains(nextWatering)) {
              uniqueWateringDates.add(nextWatering);
            }
          }

          //Keep track of which plants have been checked off
          final Map<String, bool> plantCheckedState = {};
          for (final plant in userPlants) {
            plantCheckedState[plant.id ?? ''] = false;
          }

          return Column(
            children: [
              Padding(
                padding: const EdgeInsets.only(top: 12.0),
                child: Text(
                  quote,
                  style: Theme.of(context).textTheme.titleLarge,
                  textAlign: TextAlign.center,
                ),
              ),
              Text(
                author,
                style: Theme.of(context)
                    .textTheme
                    .titleMedium
                    ?.copyWith(fontStyle: FontStyle.italic),
              ),
              // _buildPlantForm(context),
              const SizedBox(height: 16),
              if (userPlants.isEmpty)
                Center(
                  child: Text(
                    "Add your plants on the 'My Plants' page!",
                    style: Theme.of(context).textTheme.displayLarge,
                    textAlign: TextAlign.center,
                  ),
                ),
              PlantList(
                userPlants: userPlants,
                uniqueWateringDates: uniqueWateringDates,
                plantCheckedState: plantCheckedState,
                plantsToWater: plantsToWater,
              ),

              Padding(
                padding: const EdgeInsets.all(8.0),
                child: ConfirmButton(plantsToWater: plantsToWater),
              ),
            ],
          );
        },
      ),
      bottomNavigationBar: BottomNavigationBarWidget(
        currentIndex: 0, // Set the appropriate index for the Home page
        onTap: (index) {
          if (index == 1) {
            // Navigate to the My Plants screen
            Navigator.of(context).pushReplacementNamed('/myplants');
          } else if (index == 2) {
            // Navigate to the Profile screen
            Navigator.of(context).pushReplacementNamed('/profile');
          }
        },
      ),
    );
  }
}

class ConfirmButton extends StatelessWidget {
  const ConfirmButton({super.key, required this.plantsToWater});
  final List<Plant> plantsToWater;

  @override
  Widget build(BuildContext context) {
    final navigator = Navigator.of(context);
    return ElevatedButton(
      onPressed: () async {
        // When the submit button is pressed, mark all plants in the list as watered
        for (final plant in plantsToWater) {
          await MyPlantsProvider().markPlantWatered(plant.id ?? "");
        }
        // Clear the list after marking the plants
        plantsToWater.clear();
        // Reset the checked state for all plants
        navigator.popAndPushNamed('/home');
      },
      style: ElevatedButton.styleFrom(
        minimumSize: const Size(200, 60),
      ),
      child: const Text(
        "Submit",
        style: TextStyle(fontSize: 24),
      ),
    );
  }
}

//Shows all of the plants in order of which need to be watered the soonest
class PlantList extends StatefulWidget {
  const PlantList(
      {super.key,
      required this.userPlants,
      required this.uniqueWateringDates,
      required this.plantCheckedState,
      required this.plantsToWater});

  final List<Plant> userPlants;
  final List<DateTime> uniqueWateringDates;
  final Map<String, bool> plantCheckedState;
  final List<Plant> plantsToWater;

  @override
  State<PlantList> createState() => _PlantListState();
}

class _PlantListState extends State<PlantList> {
  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: ListView.builder(
        itemCount: widget.uniqueWateringDates.length,
        itemBuilder: (context, index) {
          final plant = widget.userPlants[index];

          return Card(
            elevation: 3,
            margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
            child: ListTile(
              title: Text(
                plant.name,
                style: const TextStyle(
                  fontSize: 20, // Set the font size
                  fontWeight: FontWeight.bold, // Set the font weight
                  color: Color.fromARGB(255, 0, 128, 128), // Set the text color
                ),
              ),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text.rich(
                    TextSpan(
                      children: <TextSpan>[
                        const TextSpan(
                          text: 'Water on ',
                        ),
                        TextSpan(
                          text: DateFormat('dd-MM-yyyy')
                              .format(widget.uniqueWateringDates[index]),
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ],
                    ),
                  ),
                  Text(
                      'Last Watered: ${DateFormat('dd-MM-yyyy').format(plant.lastWateredAt)}'),
                ],
              ),
              trailing: Checkbox(
                value: widget.plantCheckedState[plant.id ?? ''] ?? false,
                onChanged: (value) {
                  setState(() {
                    widget.plantCheckedState[plant.id ?? ''] = value ?? false;
                    if (value == true) {
                      // If the plant is checked, add it to the list to be watered
                      widget.plantsToWater.add(plant);
                    } else {
                      // If the plant is unchecked, remove it from the list to be watered
                      widget.plantsToWater.remove(plant);
                    }
                  });
                },
              ),
            ),
          );
        },
      ),
    );
  }
}
