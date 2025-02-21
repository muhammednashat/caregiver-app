package mnshat.dev.myproject.users.patient.dailyprogram.presentaion

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.LayoutTaskBinding
import mnshat.dev.myproject.databinding.StaionDescriptionDialogBinding
import mnshat.dev.myproject.users.patient.dailyprogram.domain.entity.Task
import mnshat.dev.myproject.util.ENGLISH_KEY
import mnshat.dev.myproject.util.LANGUAGE
import mnshat.dev.myproject.util.RELIGION

@AndroidEntryPoint
open class BaseDailyProgramFragment : BaseFragment() {

     val viewModel: DayTaskViewModel by viewModels()
    private  var _fragment = ""
    lateinit var binding: LayoutTaskBinding
    lateinit var task: Task
    var player: ExoPlayer? = null
    var isSyncNeeded = false

    fun getTaskFromList(index: Int, numberTask: Int ,) {
        showProgressDialog()
        viewModel.listOfTasks.let { listOfTasks ->
             task = listOfTasks[index]
            task.let {
                val currentLang = viewModel.sharedPreferences.getString(LANGUAGE)
                if (currentLang != ENGLISH_KEY) {
                    setText( numberTask,task.arTitle , task.arDescription)
                } else {
                    setText( numberTask,task.enTitle , task.enDescription)
                }
                checkType(task.type)
            }
        }
    }

