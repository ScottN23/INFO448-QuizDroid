package edu.uw.ischool.scottng.quizdroid.fragments

import java.io.Serializable

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    var selectedAnswer: String? = null,
    var isCorrect: Boolean = false
) : Serializable