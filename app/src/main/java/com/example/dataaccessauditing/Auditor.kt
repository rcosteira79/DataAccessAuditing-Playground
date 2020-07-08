package com.example.dataaccessauditing

import android.app.AppOpsManager
import android.app.AsyncNotedAppOp
import android.app.SyncNotedAppOp
import android.content.Context
import android.util.Log
import java.lang.Exception
import java.util.concurrent.Executor

object Auditor {

    private const val LOG_TAG = "Auditor"

    fun audit(context: Context, executor: Executor) {
        val appOpsCallback = object : AppOpsManager.OnOpNotedCallback() {

            override fun onNoted(syncNotedAppOp: SyncNotedAppOp) {
                Log.d(LOG_TAG, "onNoted called")

                logPrivateDataAccess(
                    syncNotedAppOp.op,
                    syncNotedAppOp.attributionTag,
                    createStackTrace()
                )
            }

            override fun onSelfNoted(syncNotedAppOp: SyncNotedAppOp) {
                Log.d(LOG_TAG, "onSelfNoted called")

                logPrivateDataAccess(
                    syncNotedAppOp.op,
                    syncNotedAppOp.attributionTag,
                    createStackTrace()
                )
            }

            override fun onAsyncNoted(asyncNotedAppOp: AsyncNotedAppOp) {
                Log.d(LOG_TAG, "onAsyncNoted called")

                logPrivateDataAccess(
                    asyncNotedAppOp.op,
                    asyncNotedAppOp.attributionTag,
                    "Message: ${asyncNotedAppOp.message}"
                )
            }

            private fun logPrivateDataAccess(
                opCode: String,
                attributionTag: String?,
                trace: String
            ) {
                Log.i(
                    LOG_TAG,
                    "Private data accessed.\n" +
                            "Operation: $opCode\n" +
                            "Attribution Tag: $attributionTag\n" +
                            trace
                )
            }

            private fun createStackTrace(): String {
                return Throwable().stackTrace.joinToString(
                    separator ="\n",
                    prefix = "Stack trace:\n",
                    postfix = "\n"
                ) { "\t$it" }
            }
        }

        val appOpsManager = context.getSystemService(AppOpsManager::class.java) as AppOpsManager
        appOpsManager.setOnOpNotedCallback(executor, appOpsCallback)
    }

    fun unregister(context:Context) {
        val appOpsManager = context.getSystemService(AppOpsManager::class.java) as AppOpsManager
        appOpsManager.setOnOpNotedCallback(null, null)
    }
}
