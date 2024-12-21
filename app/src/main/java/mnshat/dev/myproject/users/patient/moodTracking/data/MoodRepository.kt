package mnshat.dev.myproject.users.patient.moodTracking.data

import android.content.Context
import mnshat.dev.myproject.R
import mnshat.dev.myproject.users.patient.moodTracking.domain.entity.EmojiMood

class MoodRepository {


    fun getEmojisStatus(context: Context) = listOf(
        EmojiMood(context.getString(R.string.happy), R.drawable.icon_mood, "#ffdb75","#f8ca4a"),
        EmojiMood(context.getString(R.string.calm_relaxed), R.drawable.icon_mood3, "#ffb770","#ff9e3e"),
        EmojiMood(context.getString(R.string.tired_fatigued), R.drawable.icon_mood6, "#61e5a3","#26d57d"),
        EmojiMood(context.getString(R.string.sad_down), R.drawable.icon_mood1, "#d689d6","#c051c0"),
        EmojiMood(context.getString(R.string.anxious_overwhelmed), R.drawable.icon_mood5, "#c051c0","#5994d5"),
        EmojiMood(context.getString(R.string.angry_irritated), R.drawable.icon_mood4, "#c051c0","#c051c0"),
    )



}