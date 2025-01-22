package mnshat.dev.myproject.util

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mnshat.dev.myproject.firebase.FirebaseService.libraryContents
import mnshat.dev.myproject.commonFeatures.getLibraryContent.domain.entity.LibraryContent
import mnshat.dev.myproject.model.DayTask
import mnshat.dev.myproject.model.Task

//fun getFakeContent(
//    id: Int,
//    type: String = "article",
//    viewCount: Int = 12,
//    religion: Boolean = true,
//    subCategory: String = MENTAL_HEALTH
//): LibraryContent {
//    return LibraryContent(
//        id = id,
//        type = type,
//        viewCount = viewCount,
//        religion = religion,
//        subCategory = subCategory,
//        duration = "2:40",
//        arDescription ="نص  عن القلق  نص  عن القلق" ,
//        enDescription = "text about anxiety." ,
//        date = "2024-08-30",
//        category = "anxiety",
//        arText = " نص  عن القلق  نص  عن القلق",
//        enText = "text about anxiety.",
//        arTitle = "القلق",
//        enTitle = "Anxiety",
//        rate = 3,
//        imageURL = "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2F71GNZ%2Bf6zyL._AC_SL1500_.jpg?alt=media&token=bfeb8c1b-0e47-420a-8cb7-3161aab2c1d6",
//        videoURL = "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/videos%2F3%20_Binary%20Search%20-%20Recursive%20implementation(720P_HD).mp4?alt=media&token=78e30d71-1b51-4ff6-9d0b-de3f6365aff1",
//        audioURL = "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/audios%2F%D8%B9%D9%84%D9%89%20%D9%82%D8%AF%D8%B1%20%D8%A7%D9%87%D9%84%20%D8%A7%D9%84%D8%B9%D8%B2%D9%85.mp3?alt=media&token=bf6e8bbc-a897-4f04-ae55-5b4922aaa1c2"
//    )
//}

fun createContentFake() {

//
//    val contentList = listOf(
//        getFakeContent(id=0),
//        getFakeContent(1, type = ARTICLE , religion = true, viewCount = 12),
//        getFakeContent(2, type = VIDEO , religion = false, viewCount = 13),
//        getFakeContent(3, type = VIDEO , religion = true, viewCount = 133),
//        getFakeContent(4, type = ARTICLE , religion = true, viewCount = 1213, subCategory = HEALTHY_LIVING),
//        getFakeContent(5, type = AUDIO , religion = false, viewCount = 1),
//        getFakeContent(6, type = ARTICLE , religion = true, viewCount = 143),
//        getFakeContent(7, type = VIDEO , religion = false, viewCount = 134),
//        getFakeContent(8, type = ARTICLE , religion = true, viewCount = 345),
//        getFakeContent(9, type = ARTICLE , religion = false, viewCount = 356),
//        getFakeContent(10, type = ARTICLE , religion = true, viewCount = 354),
//        getFakeContent(11, type = VIDEO , religion = true, viewCount = 45),
//        getFakeContent(12, type = AUDIO , religion = false, viewCount = 3435),
//        getFakeContent(13, type = ARTICLE , religion = true, viewCount = 547),
//        getFakeContent(14, type = AUDIO , religion = true, viewCount = 193),
//
//    )
//
//    libraryContents.setValue(contentList)
//        .addOnSuccessListener {
//        }
//        .addOnFailureListener {
//        }
}


