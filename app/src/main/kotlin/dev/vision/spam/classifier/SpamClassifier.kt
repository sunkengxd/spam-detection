package dev.vision.spam.classifier

fun interface SpamClassifier {

    fun check(target: String): SpamClassification
}