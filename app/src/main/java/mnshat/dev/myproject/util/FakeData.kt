package mnshat.dev.myproject.util

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.model.Content
import mnshat.dev.myproject.model.DayTask
import mnshat.dev.myproject.model.Task

fun getFakeContent(id:Int): Content {
    return  Content(
        id = id,
        date = "2024-08-30",
        category = "anxiety",
        arText = " نص  عن القلق ",
        enText = "text about anxiety.",
        arTitle = "القلق",
        enTitle = "Anxiety",
        type = "article",
        rate = 3,
        viewCount = 120,
        imageURL ="https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2F71GNZ%2Bf6zyL._AC_SL1500_.jpg?alt=media&token=bfeb8c1b-0e47-420a-8cb7-3161aab2c1d6",
        videoURL = "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/videos%2F3%20_Binary%20Search%20-%20Recursive%20implementation(720P_HD).mp4?alt=media&token=78e30d71-1b51-4ff6-9d0b-de3f6365aff1",
        audioURL = "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/audios%2F%D8%B9%D9%84%D9%89%20%D9%82%D8%AF%D8%B1%20%D8%A7%D9%87%D9%84%20%D8%A7%D9%84%D8%B9%D8%B2%D9%85.mp3?alt=media&token=bf6e8bbc-a897-4f04-ae55-5b4922aaa1c2"
    )
}

fun createContentFake(){
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference(EDUCATIONAL_CONTENTS)

    val contentList = listOf(
        getFakeContent(0),
        getFakeContent(1),
        getFakeContent(2),
        getFakeContent(3),
        getFakeContent(4),
        getFakeContent(5),
        getFakeContent(6),
        getFakeContent(7),
        getFakeContent(8),
        getFakeContent(9),
        getFakeContent(10),
        getFakeContent(11),
        getFakeContent(12),
        getFakeContent(13),
        getFakeContent(14),
        getFakeContent(15),
        getFakeContent(16),
        getFakeContent(17),
        getFakeContent(18),
        getFakeContent(19),
        getFakeContent(20),
        getFakeContent(21),
        getFakeContent(22),
        getFakeContent(23)
    )

    myRef.setValue(contentList)
        .addOnSuccessListener {
        }
        .addOnFailureListener {
        }
}




fun addFaceDailyTasks(){
    val db = Firebase.firestore
    val dayTask = DayTask()

    val task1 = Task()
    val task2 = Task()
    val task3 = Task()
    val task4 = Task()

    task1.type = 1
    task1.arTitle = "تحدى اليوم"
    task1.enTitle = "challenge today"
    task1.arDescription =
        "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
    task1.enDescription =
        "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
    task1.image =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2F71GNZ%2Bf6zyL._AC_SL1500_.jpg?alt=media&token=bfeb8c1b-0e47-420a-8cb7-3161aab2c1d6"
    task1.gender = 1
    task1.ageGroup = 1
    task1.religion = true

    task2.type = 2
    task2.arTitle = "مشاهدة الفديو"
    task2.enTitle = "See Video"
    task2.arDescription =
        "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
    task2.enDescription =
        "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
    task2.link =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/videos%2F3%20_Binary%20Search%20-%20Recursive%20implementation(720P_HD).mp4?alt=media&token=78e30d71-1b51-4ff6-9d0b-de3f6365aff1"
    task2.gender = 1
    task2.ageGroup = 1
    task2.religion = true


    task3.type = 3
    task3.arTitle = "اﻹستماع الى البودكاست"
    task3.enTitle = "Listening to podcast"
    task3.arDescription =
        "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
    task3.enDescription =
        "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
    task3.link =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/audios%2F%D8%B9%D9%84%D9%89%20%D9%82%D8%AF%D8%B1%20%D8%A7%D9%87%D9%84%20%D8%A7%D9%84%D8%B9%D8%B2%D9%85.mp3?alt=media&token=bf6e8bbc-a897-4f04-ae55-5b4922aaa1c2"
    task3.image =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2F71GNZ%2Bf6zyL._AC_SL1500_.jpg?alt=media&token=bfeb8c1b-0e47-420a-8cb7-3161aab2c1d6"
    task3.gender = 1
    task3.ageGroup = 1
    task3.religion = true

    task4.type = 1
    task4.arTitle = "تحدى اليوم"
    task4.enTitle = "challenge today"
    task4.arDescription =
        "فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام فى هذه المهمة عليك القيام"
    task4.enDescription =
        "In this task you have to do In this task you have to do In this task you have to do In this task you have to do"
    task4.link =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/audios%2F%D8%B9%D9%84%D9%89%20%D9%82%D8%AF%D8%B1%20%D8%A7%D9%87%D9%84%20%D8%A7%D9%84%D8%B9%D8%B2%D9%85.mp3?alt=media&token=bf6e8bbc-a897-4f04-ae55-5b4922aaa1c2"
    task4.image =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2Fimg1.jpg?alt=media&token=d0823e7a-416f-4798-ac0a-ee91976c8082"
    task4.gender = 1
    task4.ageGroup = 1
    task4.religion = true



    dayTask.educational = listOf(task1, task2, task3, task4)
    dayTask.behaviorActivation = listOf(task1, task1, task1, task1,task1)
    dayTask.spiritual = listOf(task1, task2, task3, task4)


    for (x in 1..30) {
        db.collection("daily_programs").document("" + x).set(dayTask)
    }



}
