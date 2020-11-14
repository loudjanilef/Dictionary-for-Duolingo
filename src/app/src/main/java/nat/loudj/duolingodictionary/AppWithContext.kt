package nat.loudj.duolingodictionary

import android.app.Application
import android.content.Context

class AppWithContext : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}