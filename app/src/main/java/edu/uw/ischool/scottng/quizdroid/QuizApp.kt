package edu.uw.ischool.scottng.quizdroid

import android.app.Application;
import android.util.Log

class QuizApp : Application() {
    lateinit var topicRepository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        Log.d("QuizApp", "QuizApp Loaded")

        topicRepository = InMemoryTopicRepository()
    }

    fun getTopicDescription(topicTitle: String): String? {
        val topics = topicRepository.getTopics()
        val matchingTopic = topics.find { it.title == topicTitle }
        return matchingTopic?.longDescription
    }

    fun getQuestions(topicTitle: String): List<Question>? {
        val topics = topicRepository.getTopics()
        val matchingTopic = topics.find { it.title == topicTitle }
        return matchingTopic?.questions
    }

    fun getTopicIconId(topicTitle: String): Int {
        val topics = topicRepository.getTopics()
        val chosenTopic = topics.find { it.title == topicTitle }
        return chosenTopic?.iconId ?: 0
    }
}

data class Topic (
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val iconId: Int,
    val questions: List<Question>
)

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: Int,
    var isCorrect: Boolean,
)

interface TopicRepository {
    fun getTopics(): List<Topic>
}

class InMemoryTopicRepository : TopicRepository {
    val topicList: MutableList<Topic> = mutableListOf(
        Topic(
            "Math",
            "Mathematics is the abstract science of number, quantity, and space.",
            "Mathematics is the abstract science of number, quantity, and space.",
            android.R.drawable.ic_menu_add,
            listOf(
                Question(
                    "What is 2 + 2?",
                    listOf("3", "4", "5", "6"),
                    1,
                    false
                ),
                Question(
                    "Solve for x: 3x - 7 = 14",
                    listOf("3", "5", "7", "8"),
                    3,
                    false
                )
            )
        ),
        Topic(
            "Physics",
            "Physics is the branch of science concerned with the nature and properties of matter and energy.",
            "Physics is the branch of science concerned with the nature and properties of matter and energy.",
            android.R.drawable.ic_menu_add,
            listOf(
                Question(
                    "What is the formula for Newton's second law?",
                    listOf("F = ma", "E = mc^2", "a = F/m", "F = G * (m1 * m2) / r^2"),
                    0,
                    false
                ),
                Question(
                    "What is the SI unit of force?",
                    listOf("Newton", "Joule", "Watt", "Coulomb"),
                    0,
                    false
                )
            )
        ),
        Topic(
            "Marvel Super Heroes",
            "Marvel Super Heroes are fictional characters with extraordinary abilities inside the Marvel Universe.",
            "Marvel Super Heroes are fictional characters with extraordinary abilities inside the Marvel Universe.",
            android.R.drawable.ic_menu_add,
            listOf(
                Question(
                    "Who is known as the God of Thunder?",
                    listOf("Iron Man", "Thor", "Captain America", "Hulk"),
                    1,
                    false
                ),
                Question(
                    "What is the real name of Black Widow?",
                    listOf("Natasha Romanoff", "Wanda Maximoff", "Carol Danvers", "Pepper Potts"),
                    0,
                    false
                ),
            )
        )
    )

    override fun getTopics(): List<Topic> {
        return topicList
    }
}