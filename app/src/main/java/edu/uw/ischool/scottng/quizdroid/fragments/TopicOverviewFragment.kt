package edu.uw.ischool.scottng.quizdroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import edu.uw.ischool.scottng.quizdroid.QuizApp
import edu.uw.ischool.scottng.quizdroid.R

class TopicOverviewFragment : Fragment() {

    private lateinit var quizApp: QuizApp

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_topic_overview, container, false)
        quizApp = requireActivity().application as QuizApp
        val topic = arguments?.getString("topic") ?: ""

        val topicDescription = view.findViewById<TextView>(R.id.topicDescription)
        val beginButton = view.findViewById<Button>(R.id.beginButton)

        val description = "${quizApp.getTopicDescription(topic)} \n Total questions: ${quizApp.getQuestions(topic)?.size ?: 0}"
        topicDescription.text = description

        beginButton.setOnClickListener {
            val selectedTopic = arguments?.getString("topic")

            if (!selectedTopic.isNullOrEmpty()) {
                navigateToFirstQuestion(selectedTopic)
            }
        }

        return view
    }

    private fun navigateToFirstQuestion(topic: String) {
        val questionFragment = QuestionFragment().apply {
            arguments = Bundle().apply {
                putString("topic", topic)
                putSerializable("questions", ArrayList(quizApp.getQuestions(topic)))
            }
        }

        if (isAdded) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, questionFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}