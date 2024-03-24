package dev.vision.spam.classifier

val randomSpamClassifier = SpamClassifier {
    SpamClassification.entries.random()
}