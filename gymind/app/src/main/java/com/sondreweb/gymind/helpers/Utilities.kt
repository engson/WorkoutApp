package com.sondreweb.gymind.helpers

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream

class Utilities {
    companion object {
        fun getAssetsData(context: Context, url: String): String =
            context
                .assets
                .open(url)
                .bufferedReader()
                .use(BufferedReader::readText)
    }

}