    private fun setText ( numberTask: Int ,title: String?, description: String?){
      if (title?.isEmpty() == true){
          binding.textTitle.visibility = View.GONE
      }else{
          if (_fragment == "BehaviouralActivationFragment"){
              binding.textTitle.text = title
          }else{
              binding.textTitle.text = title
          }
      }
        binding.textDescription.text = Html.fromHtml(
 //     <p></p>
            /**
             * <p></p>
             * <h2></h2>
             * <b></b>
             * <font color='#496c38'></font>
             */

            "<h2>كيف يعمل؟<h2><p>التخيل هو تمرين ذهني يمكن أن يساعدك على التعامل مع المواقف المسببة للتوتر بثقة أكبر وقلق أقل. من خلال تخيل نفسك وأنت تتعامل مع موقف ما بهدوء، يمكنك نقل تركيزك من الفشل إلى النجاح. الممارسة المنتظمة تعزز الثقة، تقلل القلق، وتحول طريقة التفكير من الشك إلى الإيمان بالقدرات، مما يجعلك مستعدًا لمواجهة العقبات الحقيقية بعقلية إيجابية.</p><h3>دليل خطوة بخطوة:</h3><b>ابحث عن مكان هادئ ومريح</b><br> للجلوس أو الاستلقاء دون أي مشتتات، وخذ بضع أنفاس عميقة.<br><br.<b>فكر في موقف سبب لك مؤخرًا التوتر أو القلق،</b>  مثل محادثة صعبة عليك إجراؤها أو اختبار قادم، وحاول تخيل ذلك الموقف.<br><br><b>تخيل المشهد بكل تفاصيله: ما الذي تفعله؟ ماذا ترى؟ ماذا تسمع؟ كيف تشعر؟</b>تخيل نفسك تتعامل مع الموقف بهدوء وثقة. ركّز على كل التفاصيل:<br>-أين أنت الآن؟ تخيل البيئة من حولك، سواء كنت في المنزل، المدرسة، أو أي مكان آخر.<br>-What are you doing now? Picture yourself speaking confidently, staying calm, and effectively handling the situation.<br>-How are you feeling? Concentrate on the positive emotions: calm, in control, and prepared.<br><br> <b>4. Picture yourself handling difficulties.</b> Now, instead of seeing everything go well, picture things going wrong or happening unexpectedly. Take a deep breath, calm down, and focus on maintaining your confidence. Remind yourself that difficulties are normal, and you are entirely capable of dealing with the situation effectively.<br><br>5. As you visualise overcoming the problem,<b> think about how good it feels to handle the situation well.</b> Imagine yourself feeling calm, competent, and proud of how you handled things. Allow yourself to fully experience these emotions, as if they were happening right now.<br><br><b>6. Repeat positive affirmations </b>that will help you to improve your ability to handle the situation. For instances, repeat: I can handle this situation calmly and confidently<br><br>-7. After visualising your success, now, slowly <b>open your eyes and reflect on how you feel.</b> Do you feel more confident and less concerned about the situation? Consider how your attitude shifted from stress to calm preparedness.<br><br>8. Now that you've gained confidence from your visualization, it's time to <b>put that positive energy into action.</b> Visualization prepares your mind, but taking a small steps can help to make your success a reality. Choose one simple, achievable step that brings you closer to the goal you have set.<br><br>For example,<br>- if you're preparing for a difficult talk, your little action could be writing down key points or practicing what you're going to say.<br>- If you imagined yourself successfully presenting a presentation, a tiny action may be going over a piece of your notes or practicing in front of the mirror for five minutes.<br><br>"
//            "<h2>How it works?<h2><p>Visualization is a mental practice that can help you manage stressful situations more confidently and with less stress. By imagining yourself handling a situation calmly, you shift focus from failure to success. Regular practice increases confidence, reduces worry, and shifts mindset from doubt to belief in abilities, preparing you to face real-world obstacles with a positive attitude.</p><h3>Step-by-Step Guide:</h3><b>1. Find a quiet, comfortable place</b><br> to sit or lie down with no distractions and take a few deep breaths.<br><br.<b>2. Think about something that has recently caused you stress or worry,</b> such as a difficult conversation you need to have or an exam, and try to picture that situation.<br><br><b>3.Imagine the setting, what you're doing, what you see, hear, and feel.</b> Imagine yourself handling the situation calmly and confidently. Consider every detail:<br>-Where are you now? Imagine the setting—whether it is your home, a school, or another location.<br>-What are you doing now? Picture yourself speaking confidently, staying calm, and effectively handling the situation.<br>-How are you feeling? Concentrate on the positive emotions: calm, in control, and prepared.<br><br> <b>4. Picture yourself handling difficulties.</b> Now, instead of seeing everything go well, picture things going wrong or happening unexpectedly. Take a deep breath, calm down, and focus on maintaining your confidence. Remind yourself that difficulties are normal, and you are entirely capable of dealing with the situation effectively.<br><br>5. As you visualise overcoming the problem,<b> think about how good it feels to handle the situation well.</b> Imagine yourself feeling calm, competent, and proud of how you handled things. Allow yourself to fully experience these emotions, as if they were happening right now.<br><br><b>6. Repeat positive affirmations </b>that will help you to improve your ability to handle the situation. For instances, repeat: I can handle this situation calmly and confidently<br><br>-7. After visualising your success, now, slowly <b>open your eyes and reflect on how you feel.</b> Do you feel more confident and less concerned about the situation? Consider how your attitude shifted from stress to calm preparedness.<br><br>8. Now that you've gained confidence from your visualization, it's time to <b>put that positive energy into action.</b> Visualization prepares your mind, but taking a small steps can help to make your success a reality. Choose one simple, achievable step that brings you closer to the goal you have set.<br><br>For example,<br>- if you're preparing for a difficult talk, your little action could be writing down key points or practicing what you're going to say.<br>- If you imagined yourself successfully presenting a presentation, a tiny action may be going over a piece of your notes or practicing in front of the mirror for five minutes.<br><br>"




//            "<p>دعنا نستلهم هذا التمرين من كلمات الدكتور مصطفى محمود:</p><p><font color='green'><b>إن مشكلتك ليست في السنوات التي ضاعت، ولكن سنواتك القادمة التي ستضيع حتما إذا واجهت الدنيا بنفس العقلية</b></font></p>التغيير الذي تبحث عنه في كل مكان وتنتظره من شخص آخر، هو في الحقيقة أنت من يملكه لذا ما عليك الآن فعله، هو أن تلتفت لتفكيرك وتبدأ في تغييره لصنع التغير الأعظم الذي تنتظره.<br><h2>خطوات التمرين:</h2><p><b>1.التعرف على الأفكار السلبية الحالية</b><br>اجلس في مكان هادئ<br>   اكتب 3 أفكار أو معتقدات سلبية تشعر أنها تعيق تقدمك.<br> مثال:<br>فات الأوان على التغيير.<br>-ليس لدي أي فرصة للنجاح.<br>-أخطائي السابقة دمرت مستقبلي.<br>الآن، اسأل نفسك:<font color='red'> من 100٪، ما مدى صحة هذه الفكرة؟</font><b>2.إعادة صياغة إحدى الأفكار السلبية</b><br>-اختر فكرة واحدة من الأفكار التي كتبتها.<br>-اسأل نفسك:<br> هل يمكنك تغيير هذه الفكرة إلى فكرة بديلة؟<br>أعد صياغة الفكرة بأسلوب إيجابي.<br>أمثلة<br>بدلًا من <font color='red'><b>فات الأوان على التغيير</b></font> → <font color='green'><b>التغيير ممكن. لقد تمكنت من إجراء تغييرات في الماضي</b></font></p><p>بدلًا من<font color='red'>لا أحد يهتم بي أو بما أفعله</font> → <font color='green'>هناك بعض الأشخاص الذين يهتمون بي</font></p><p>الآن، اسأل نفسك: <font color='red'>-من 100٪، ما مدى صحة هذه الفكرة؟</font>إذا بدت لك هذه الفكرة البديلة صحيحة إلى حد ما، انتقل إلى الخطوة 3</p><p><b><font color=’red’>-أعد تقييم فكرتك السلبية من 100٪. هل تغير تقييمك بعد أن اقترحت فكرة بديلة؟</b></p></font><b>3.اقترح عبارة إيجابية موجهة نحو الفعل</b><br>أمثلة:<br><p><b><font color='green'>يبدأ التغيير بخطوة صغيرة واحد<br>يمكنني البدء في بناء علاقات أقوى من خلال التواصل والتفاعل مع الآخرين</font></b></p><b>4.اتخاذ خطوة عملية صغيرة</b><br>فكر في خطوة بسيطة يمكنك اتخاذها اليوم لتعكس الفكرة الإيجابية الجديدة التي كتبتها.<br>مثال<br>إذا كانت فكرتك الجديدة:  <p><b><font color='green'>يمكنني بدء بناء علاقات أقوى من خلال المبادرة والتواصل مع الآخرين.</font></b></p><b>-فالخطوة التي يمكن ان تقوم بها:</b><br>  أرسل رسالة نصية لشخص تحبه واسأله عن يومه.<br> أو خطط للاتصال بصديق قديم لبضع دقائق.<br><br><b>5.تسجيل تقدمك ومشاعرك</b><br>في نهاية اليوم، سجل في الدفتر أو الورقة:<br>-ما الذي قمت به؟<br>  -كيف شعرت أثناء تنفيذ الخطوة؟<br>  -ما الذي تعلمته من هذه التجربة؟<br></b></p><br><p><b><font color='red'>\uD83D\uDCA1 ملاحظة: إذا كان تقييمك للفكرة السلبية مرتفعًا جدًا (قريبًا من 100٪) ولم يتغير في الخطوة 2 بعد اقتراحك لفكرة بديلة، أو إذا كانت فكرتك البديلة غير قابلة للتصديق بشكل كبير (قريبة من 0٪)، فقد تحتاج إلى التحدث مع شخص تثق به أو مع أخصائي في الصحة النفسية. </font></b></p><p><b>ضع عبارة مصطفى محمود في مكان تراه يوميًا (على الهاتف، أو على ورقة مكتوبة) لتذكرك بأهمية تغيير طريقة التفكير.</b></p>"
//            "<p>Let’s draw inspiration for this exercise from the words of Dr. Mustafa Mahmoud:</p><p><font color='#496c38'>Your problem is not the years that have been lost, but the years to come that will surely be lost if you face life with the same mindset.</font></p><h2>Steps for the Exercise:</h2><p><b>1.Identify Your Current Negative Thoughts</b><br>-Sit in a quiet place.<br>       -Write down three negative thoughts or beliefs that you feel are holding you back. Examples:<br>“It’s too late to change.<br>“I have no chance of success.”<br>“My past mistakes have ruined my future.”<br>-Now, ask yourself:<font color='red'> Out of 100%, how true is this thought?</font><b>2.Reframe One Negative Thought</b><br>-Choose one thought from the ones you’ve written down.<br>-Ask yourself:<br>   Can you change this thought to an alternative thought?<br>-Reframe the thought into a positive statement.<br>Examples:<br>Instead of <font color='red'><b>It’s too late to change</b></font> → <font color='green'><b>Change is possible. I have been able to make changes in the past</b></font></p><p>Instead of <font color='red'>No one cares about me or what I do</font> → <font color='green'>There are some people who care about me</font></p><p>Now, ask yourself: <font color='red'>Out of 100%, how true is this thought?</font>-If this alternative thought seems somewhat true, go to step 3.</p><p><b><font color=’red’>-Re-rate your negative thought out of 100%. Has your rating changed after you proposed an alternative thought?</b></p></font><b>3.Propose a positive action-oriented statement</b><br>Examples:<br><p><b><font color='green'>Change begins with a single small step I can start building stronger relationships by reaching out and connecting with others</font></b></p><b>4.Take a Small Practical Step</b><br>-Think of a simple action you can take today to reflect the positive thought you’ve reframed.<br>Example:<br>If your new thought is: <p><b><font color='green'>I can start building stronger relationships by reaching out and connecting with others</font></b></p><b>- Your step could be:</b><br>  Send a text to someone you care about and ask how their day is going.<br>  Plan to call an old friend for a few minutes.<br><br><b>5.Track Your Progress and Feelings</b><br>-At the end of the day, jot down in a notebook or on a piece of paper:<br>  What did you do?<br>  How did you feel while taking that step?<br>  What did you learn from the experience?<br><p><b><font color='red'>\uD83D\uDCA1 Note: If your rating of the negative thought is very high (close to 100%) and does not change at Step 2 after you have proposed an alternative thought, or your alternative thought is not very believable (close to 0%), you may need to talk to another person you trust, or to a mental health professional.</font></b></p><br><p><b><font color='red'>\uD83D\uDCA1 Note: If your rating of the negative thought is very high (close to 100%) and does not change at Step 2 after you have proposed an alternative thought, or your alternative thought is not very believable (close to 0%), you may need to talk to another person you trust, or to a mental health professional.</font></b></p><p><b>Place Dr. Mustafa Mahmoud’s quote somewhere you’ll see it daily (on your phone screen or written on paper) as a reminder of the importance of changing your mindset.</b></p>"




        //            "<h2><font color='#496c38'>{قُلْ لَنْ يُصِيبَنَا إِلَّا مَا كَتَبَ اللَّهُ لَنَا هُوَ مَوْلَانَا ۚ وَعَلَى اللَّهِ فَلْيَتَوَكَّلِ الْمُؤْمِنُونَ}</font><br><br><h2><p>[٥١:التوبة]</p><b>اقرأ الآية القرآنية بتأني</b>وتأمل معناها، وكيف يمكن أن تساعدك على تحدي الأفكار السلبية التلقائية. استخدم الأسئلة التالية لتتعلم كيفية تغيير نظرتك وثقتك بخطة الله أثناء الأوقات الصعبة.<br><h2>اسأل نفسك:</h2><b>1.ما معنى التوكل على الله؟ </b><br>-تأمل ماذا يعني أن تضع اعتمادك الكامل على الله.<br><br><b>2.كيف ستبدو حياتي إذا وثقت بخطة الله بالكامل؟</b><br><br>-فكر في كيفية استجابتك للمشاكل والصعوبات. <br>-تخيل كيف ستشعر وتتصرف إذا كنت واثقًا تمامًا أن كل شيء يحدث وفقًا لخطة الله.<br><br><b>3.هل يمكنني أن أتذكر موقفًا معينًا أظهرت فيه خططي السلبية أنها خاطئة بسبب خطة الله الأفضل؟</b><br><br> مثال:عندما لم أحصل على الوظيفة التي كنت أتمناها بشدة، ثم فكرت: لن أنجح أبدًا. ولكن لاحقًا وجدت فرصة أفضل تناسبني أكثر. أدركت أن الله قد خطط لي شيئًا أفضل.<br><br>-تأمل في تحدٍ سابق، حيث انتهت الأمور بشكل أفضل مما كنت تتوقع.<br>-كيف ظهرت خطة الله في ذلك الموقف؟ وكيف يساعدك هذا الآن؟<br><br><b>4.فكر الآن في كيفية تطبيق الإيمان بأن كل شيء يحدث بمشيئة الله في حياتك اليومية، خصوصًا عندما تحاول الأفكار السلبية السيطرة.</b><br><br> "
//            "<font color='#496c38'>{قُلْ لَنْ يُصِيبَنَا إِلَّا مَا كَتَبَ اللَّهُ لَنَا هُوَ مَوْلَانَا ۚ وَعَلَى اللَّهِ فَلْيَتَوَكَّلِ الْمُؤْمِنُونَ}</font><br><br><p>[٥١:التوبة]</p><b>Translation:</b> “Say, “Nothing will happen to us, except what Allah has destined for us. He is our Protector. So in Allah, let the believers trust Read the Quranic verse or Āyah carefully and reflect on its meaning, particularly in relation to how it can help you challenge negative automatic thoughts. Use the following questions to learn how to shift your mindset and trust in Allah’s plan during difficult times.<br><h2>Ask your self:</h2><b>1.What does the concept of 'reliance on Allah' ('التوكل على الله') mean? </b><br>-Reflect on what it means to place full reliance on Allah.<br><br><b>2.How would my life  look like if I fully trusted Allah's plan?</b><br><br>-Think about how you would react to problems and difficulties. <br>-Imagine how you would feel and act if you completely trusted that everything is happening according to Allah’s plan.<br><br><b>3.Can I think of a specific situation where my negative thoughts were proven wrong by Allah’s better plan? </b><br><br>Example: When I didn’t get the job I really wanted, I kept thinking, I’ll never succeed.' But later, I found a better opportunity that was more suited to me. I realise that Allah had something better in planned for me.<br><br>-Reflect on a past challenge where things turned out better than you expected.<br>-How did Allah’s plan reveal itself in that situation, and how does that experience help you now?<br><br><b>4.Now, think how you could implement the belief that everything happens according to Allah's will into your daily life, particularly when negative thoughts attempt to take over.</b><br><br> "

//            "<h2>How it works?</h2>Recognize and question your negative thoughts or worries by asking if they are founded on assumptions or facts, which leads to a more sympathetic and fair judgement on your efforts.<br><h3>Step-by-Step Guide:</h3><b>1. Think about a recent situation that made you stressed or worried. </b> Consider the first unpleasant thought that comes to your mind. <br><br><font color ='red'><b>\uD83D\uDCA1 Note: These automatic thoughts often occur without our awareness, but they have a significant impact on how we feel.<br> Example: You've been trying to keep up with your family and professional obligations, but recently you're feeling overwhelmed. You may feel like you're failing as a parent or partner. The thought crosses your mind I am not doing enough for my family, I am letting my family down.</b></font><br><br><b>2. Write down your thoughts</b> this will help you address them and identify potential causes of this stress or anxiety.<br><br><b>3. Now, ask yourself:</b><br>-Is this thought based on facts or assumptions?<br>-What proof do I have to support up my thought?<br>-What evidence do I have that contradicts this thought? what I’ve done well?<br>-Am I being harsh on myself?<br>-How would I respond if a close friend or family member had this same thought?<br><br><b>4. After challenging your thought,</b> consider a more balanced and realistic alternate thought.<br><br><font color='red'><b>\uD83D\uDCA1 Note: This new thought must reflect a more pleasant and fair perspective.<br>Example: I may be busy, but I'm doing my best to support my family. I'm not perfect, but I care deeply for them and always make time for what important.</b></font><br><br><b>5. When you're feeling guilty or inadequate,</b> practice this on a regular basis. Instead of allowing stress to take over, focus on your contributions and how much your family values you.<br>"
//            "<h2>كيف يعمل هذا التحدي؟</h2>تعرف على أفكارك السلبية أو مخاوفك، وقم بطرح أسئلة، لمعرفة ما إذا كانت مبنية على افتراضات أم حقائق؛ مما يؤدي إلى تقييم أكثر تعاطفًا وإنصافًا لجهودك.<br><h3>دليل خطوة بخطوة:</h3><b>1. فكر في موقف حديث جعلك تشعر بالتوتر أو القلق. </b> ضع في اعتبارك أول فكرة مزعجة تخطر في بالك. <br><br><font color ='red'><b>\uD83D\uDCA1 ملاحظة: غالبًا ما تحدث هذه الأفكار التلقائية دون إدراك منا، ولكنها تؤثر بشكل كبير على شعورنا.<br> مثال: كنت تحاول الوفاء بالتزاماتك العائلية والمهنية، ولكنك تشعر بالإرهاق مؤخرًا. لذا قد تشعر أنك تخفق كوالد أو شريك، وتخطر ببالك فكرة: أنا لا أفعل ما يكفي لعائلتي. أنا أخيب ظنهم.</b></font><br><br><b>2. اكتب أفكارك:</b> .يساعدك ذلك على مواجهتها، وتحديد الأسباب المحتملة لهذا التوتر أو القلق.<br><br><b>3. .اسأل نفسك الآن:</b><br>-هل هذه الفكرة مبنية على حقائق أم افتراضات؟<br>-ما الدليل الذي أملكه لدعم هذه الفكرة؟<br>-ما الأدلة التي تناقض هذه الفكرة؟ وما الذي فعلته بشكل جيد؟<br>-هل أكون قاسيًا على نفسي؟<br>-كيف سأرد إذا كان صديق مقرب أو أحد أفراد العائلة لديه نفس الفكرة؟<br><br><b>4. بعد تحدي فكرتك</b> .فكر في فكرة بديلة أكثر توازنًا وواقعية.<br><br><font color='red'><b>\uD83D\uDCA1 ملاحظة: يجب أن تعكس هذه الفكرة الجديدة وجهة نظر أكثر لطفًا وإنصافًا.<br>مثال: قد أكون مشغولًا، لكنني أبذل قصارى جهدي لدعم عائلتي. لست مثاليًا، لكنني أهتم بهم بعمق وأخصص دائمًا الوقت للأشياء المهمة.</b></font><br><br><b>5. عندما تشعر بالذنب أو التقصير</b> . مارس هذا التمرين بانتظام. بدلًا من السماح للتوتر بالسيطرة عليك، ركز على ما ساهمت به، وكيف يقدرك أفراد عائلتك.<br>"

//            "Negative thoughts cause a person to feel helpless and hopeless, and kill hope. The Qur'anic verse,<br>﴿ إِنَّ اللَّهَ لَا يُغَيِّرُ مَا بِقَوْمٍ حَتَّى يُغَيِّرُوا مَا بِأَنْفُسِهِمْ ﴾<br> ( translated as ‘Indeed, Allah will not change the condition of a people until they change what is in themselves’) came to build confidence in a person’s abilities and that change is within personreach. His happiness or misery is based on a decision he makes for himself. The Qur'anic verse clearly informs you that your life is made by your thoughts and decisions.<br>"
//            "الأفكار السلبية تسبب للإنسان العجز واليأس، وتقتل الأمل وقد جاءت الآية الكريمة :<br>﴿ إِنَّ اللَّهَ لَا يُغَيِّرُ مَا بِقَوْمٍ حَتَّى يُغَيِّرُوا مَا بِأَنْفُسِهِمْ ﴾<br> لتبني للإنسان الثقة بقدراته، وأن التغيير في متناول يده، فسعادته أو شقائه مبني على قرار يتخذه بنفسه. والآية الكريمة تنبؤك بشكل واضح أن حياتك من صنع أفكارك وقراراتك.<br>"



        //"كانت حنان تستعد لاجتماع عائلي كبير في منزلها. واستضافتها الدائمة لمثل هذه المناسبات أمرًا مهمًا بالنسبة لها، لكن ذلك يجعلها تشعر بالتوتر من أجل أن يكون كل شيء مثاليًا. عندما انتهت من الطهي، أدركت أن الطبق الرئيسي قد احترق قليلاً، ففزع قلبها بأن هذه كارثة، وفكرت، سيعتقد الجميع أنني لا أستطيع حتى الطهي بشكل صحيح<br>ظل هذا الفكر معها وهي تسارع الوقت لإنهاء الاستعدادات. بدأت تلاحظ كل شيء فلم يكن المنزل نظيفًا بما فيه الكفاية، ولم تكن طاولة الطعام مرتبة بما فيه الكفاية، وقد يعتقد الضيوف أنها غير منظمة. تراكمت كل الأفكار؛ مما جعلها تشعر بقلق أكبر<br>بحلول الوقت الذي وصلت فيه الأسرة، لم تتمكن حنان من التخلص من توترها. كانت تتحرك ببطء خلال العشاء، مقتنعة أن الجميع يحكمون عليها بصمت. وعندما أثنى أحدهم على الطعام، لم تسمع في ذهنها سوى إنهم مهذبون فقط. طوال المساء، شعرت وكأنها فشلت، على الرغم من أن عائلتها قضت وقتًا رائعًا.<br><h2>ولكن ما الذي أوصل حنان إلى الشعور بالفشل وعكر صفو يومها، وحرمها من الاستمتاع بوقتها مع عائلتها؟</h2>إنها الأفكار. تتُولَد الأفكار في العقل في كل موقف، وغالبًا ما تكون المشاعر سواء كانت سلبية أم إيجابية مرتبطة بهذه الأفكار. ورغم أن الأفكار قد تؤثر على المشاعر، إلا أن العلاقة يمكن أن تسير أيضًا في الاتجاه الآخر<br><br>• “انا فاشل.”<br>• “لا شيء يسير على ما يرام بالنسبة لي.”<br>• “لن أتحسن أبدًا.”<br>قد تبدو هذه الأفكار صغيرة، لكنها مع الوقت تتراكم بسرعة. فعندما تبدأ في الاعتقاد بأني لن أتحسن أبدا ويدور في ذهنك كما في المثل إجا ليكحلها عماها، فذلك يمنعك من تجربة أشياء جديدة أو الاعتناء بنفسك. بمرور الوقت لا تبق هذه الأفكار في ذهنك فحسب، بل تؤثر على مزاجك وسلوكك وكيفية تفاعلك مع الآخرين؛ و يجعل شعورك أسوأ مما كان.<br><h2>ما يجب أن تعرفه؟</h2>أفكارك لها تأثير كبير على مشاعرك وسلوكك. فالاكتئاب  ما هو إلا نتاج فكرة سلبية عن النفس أو عن الحياة أو كليهما. فمجرد أنك تفكر في شيء لا يعني ذلك أنه صحيح. <br><h2>ما يجب القيام به؟</h2><b> 1.تعرف على النمط:</b><br>لاحظ متى تتكرر أفكارك السلبية، وما هي المحفزات أو المواقف أو الأوقات التي تزيد من حدة هذه الأفكار. على سبيل المثال، إذا كنت تفكر في كل مرة يعلّق فيها مديرك بأنه: لا شيء يسير على ما يرام بالنسبة لي، فقد يؤدي ذلك إلى أفكار أخرى مثل: أنا دائمًا أخطئ ولست جيدًا في أي شيء. لذا حاول أن تتراجع خطوة للوراء وتذكر المثل: سد الباب اللي يجيك منه الريح واستريح. واسأل نفسك: هل كل شيء يسير بشكل خاطئ حقًا، أم أن حالتي المزاجية أو تفكيري الآن يجعلني أراه هكذا؟<br><br><b2.تحدَّ أفكارك السلبية:</b><br>في المرة القادمة التي تجد فيها نفسك تفكر بأفكار مثل: لا شيء يسير على ما يرام بالنسبة لي، توقف واسأل نفسك: هل هذا صحيح حقًا؟ فكّر في الأدلة التي تدعم هذا الاعتقاد والأدلة التي تنفيه.<br><br><b>3.إعادة صياغة الأفكار السلبية بأفكار أكثر صحة: </b><br>بعد أن تفكر في الأدلة على فكرة أنه لا شيء يسير على ما يرام بالنسبة لي، حاول إعادة صياغتها بشيء أكثر تفاؤلًا: بعض الأشياء لا تسير بشكل جيد بالنسبة لي، لكن هذا لا يعني أن لا شيء يسير بشكل جيد.<br>قد تجد أنك قاسٍ على نفسك أكثر مما على الآخرين. لذا يمكنك تجربة هذا مع اعتقادات أكثر تحديًا، مثل: أنا دائمًا أخطئ أو لست جيدًا في أي شيء. فكّر في الأدلة التي تدعم وتناقض هذه الأفكار، وفكر في اعتقاد بديل: لقد واجهت إخفاقات، لكن هذا لا يعني أنني فاشل.<br><br><b>تذكر:</b><br>إن أفكارك تشكل مشاعرك، ولكنك أنت من يستطيع تشكيل أفكارك.<br>إن تعلم تحدي أنماط التفكير السلبية وتغييرها ليس بالأمر السهل؛ ولكنه وسيلة قوية لتحسين حالتك المزاجية وسلوكك. في المرة القادمة التي تشعر فيها بأنك عالق في حلقة مفرغة من السلبية خذ لحظة للتوقف وإعادة صياغة الأفكار.<br>"
//"Hanan was preparing for a big family gathering at her home. Hosting such events was always important to her, but it also made her feel stressed about making sure everything was perfect. When she finished cooking, she realized that the main dish was slightly burnt. “This is a disaster,” she thought, “Everyone will think I can’t even cook properly.This thought stayed with her as she raced to finish the preparations. She started noticing everything: the house wasn’t clean enough, the dining table wasn’t arranged enough, and the guests might think she was disorganized. All the thoughts piled up, making her feel even more anxious.<br>By the time the family arrived, Hanan couldn’t shake her stress. She moved slowly through dinner, convinced that everyone was silently judging her. When someone complimented the food, all she could hear in her mind was “They’re just being polite”. Throughout the evening, she felt like she had failed, even though her family had had a great time.<br><h2>But what made Hanan feel like a failure, spoil her day, and deprive her of enjoying her time with her family?!</h2>It’s thoughts… Thoughts are generated in the mind in every situation, and moods, whether negative or positive, are often associated with these thoughts. While thoughts can influence emotions, the connection can also work in the opposite direction.Like Hanan, we can allow negative thoughts to shape our entire day. Have you ever had thoughts such as these?:<br>• “I’m a failure.”<br>• “Nothing is going right for me.”<br>• “I’ll never get better.”<br>These thoughts may seem small, but over time they quickly add up. When you start to believe that “I’ll never get better” as the proverb says, “Instead of applying kohl to her eyes, he blinded her”, this will prevent you from trying new things or taking care of yourself. Over time, these thoughts don’t just stick with you—they affect your mood, your behaviour, and how you interact with others, making you feel worse.<br><h2>What you need to know?</h2>Your thoughts have a huge impact on how you feel and behave. An enduring pattern of profoundly negative thoughts about yourself, your life, or both over time can lead to depression. Just because you think or believe something doesn’t mean it’s true.<br><h2>What to do?</h2><b>1. Recognize the pattern:</b><br>Notice when your negative thoughts recur, and what triggers, situations, and times these thoughts occur and escalate. For example, if every time your boss comments, you think, “Nothing is going right for me”, then this thought can lead to other thoughts such as “I always mess things up” and “I’m not good at anything”. Try to step back and remember the proverb, “Shut thedoor from which the wind comes and relax”. Ask yourself, “Is everything really going wrong, or is my present mood or my mind making it seem that way?”<br><br><b>2. Challenge your negative thoughts:</b><br>The next time you find yourself thinking thoughts like , “Nothing is going right for me” stop yourself and ask, “Is that really true?” Consider the evidence for this belief, and the evidence that it is not true.<br><br><b>3. Reframe negative thoughts with healthier thoughts: </b><br>After considering the evidence for the belief “Nothing is going right for me” try reframing it with something more optimistic: “Some things are not going right for me, but it doesn’t mean that nothing is going right.”<br>You may find that you’re being harsher on yourself than you are on someone else. You can try this with a more challenging belief, such as “I always mess things up” or “I’m not good at anything”. You can consider the evidence for and against such thoughts, and consider an alternative belief: “I’ve had failures, but that doesn’t mean I’m a failure”.<br><br><b>Remember:</b><br>Your thoughts can help to shape your feelings, but you can shape your thoughts.Learning to challenge and change negative thought patterns is not easy, but it is a powerful way to improve your mood and behaviour. The next time you feel stuck in a vicious cycle of negativity, take a moment to stop, reflect, and reframe your thoughts.<br>"
        )
    }
    fun changeColorOfTaskImage(status: Int?, root: ConstraintLayout, image: ImageView){
        when(status){
            1 -> {
                image.setImageResource( R.drawable.ic_check_blue)
                root.setBackgroundResource(R.drawable.circle_blue_dark)
            }
            2 -> {
                val params = root.layoutParams
                params.width = 70
                params.height = 70
                root.layoutParams = params
                root.setBackgroundResource(R.drawable.circle_orange_with_border)
            }
        }
    }


