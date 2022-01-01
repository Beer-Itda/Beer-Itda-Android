package com.hjiee.core.manager

import android.content.Context
import android.content.SharedPreferences
import com.hjiee.core.ext.orZero
import com.hjiee.core.observer.UserInfoChangeObserver.Companion.CHANGED_INFORMATION
import com.hjiee.core.provider.SharedPreferenceProvider.Companion.SHARED_PRIVATE_KEY

class UserInfoManager private constructor() {


    companion object {

        private var instance: UserInfoManager? = null
        private var preference: SharedPreferences? = null

        fun init(context: Context) {
            initPreference(context)
        }

        private fun initPreference(context: Context) {
            preference = context.applicationContext.getSharedPreferences(
                SHARED_PRIVATE_KEY,
                Context.MODE_PRIVATE
            ).apply {
                edit()?.run {
                    remove(CHANGED_INFORMATION).apply()
                }
            }
        }

        fun getInstance(): UserInfoManager {
            return instance ?: UserInfoManager().also {
                instance = it
            }
        }

        /**
         * 현재 정보 변경
         */
        fun getStatus(): Long {
            val status = preference?.getLong(CHANGED_INFORMATION, 0L).orZero()
            return status
        }

        /**
         * 유저 정보가 변경되었을때 값을 증가 시킨다.
         */
        fun changed() {
            var status = preference?.getLong(CHANGED_INFORMATION, 0L).orZero()
            preference?.edit()?.run {
                putLong(CHANGED_INFORMATION, ++status).apply()
            }
        }

    }
}