package mnshat.dev.myproject.users.caregiver.tools.cofe.presintaion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mnshat.dev.myproject.R


class SuggestedPhrasesSympathyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_suggested_phrases_sympathy, container, false)
    }


    /**
     * fun getPhrases(context: Context): List<String> {
     *     return listOf(
     *         context.getString(R.string.phrase1),
     *         context.getString(R.string.phrase2),
     *         context.getString(R.string.phrase3),
     *         context.getString(R.string.phrase4),
     *         context.getString(R.string.phrase5),
     *         context.getString(R.string.phrase6),
     *         context.getString(R.string.phrase7),
     *         context.getString(R.string.phrase8),
     *         context.getString(R.string.phrase9),
     *         context.getString(R.string.phrase10),
     *         context.getString(R.string.phrase11)
     *     )
     * }
     *
     */
}