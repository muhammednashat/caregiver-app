package mnshat.dev.myproject.users.patient.moodTracking.domain.entity

class EmojiMood
    (
    val name: String,
    val title:String,
    val subTitle:String,
    val titleSuggestion:String,
    val suggestion:List<SuggestionToDo>,
    val emoji: Int,
    val backgroundColor: String,
    val buttonColor: String,
    val tipBackgroundColor :String

)