fun addFaceDailyTasks() {
    val db = Firebase.firestore
    val dayTask = DayTask()

    val task1 = Task()
    val task2 = Task()
    val task3 = Task()
    val task4 = Task()

    task1.type = 1
    task1.arTitle = "هل تشعر بالحزن أو الألم أو الانزعاج؟"
    task1.enTitle = "Are you feeling upset, sad or in pain?"
    task1.arDescription =
                 "ما أشعر به أحيانًا:\n" +
                "\"أشعر أحيانًا أني لا أرى من ألوان الحياة إلا الأبيض والأسود. أشعر بألم ينهك روحي وتفكيري. إنه يحق لي أن أعيش بسعادة، فشعوري بهذا الحزن والألم يعني أن هناك خطأ ما فيّ، ولا أعرف ماذا أفعل.\"\n" +
                "ما أحتاج إلى معرفته؟\n" +
                "لا بأس على الإطلاق أن تشعر بالضيق أو الحزن أو الألم. فهذه مشاعر ذات طابع إنساني لا تعيب صاحبها، ولا تعني اعتراضا على قدر أو يأسا من الحياة. إذ لا بأس ألا تكون بخير في بعض الأحيان، وهذ المشاعر هي جزء من الحياة، ومهما تتطلع بسعيك نحو الكمال لا بد أن تتقبل كونك ضعيفا، فإنك خلقت ناقصا ولن تكتمل أبدا، ولا يوجد على هذه الأرض إنسان كامل سعيد دائما؛ فالمفتاح للشفاء هو أن تتكيف مع تلك المشاعر وألا تنكرها.\n" +
                "غالبًا ما يخبرنا الآخرين أنه يجب علينا قمع هذه المشاعر، فنسمع منهم كلمات محبطة مثل \" شوف بلاوي الناس تهون مصيبتك \" وغيرها من الكلمات، والتي يشعرونني فيها بأني غير راضٍ بقضاء الله\" وحالي معهم كما يقول المثل (اللي ايده مو تحت الحجر ما بتوجعوا)، ولكن واجبك معهم أن تعمل على قول المثل (إذن من طين وإذن من عجين). لا بأس أن تعترف بهذه المشاعر وتتقبلها.  يقول المثل (دواء الدهر الصبر عليه) وأيضا (الصبر باب للمر حتى يمر) وأيضا (إذا غلي اللحم الصبر رخيص) وتلك هي خطوة أولى نحو فهم نفسك بشكل أفضل؛ لاستعادة فهمك للحياة واكتساب خبرات أقوى لمواجهة ظروفها.\n" +
                "ما يجب القيام به:\n" +
                "1. تقبل واعترف بمشاعرك: من المهم أن تسمح لنفسك بتقبل مشاعرك دون الشعور بالذنب أو الخجل.\n" +
                "2. تحدث عن الأمر: سواء مع صديق أو أحد أفراد الأسرة أو مستشار، فإن التحدث عن مشاعرك يمكن أن يوفر لك الراحة. لست مضطرًا إلى المرور بهذا بمفردك.\n" +
                "3.اسمح لنفسك بالبكاء أو الراحة: في بعض الأحيان، قد تجتاحك المشاعر وهذا أمر طبيعي، فالبكاء أو أخذ الوقت لإعادة شحن طاقتك هي مراحل مهمة في الشفاء.\n" +
                "4.مارس الرعاية الذاتية: شارك في الأنشطة التي تساعدك على الاسترخاء وإعادة شحن طاقتك، سواء كانت كتابة مذكرات أو الذهاب في نزهة؛ فالرعاية الذاتية تتيح لك التركيز على رفاهيتك.\n" +
                "اطلب الدعم المهني: إذا اجتاحك أو استمر معك الحزن أو الألم، فيمكن لمتخصص الصحة النفسية تقديم التوجيه، والأدوات والاستراتيجيات؛ لمساعدتك على التشافي."

    task1.enDescription =
                "<h1>What I sometimes feel:<h1><br>" +
                "\"Sometimes I feel like I can't see anything in life except black and white... I feel a pain that exhausts my soul and my mind... I have the right to live happily... Feeling this sadness and pain means that there is something wrong with me, and I don't know what to do.\"\n" +
                "\n" +
                "<h1>What I need to know?<h1>\n" +
                "It is absolutely okay to feel upset, sad or in pain. These are human feelings that do not shame the person who feels them, and they do not mean objection to the decrees of Allaah The Almighty or time to give up. It is okay to not be okay sometimes. These feelings are part of life, and if you are looking forward to your quest for perfection, you must accept that you are weak, because you were created imperfect and will never be complete, and there is no perfect person on this earth who is always happy. The key to healing is to cope with these feelings and not deny them.\n" +
                "\n" +
                "Others often suggest that we should suppress these feelings, saying things like 'Look at the troubles of others; your misfortune will seem easier’' and other words that make me feel that I am not satisfied with “God's will . Such words can be frustrating and may leave us feeling invalidated or misunderstood, as if our emotions or our right to feel them are not being acknowledged.  As the proverb says, ‘Whose hand is not under the stone does not hurt’, so  practise saying the proverb ‘Let it go in one ear and out the other’. It’s okay to acknowledge and accept these feelings. The proverb says, ‘Patience is a door to bitterness until it passes’, and this is the first step towards understanding yourself better, regaining your acceptance of life’s challenges and experiences to help face its circumstances.\n" +
                "\n" +
                "<h1>What to do?<h1>\n" +
                "<b>1. Accept and acknowledge your feelings:<b> It is important to allow yourself to accept your feelings without feeling guilty or ashamed.\n" +
                "<b>2. Talk about it with somebody you are comfortable talking with:<b> Whether you talk with a friend, family member, or counsellor, talking about your feelings can provide you with comfort. You do not have to go through this alone.\n" +
                "<b>3. Allow yourself to cry or rest:<b> Sometimes, emotions can be overwhelming, and that’s normal. Crying or taking time to recharge are important steps in healing.\n" +
                "<b>4. Practice self-care:<b> Participate in activities that help you relax and recharge your energy, whether by, for example, journalling or going for a walk. Self-care allows you to focus on your well-being.\n" +
                "<b>5. Seek professional support:<b> If grief or pain becomes overwhelming or persistent, a mental health professional can provide guidance, tools, and strategies to help you express that grief and to heal."
    task1.image =
        "https://firebasestorage.googleapis.com/v0/b/myproject-18932.appspot.com/o/images%2F71GNZ%2Bf6zyL._AC_SL1500_.jpg?alt=media&token=bfeb8c1b-0e47-420a-8cb7-3161aab2c1d6"
    task1.gender = 1
    task1.ageGroup = 1
    task1.religion = false



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
    dayTask.behaviorActivation = listOf(task1, task1, task1, task1, task1)
    dayTask.spiritual = listOf(task1, task2, task3, task4)


    for (x in 1..30) {
        db.collection("daily_programs").document("" + x).set(dayTask)
    }


}
