package dev.vision.spam.classifier

import org.tensorflow.lite.task.text.nlclassifier.NLClassifier

fun nlSpamClassifier(classifier: NLClassifier, threshold: Float = 0.8f) = Classifier { target ->
    val score = classifier.classify(target)[1].score
    if (score > threshold) Spam(score) else Ham(score)
}

