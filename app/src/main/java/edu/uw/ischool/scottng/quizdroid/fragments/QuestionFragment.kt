package edu.uw.ischool.scottng.quizdroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
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

                checkAnswer(selectedAnswer)
                currentQuestionIndex++

                if (currentQuestionIndex < questions.size) {
                    question = questions[currentQuestionIndex]

                    questionText?.text = question.questionText
                    radioGroup?.removeAllViews()

                    for (option in question.options) {
                        val radioButton = RadioButton(context)
                        radioButton.text = option
                        radioGroup?.addView(radioButton)
                    }

                    val bundle = Bundle()
                    bundle.putSerializable("question", question)
                } else {
                    showQuizSummary()
                }
            }
        }

        return view
    }

    private fun checkAnswer(selectedAnswer: String) {
        question.isCorrect = selectedAnswer == question.correctAnswer

        if (question.isCorrect) {
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(),"Incorrect! Correct answer is: ${question.correctAnswer}",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showQuizSummary() {
        val totalCorrect = questions.count { it.isCorrect }
        val summaryMessage = "You have $totalCorrect out of ${questions.size} correct."

        Toast.makeText(requireContext(), summaryMessage, Toast.LENGTH_LONG).show()
        if (currentQuestionIndex == questions.size) {
            navigateToTopicList()
        } else {
            navigateToNextQuestion()
        }
    }

    private fun navigateToTopicList() {
        val topicListFragment = TopicListFragment()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, topicListFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToNextQuestion() {
        question = questions[currentQuestionIndex]

        val bundle = Bundle()
        bundle.putSerializable("question", question)

        val questionFragment = QuestionFragment()
        questionFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, questionFragment)
            .addToBackStack(null)
            .commit()
    }
}