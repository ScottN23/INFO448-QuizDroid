package edu.uw.ischool.scottng.quizdroid.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import edu.uw.ischool.scottng.quizdroid.Question
import edu.uw.ischool.scottng.quizdroid.R

class QuestionFragment : Fragment() {

    private lateinit var question: Question
    private var currentQuestionIndex: Int = 0
    private lateinit var questions: List<Question>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        questions = arguments?.getSerializable("questions") as? List<Question> ?: emptyList()
        currentQuestionIndex = arguments?.getInt("currentQuestionIndex") ?: 0

        val questionText = view.findViewById<TextView>(R.id.questionText)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        val submitButton = view.findViewById<Button>(R.id.submitButton)

        question = questions[currentQuestionIndex]
        questionText?.text = question.questionText

        radioGroup?.removeAllViews()
        for (option in question.options) {
            val radioButton = RadioButton(context)
            radioButton.text = option
            radioGroup?.addView(radioButton)
        }

        submitButton.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId

            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = view.findViewById<RadioButton>(selectedRadioButtonId)
                val selectedAnswer = selectedRadioButton.text.toString()

                val answerCheck = checkAnswer(selectedAnswer)

                if (currentQuestionIndex < questions.size) {
                    question = questions[currentQuestionIndex]

                    questionText?.text = question.questionText
                    radioGroup?.removeAllViews()

                    for (option in question.options) {
                        val radioButton = RadioButton(context)
                        radioButton.text = option
                        radioGroup?.addView(radioButton)
                    }

                    val bundle = Bundle().apply {
                        putSerializable("questions", ArrayList(questions))
                        putInt("currentQuestionIndex", currentQuestionIndex)
                        putString("answer", selectedAnswer)
                        putBoolean("answerCheck", question.isCorrect)
                    }
                    navigateToAnswerFragment(bundle)
                } else {
                    showQuizSummary()
                }
            }
        }

        return view
    }

    private fun isLastQuestion(): Boolean {
        return currentQuestionIndex == questions.size - 1
    }

    private fun checkAnswer(selectedAnswer: String) {
        question.isCorrect = question.options[question.correctAnswer] == selectedAnswer
    }

    private fun showQuizSummary() {
        val totalCorrect = questions.count { it.isCorrect }
        val summaryMessage = "You have $totalCorrect out of ${questions.size} correct."

        val bundle = Bundle().apply {
            putSerializable("questions", ArrayList(questions))
            putInt("currentQuestionIndex", currentQuestionIndex)
            putString("summaryMessage", summaryMessage)
        }

        if (isLastQuestion()) {
            navigateToTopicList()
        } else {
            navigateToAnswerFragment(bundle)
        }
    }

    private fun navigateToAnswerFragment(bundle: Bundle) {
        val answerFragment = AnswerFragment().apply {
            arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, answerFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToTopicList() {
        val topicListFragment = TopicListFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, topicListFragment)
            .addToBackStack(null)
            .commit()
    }
}