    fun hideSpiritualIcon(constraintTask: ConstraintLayout, line: View) {
        if( !viewModel.sharedPreferences.getBoolean(RELIGION)){
            constraintTask.visibility = View.GONE
            line.visibility = View.GONE
        }
    }
    fun witchFragment(fragment: String) {
        _fragment = fragment
    }
    fun getNextTask(index: Int, numberTask: Int):Int {
         player?.pause()
      return  if (viewModel.listOfTasks != null) {
            val currentTaskIndex = (index + 1) % viewModel.listOfTasks.size
            getTaskFromList(currentTaskIndex,numberTask)
          currentTaskIndex
        }else 0
    }
    fun updateCompletionRate() {
        viewModel.status.remaining = viewModel.status.remaining?.minus(1)
        if (viewModel.sharedPreferences.getBoolean(RELIGION)) {
            viewModel.status.completionRate = viewModel.status.completionRate?.plus(30)
        } else {
            viewModel.status.completionRate = viewModel.status.completionRate?.plus(50)
        }
        viewModel.updateCurrentTaskLocally()

    }

    private fun checkType(type: Int?) {
        when (type) {
            1 -> {
                displayImage(type)
            }

            2 -> {
                hideViews(type)
                playVideoAudio(Uri.parse(task.link))
            }

            3 -> {
                hideViews(type)
                playVideoAudio(Uri.parse(task.link))
            }

            4 -> {
                hideViews(type)
                showText(type)
            }
        }
    }
    private fun playVideoAudio(uri: Uri) {
        player = ExoPlayer.Builder(requireContext()).build().also { exoPlayer ->
            binding.exoPlayer.player = exoPlayer
            val mediaItem = MediaItem.fromUri(uri)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            dismissProgressDialog()
//            exoPlayer.playWhenReady = true
        }

    }

    private fun showText(int: Int) {
        hideViews(int)
    }

    private fun displayImage(int: Int) {
        Glide.with(this).load(task.image).into(binding.imageView)
        hideViews(int)
        dismissProgressDialog()
    }

    private fun hideViews(int: Int) {

        when (int) {
            //image
            1 -> {
                binding.exoPlayer.visibility = View.GONE
                binding.imageView.visibility = View.VISIBLE

            }
            //video
            2 -> {
                binding.imageView.visibility = View.GONE
                binding.exoPlayer.visibility = View.VISIBLE
            }
            //audio
            3 -> {
                binding.imageView.visibility = View.GONE
                binding.exoPlayer.visibility = View.VISIBLE
            }

        }
    }
     fun showDescriptionDialog(image:Int,title:String,text:String) {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dialogBinding = StaionDescriptionDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)

        val window = dialog.window
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val layoutParams = attributes
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.8).toInt()
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = layoutParams
        }

        dialogBinding.image.setImageResource(image)
        dialogBinding.title.text = title
        dialogBinding.text.text= text

        dialogBinding.icClose.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.button.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }


}