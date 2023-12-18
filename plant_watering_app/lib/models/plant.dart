import 'package:cloud_firestore/cloud_firestore.dart';

// a plant model

class Plant {
  final String? id; // id is option is because the id is assigned by firebase
  final String userId;
  final String name;
  final int wateringFrequency;
  final DateTime createdAt;
  final DateTime lastWateredAt;

  Plant({
    required this.userId,
    required this.name,
    required this.wateringFrequency,
    required this.createdAt,
    required this.lastWateredAt,
    this.id,
  });

  factory Plant.fromMap(String id, Map<String, dynamic> data) {
    return Plant(
      id: id,
      userId: data['userId'],
      name: data['name'],
      wateringFrequency: data['wateringFrequency'],
      createdAt: (data['createdAt'] as Timestamp).toDate(),
      lastWateredAt: (data['lastWateredAt'] as Timestamp).toDate(),
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'userId': userId,
      'name': name,
      'wateringFrequency': wateringFrequency,
      'createdAt': createdAt,
      'lastWateredAt': lastWateredAt,
    };
  }
}
