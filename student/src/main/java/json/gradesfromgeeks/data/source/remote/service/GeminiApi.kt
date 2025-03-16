package json.gradesfromgeeks.data.source.remote.service

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content


class GeminiApi(private val model: GenerativeModel) {

   suspend fun generateUniversityContent(question: String, university: String): GenerateContentResponse {
        val response = model.generateContent(
            content {
                text("أنت مساعد أكاديمي يقدم معلومات مفصلة حول $university. بناءً على السؤال المطروح، قم بإنشاء إجابة موجزة ولكن مفيدة حول أي معلومة تتعلق بهذه الجامعة، سواء كانت عن الموارد، الموقع، التخصصات، الحياة الجامعية، أو أي معلومات أكاديمية أخرى.")

                text("إذا كان السؤال يحتوي على كلمات شكر مثل 'شكرًا' أو 'أشكرك' أو ما يشابهها، فقم بالرد بجملة مناسبة تعبر عن الامتنان، مثل: 'على الرحب والسعة! يسعدني مساعدتك دائمًا 😊'")

                text("إذا كان السؤال يحتوي على كلمات ترحيب مثل 'مرحبًا' أو 'أهلاً' أو 'السلام عليكم' أو ما يشابهها، فقم بالرد بجملة ترحيبية مثل: 'أهلًا وسهلًا! كيف يمكنني مساعدتك في معرفة معلومات عن $university؟ يمكنك طرح أي سؤال يخص الجامعة، سواء كان عن التخصصات، القبول، المرافق، أو أي موضوع آخر.'")

                text("إذا كان السؤال لا يتعلق بـ $university على الإطلاق، فقم بالرد بهذه الجملة: 'يرجى سؤالي عن شيء متعلق بـ $university فقط حتى أتمكن من الإجابة عليك.'")

                text("الجامعة: $university")
                text("السؤال: $question")
                text("الإجابة: ")
            }
        )
        return response
    }

}
