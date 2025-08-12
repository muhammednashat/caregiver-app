package mnshat.dev.myproject.util.data

import android.content.Context
import mnshat.dev.myproject.R
import mnshat.dev.myproject.model.DataPatient
import mnshat.dev.myproject.model.Step
import mnshat.dev.myproject.users.patient.tools.breath.data.Duration
import mnshat.dev.myproject.util.MOOD
import mnshat.dev.myproject.util.PROGRAM
import mnshat.dev.myproject.util.SHARING


fun itemList() = listOf(
    DataPatient(R.drawable.ic_daily_program3,"البرنامج اليومي" ,"Daily Program" ,"يمكنك الاطلاع على نتائج البرنامج اليومى للمستخدم" ,"You can view the results of the user's daily program",
        PROGRAM),
    DataPatient(R.drawable.ic_mood4,"الحالة المزاجية" ,"Mood" ,"يمكنك  الحالة المزاجية الخاصة بالمستخدم" ,"You can track the user's mood",
        MOOD),
//    DataPatient(R.drawable.ic_reports6,"التقارير" ,"Reports" ,"يمكنك الاطلاع على نتائج البرنامج اليومى للمستخدم" ,"You can view the results of the user's daily program"),
    DataPatient(R.drawable.ic_posts5,"المشاركات" ,"Posts" ,"رؤية ما تم مشاركته معك" ,"See what has been shared with you",SHARING),
)
fun getListOfDurations(context: Context) = listOf(
    Duration(context.getString(R.string.minutes_5), 5 , R.drawable.min5),
    Duration(context.getString(R.string.minutes_10), 10, R.drawable.min10),
    Duration(context.getString(R.string.minutes_15), 15, R.drawable.min15),
    Duration(context.getString(R.string.minutes_20), 20, R.drawable.min20),
    Duration(context.getString(R.string.minutes_25), 25, R.drawable.min25),
    Duration(context.getString(R.string.minutes_30), 30, R.drawable.min30),
    Duration(context.getString(R.string.minutes_35), 35, R.drawable.min35),
    Duration(context.getString(R.string.minutes_40), 40, R.drawable.min40),
)

fun   stepsBuildStrengthList(context: Context) = listOf(
    Step(
        step = context.getString(R.string.introduction),
        title = context.getString(R.string.you_are_not_alone_on_this_journey),
        description = context.getString(R.string.mental_health_support_text),
        image = R.drawable.img_not_alone
    ), Step(
        step = context.getString(R.string.step_1),
        title = context.getString(R.string.title_step_1),
        description = context.getString(R.string.description_step_1),
        toDo = context.getString(R.string.to_do_step_1),
        image = R.drawable.step1
    ),
    Step(
        step = context.getString(R.string.step_2),
        title = context.getString(R.string.title_step_2),
        description = context.getString(R.string.description_step_2),
        toDo = context.getString(R.string.to_do_step_2),
        image = R.drawable.step2
    ),
    Step(
        step = context.getString(R.string.step_3),
        title = context.getString(R.string.title_step_3),
        description = context.getString(R.string.description_step_3),
        toDo = context.getString(R.string.to_do_step_3),
        image = R.drawable.step3
    ),
    Step(
        step = context.getString(R.string.step_4),
        title = context.getString(R.string.title_step_4),
        description = context.getString(R.string.description_step_4),
        toDo = context.getString(R.string.to_do_step_4),
        image = R.drawable.step4
    ),
    Step(
        step = context.getString(R.string.step_5),
        title = context.getString(R.string.title_step_final),
        description = context.getString(R.string.description_step_5),
        toDo = context.getString(R.string.to_do_step_5),
        image = R.drawable.step5
    )
)

fun stepsCompassList(context: Context) = listOf(
    Step(
        step = context.getString(R.string.introduction),
        title = context.getString(R.string.supporter_compass),
        description = context.getString(R.string.camposs_step_0),
        image = R.drawable.image0
    ), Step(
        step = context.getString(R.string.step_1),
        title = context.getString(R.string.recognizing_signs),
        description = context.getString(R.string.description_camposs_step_1),
        toDo = context.getString(R.string.step_1_action),
        image = R.drawable.image10,
        flag = 2
    ),
    Step(
        step = context.getString(R.string.step_2),
        title = context.getString(R.string.step_2_title),
        description = context.getString(R.string.step_2_desc),
        image = R.drawable.image02,
        flag = 2
    ),
    Step(
        step = context.getString(R.string.step_3),
        title = context.getString(R.string.step_3_title),
        description = context.getString(R.string.step_3_desc),
        image = R.drawable.image03,
        flag = 2
    ),
    Step(
        step = context.getString(R.string.step_4),
        title = context.getString(R.string.step_4_title),
        description = context.getString(R.string.step_4_desc),

        image = R.drawable.image04,
        flag = 2
    ),
    Step(
        step = context.getString(R.string.step_5),
        title = context.getString(R.string.step_5_title),
        description = context.getString(R.string.step_5_desc),
        toDo = context.getString(R.string.step_5_action),
        image = R.drawable.image05,
        flag = 2
    ),
    Step(
        step = context.getString(R.string.step_6),
        title = context.getString(R.string.step_6_title),
        description = context.getString(R.string.step_6_desc),
        image = R.drawable.image06,
        flag = 2
    ),
    Step(
        step = context.getString(R.string.step_7),
        title = context.getString(R.string.step_7_title),
        description = context.getString(R.string.step_7_desc),
        image = R.drawable.image07,
        flag = 2
    ),
    Step(
        step = context.getString(R.string.step_8),
        title = context.getString(R.string.step_8_title),
        description = context.getString(R.string.step_8_desc),
        image = R.drawable.image08,
        flag = 2
    ),
    Step(
        step = context.getString(R.string.step_9),
        title = context.getString(R.string.step_9_title),
        description = context.getString(R.string.step_9_desc),
        toDo = context.getString(R.string.step_9_action),
        image = R.drawable.image09,
        flag = 2
    )
)

fun getListHands()= listOf(
    R.drawable.image_hand1,
    R.drawable.image_hand2,
    R.drawable.image_hand3,
    R.drawable.image_hand4,
    R.drawable.image_hand5,
    R.drawable.image_hand6,
    R.drawable.image_hand7,
    R.drawable.image_hand8,
    R.drawable.image_hand9,
    R.drawable.image_hand10,
    R.drawable.image_hand11,
    R.drawable.image_hand12,
    R.drawable.image_hand13,
    R.drawable.image_hand14,
    R.drawable.image_hand15,
    R.drawable.image_hand16,
)

fun getListSebha()= listOf(
    R.drawable.image_sebha0,
    R.drawable.image_sebha1,
    R.drawable.image_sebha2,
    R.drawable.image_sebha3,
    R.drawable.image_sebha4,
    R.drawable.image_sebha5,
    R.drawable.image_sebha6,
    R.drawable.image_sebha7,
    R.drawable.image_sebha8,
    R.drawable.image_sebha9,
    R.drawable.image_sebha10,
    R.drawable.image_sebha11,
    R.drawable.image_sebha12,
    R.drawable.image_sebha13,
    R.drawable.image_sebha14,
    R.drawable.image_sebha16,
    R.drawable.image_sebha17,
    R.drawable.image_sebha18,
    R.drawable.image_sebha19,
    R.drawable.image_sebha20,
    R.drawable.image_sebha21,
    R.drawable.image_sebha22,
)
