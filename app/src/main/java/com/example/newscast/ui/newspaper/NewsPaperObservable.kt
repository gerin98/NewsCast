package com.example.newscast.ui.newspaper

import android.text.SpannableString
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlin.properties.Delegates

class NewsPaperObservable(
    _title: String = "",
    _body: String = "",
    _url: String = "",
    _topic: String = "",
    _author: SpannableString = SpannableString("")
) : BaseObservable() {

    @get:Bindable
    var title: String by Delegates.observable(_title) { _, _, _ ->
        notifyPropertyChanged(BR.title)
    }

    @get:Bindable
    var body: String by Delegates.observable(_body) { _, _, _ ->
        notifyPropertyChanged(BR.body)
    }

    @get:Bindable
    var url: String by Delegates.observable(_url) { _, _, _ ->
        notifyPropertyChanged(BR.url)
    }

    @get:Bindable
    var topic: String by Delegates.observable(_topic) { _, _, _ ->
        notifyPropertyChanged(BR.topic)
    }

    @get:Bindable
    var author: SpannableString by Delegates.observable(_author) { _, _, _ ->
        notifyPropertyChanged(BR.author)
    }

}