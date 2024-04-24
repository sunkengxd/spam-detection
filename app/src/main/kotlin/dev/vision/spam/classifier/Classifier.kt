package dev.vision.spam.classifier

fun interface Classifier {
    suspend fun check(target: String): Classification
}