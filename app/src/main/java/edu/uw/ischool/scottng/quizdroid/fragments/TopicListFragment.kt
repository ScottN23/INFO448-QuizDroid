package edu.uw.ischool.scottng.quizdroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import edu.uw.ischool.scottng.quizdroid.QuizApp
import edu.uw.ischool.scottng.quizdroid.R

class TopicListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_topic_list, container, false)

        val quizApp = requireActivity().application as QuizApp
        val topicList = quizApp.topicRepository.getTopics()
        val topics = mutableListOf<String>()
        val topicsWithDescriptions = mutableListOf<String>()

        for (topic in topicList) {
            topics.add(topic.title)
        }

        for (topic in topicList) {
            val topicWithDescription = "${topic.title}\n${quizApp.getTopicDescription(topic.title)}"
            topicsWithDescriptions.add(topicWithDescription)
        }

        val listView: ListView = view.findViewById(R.id.listView)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, topicsWithDescriptions)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTopic = topics[position]
            val topicOverviewFragment = TopicOverviewFragment().apply {
                arguments = Bundle().apply { putString("topic", selectedTopic) }
            }

            if (isAdded) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, topicOverviewFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        return view
    }
}
