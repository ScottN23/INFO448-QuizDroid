package edu.uw.ischool.scottng.quizdroid.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import edu.uw.ischool.scottng.quizdroid.R

class AnswerFragment : Fragment() {

    private lateinit var userAnswer: String
    private var currentQuestionIndex: Int = 0
    private lateinit var questions: List<Question>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_answer, container, false)

        questions = arguments?.getSerializable("questions") as? List<Question> ?: emptyList()
        currentQuestionIndex = arguments?.getInt("currentQuestionIndex") ?: 0
        userAnswer = arguments?.getString("answer") ?: ""

        val givenAnswerText = view.findViewById<TextView>(R.id.givenAnswerText)
        val correctAnswerText = view.findViewById<TextView>(R.id.correctAnswerText)
        val summaryText = view.findViewById<TextView>(R.id.summaryText)
        val nextButton = view.findViewById<Button>(R.id.nextButton)
        val finishButton = view.findViewById<Button>(R.id.finishButton)

        if (currentQuestionIndex < questions.size - 1) {
            nextButton.visibility = View.VISIBLE
            finishButton.visibility = View.GONE
        } else {
            nextButton.visibility = View.GONE
            finishButton.visibility = View.VISIBLE
        }

        val currentQuestion = questions[currentQuestionIndex]

        givenAnswerText.text = "You selected: ${userAnswer}"
        correctAnswerText.text = "Correct answer: ${currentQuestion.correctAnswer}"

        val correctCount = questions.count { it.isCorrect }

        summaryText.text = "You have $correctCount out of ${questions.size} correct"

        nextButton.setOnClickListener {
            navigateToNextQuestion()
        }

        finishButton.setOnClickListener {
            navigateToQuizList()
        }

        return view
    }

    private fun navigateToNextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            val bundle = Bundle()
            bundle.putSerializable("questions", ArrayList(questions))
            bundle.putInt("currentQuestionIndex", currentQuestionIndex + 1)

            val questionFragment = QuestionFragment().apply {
                arguments = bundle
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, questionFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun navigateToQuizList() {
        val topicListFragment = TopicListFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, topicListFragment)
            .addToBackStack(null)
            .commit()
    }
}
