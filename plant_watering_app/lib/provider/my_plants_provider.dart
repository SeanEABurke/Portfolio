import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';

import '../models/plant.dart';

class MyPlantsProvider extends ChangeNotifier {
  final FirebaseFirestore _firestore = FirebaseFirestore.instance;

  Future<List<Plant>> fetchPlants(String userId) async {
    final querySnapshot = await _firestore
        .collection('plants')
        .where('userId', isEqualTo: userId)
        .get();

    final plants = querySnapshot.docs.map((doc) {
      final data = doc.data();
      return Plant.fromMap(doc.id, data);
    }).toList();

    return plants;
  }


  Future<void> addPlant(Plant plant) async {
    await _firestore.collection('plants').add(plant.toMap());
    notifyListeners();
  }

  Future<void> updatePlant(Plant plant) async {
    await _firestore.collection('plants').doc(plant.id).update(plant.toMap());
    notifyListeners();
  }

  Future<void> deletePlant(String plantId) async {
    await _firestore.collection('plants').doc(plantId).delete();
    notifyListeners();
  }


  Future<void> markPlantWatered(String plantId) async {
    final plant = await _firestore.collection('plants').doc(plantId).get();
    final lastWateredAt = DateTime.now();
    await _firestore.collection('plants').doc(plantId).update({
      'lastWateredAt': Timestamp.fromDate(lastWateredAt),
    });
    notifyListeners();
  }

  Future<List<Plant>> getUserPlants(String userId) async {
    final querySnapshot = await _firestore
        .collection('plants')
        .where('userId', isEqualTo: userId)
        .get();

    final plants = querySnapshot.docs.map((doc) {
      final data = doc.data();
      return Plant.fromMap(doc.id, data);
    }).toList();

    return plants;
  }
}
