package json.gradesfromgeeks.data.source.remote.service

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content


class GeminiApi(private val model: GenerativeModel) {

    suspend fun summarizeText(text: String): String {
        val response = model.generateContent(
            content {
                text(
                    """
                    please  summarize the  this video Transcript $text and make it like point sentences              
                """.trimIndent()
                )
                text(":")
            }
        )
        return response.text?:""
    }

    suspend fun generateUniversityContent(question: String, university: String): GenerateContentResponse {
        val response = model.generateContent(
            content {
                text("""
                    [الهوية الأكاديمية]
                    مساعد معرفي متخصص في:
                    1. المصادر الرسمية لجامعة $university:
                       - الكتب الدراسية المعتمدة
                       - المناهج الأكاديمية
                       - أوراق البحث الجامعية
                    2. المراجع العلمية الخارجية الموثوقة:
                       - الكتب الأكاديمية ذات الصلة
                       - الدوريات العلمية المحكمة
                       - المصادر المعتمدة في المجال

                    [خوارزمية الإجابة]
                    1. البحث الأولي في مصادر الجامعة الداخلية
                    2. إذا لم توجد إجابة واضحة:
                       - الاستعانة بمرجع خارجي مع ذكر المصدر بدقة
                       - عدم الإشارة لعدم وجود المعلومة في مصادر الجامعة
                    3. صياغة الإجابة ككتلة واحدة متجانسة

                    [نماذج الرد]
                    ● عند وجود إجابة داخلية:
                    "وفقًا لـ(اسم المقرر/الكتاب) في جامعة $university (الصفحة X)..."

                    ● عند استخدام مصدر خارجي:
                    "في إطار الإجابة العلمية، نذكر أن (المعلومة) حسب (اسم الكتاب الخارجي) للمؤلف (الاسم) (السنة)..."

                    ● مثال تطبيقي:
                    السؤال: "ما تطبيقات نظرية فيثاغورس في الهندسة الحديثة؟"
                    الإجابة: "في كتاب 'الأسس الهندسية' الصادر عن كلية الهندسة (2022) صفحة 78... كما أشار الدكتور سمير عبدالله في كتاب 'تطور النظريات الرياضية' (2019) إلى..."

                    [إرشادات الاستشهاد]
                    - ذكر تفاصيل المصدر الخارجي بشكل أكاديمي
                    - دمج المعلومات بسلاسة دون تمييز واضح
                    - التركيز على المعلومة العلمية نفسها
                    - تجنب أي إشارة لعدم اكتمال المصادر الداخلية

                    [الحدود المسموحة]
                    - مسموح باستخدام 30% من المصادر الخارجية كحد أقصى
                    - يجب أن تكون المراجع الخارجية من نفس التخصص
                    - منع الاستشهاد بمواقع غير أكاديمية

                    السؤال المطلوب: "$question"
                    الرجاء بناء الإجابة وفق هذه المعايير:
                """.trimIndent())
            }
        )
        return response
    }

}
