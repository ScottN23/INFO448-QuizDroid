package edu.uw.ischool.scottng.quizdroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import edu.uw.ischool.scottng.quizdroid.R

class TopicOverviewFragment : Fragment() {

    private val topicDescriptions = mapOf(
        "Math" to "Mathematics is the abstract science of number, quantity, and space.",
        "Physics" to "Physics is the branch of science concerned with the nature and properties of matter and energy.",
        "Marvel Super Heroes" to "Marvel Super Heroes are fictional characters with extraordinary abilities inside the Marvel Universe."
    )

    private val topicQuestionsMap = mapOf(
        "Math" to getMathQuestions(),
        "Physics" to getPhysicsQuestions(),
        "Marvel Super Heroes" to getMarvelQuestions()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_topic_overview, container, false)
        val topic = arguments?.getString("topic")

        val topicDescription = view.findViewById<TextView>(R.id.topicDescription)
        val beginButton = view.findViewById<Button>(R.id.beginButton)

        beginButton.setOnClickListener {
            val selectedTopic = arguments?.getString("topic")

            if (!selectedTopic.isNullOrEmpty()) {
                navigateToFirstQuestion(selectedTopic, topicQuestionsMap[selectedTopic])
            }
        }
        val description = "${topicDescriptions[topic]} \n Total questions: ${topicQuestionsMap[topic]?.size ?: 0}"
        topicDescription.text = description

        return view
    }

    private fun navigateToFirstQuestion(topic: String, questions: List<Question>?) {
        val questionFragment = QuestionFragment().apply {
            arguments = Bundle().apply {
                putString("topic", topic)
                putSerializable("questions", ArrayList(questions))
            }
        }

        if (isAdded) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, questionFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun getMathQuestions(): List<Question> {
        return listOf(
            Question("What is 2 + 2?", listOf("3", "4", "5", "6"), "4"),
            Question("Solve for x: 3x - 7 = 14", listOf("3", "5", "7", "8"), "8"),
        )
    }

    private fun getPhysicsQuestions(): List<Question> {
        return listOf(
            Question("What is the formula for Newton's second law?", listOf("F = ma", "E = mc^2", "a = F/m", "F = G * (m1 * m2) / r^2"), "F = ma"),
            Question("What is the SI unit of force?", listOf("Newton", "Joule", "Watt", "Coulomb"), "Newton"),
        )
    }

    private fun getMarvelQuestions(): List<Question> {
        return listOf(
            Question("Who is known as the God of Thunder?", listOf("Iron Man", "Thor", "Captain America", "Hulk"), "Thor"),
            Question("What is the real name of Black Widow?", listOf("Natasha Romanoff", "Wanda Maximoff", "Carol Danvers", "Pepper Potts"), "Natasha Romanoff"),
        )
    }
}