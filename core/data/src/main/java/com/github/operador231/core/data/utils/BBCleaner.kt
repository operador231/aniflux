package com.github.operador231.core.data.utils

public object BBCleaner {
    private val BB_TAG_REGEX = Regex("\\[/?[a-zA-Z0-9]+.*?]")

    public fun clean(text: String?): String {
        if (text.isNullOrBlank()) return ""
        return BB_TAG_REGEX.replace(text, "")
    }
}