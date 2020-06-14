package com.example.newscast.utils.string

import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import org.koin.dsl.module

val stringHelperModule = module {
    factory { StringHelper() }
}

class StringHelper {

    fun underlineText(text: String, underlineStart: Int? = null, underlineEnd: Int? = null): SpannableString {
            val underlinedContent = SpannableString(text)
            val start = 0
            val end = underlinedContent.length

            underlinedContent.setSpan(UnderlineSpan(), underlineStart ?: start, underlineEnd ?: end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return underlinedContent
    }

}