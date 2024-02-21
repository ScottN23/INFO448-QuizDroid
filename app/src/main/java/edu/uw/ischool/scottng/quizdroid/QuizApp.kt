package edu.uw.ischool.scottng.quizdroid

import android.app.Application
import android.content.Context
import android.util.Log
import org.json.JSONArray
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class QuizApp : Application() {
    lateinit var topicRepository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        Log.d("QuizApp", "QuizApp Loaded")

        topicRepository = JsonTopicRepository(this, "questions.json")
    }

    fun getTopicDescription(topicTitle: String): String? {
        val topics = topicRepository.getTopics()
        val matchingTopic = topics.find { it.title == topicTitle }
        return matchingTopic?.description
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

data class Topic(
    val title: String,
    val description: String,
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

class JsonTopicRepository(private val context: Context, private val jsonFilePath: String) : TopicRepository {
    private val topics: List<Topic>

    init {
        val json = readJsonFromAsset()
        topics = parseJson(json)
    }

    override fun getTopics(): List<Topic> {
        return topics
    }

    private fun readJsonFromAsset(): String {
        val assetManager = context.assets
        val stringBuilder = StringBuilder()

        try {
            val inputStream = assetManager.open("extra_credit.json")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            bufferedReader.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }
    private fun parseJson(json: String): List<Topic> {
        val topicsList = mutableListOf<Topic>()

        try {
            val jsonArray = JSONArray(json)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val title = jsonObject.getString("title")
                val description = jsonObject.getString("desc")
                val iconId = android.R.drawable.ic_menu_add

                val questions = jsonObject.getJSONArray("questions")
                val questionsList = mutableListOf<Question>()

                for (j in 0 until questions.length()) {
                    val questionObject = questions.getJSONObject(j)
                    val questionText = questionObject.getString("text")
                    val correctAnswer = questionObject.getInt("answer") - 1
                    val options = questionObject.getJSONArray("answers")
                    val optionsList = mutableListOf<String>()

                    for (k in 0 until options.length()) {
                        optionsList.add(options.getString(k))
                    }

                    val question = Question(questionText, optionsList, correctAnswer, false)
                    questionsList.add(question)
                }

                val topic = Topic(title, description, iconId, questionsList)
                topicsList.add(topic)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return topicsList
    }
}
