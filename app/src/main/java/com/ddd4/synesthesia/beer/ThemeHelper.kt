package com.ddd4.synesthesia.beer

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {
    const val KEY_SELECTED_THEME_MODE = "selected_theme_mode"

    fun applyTheme(theme: ThemeMode) {
        when (theme) {
            ThemeMode.WHITE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            ThemeMode.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            ThemeMode.SYSTEM -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }

    fun getThemeMode(selectedPosition: Int): ThemeMode {
        return try {
            ThemeMode.values()[selectedPosition]
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    }

    fun getThemeMode(selectedTheme: String): ThemeMode {
        return try {
            ThemeMode.valueOf(selectedTheme)
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    }
}

/**
 * @see R.array.theme_list 순서로 정의 필요
 */
enum class ThemeMode {
    SYSTEM,
    DARK,
    WHITE
}