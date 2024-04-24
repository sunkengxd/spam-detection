package dev.vision.spam.classifier

import org.tensorflow.lite.task.text.nlclassifier.NLClassifier

fun nlSpamClassifier(classifier: NLClassifier, threshold: Float = 0.8f) = Classifier { target ->
    val (ham, spam) = classifier.classify(target)
    if (spam.score >= threshold) Spam(spam.score) else Ham(ham.score)
}

