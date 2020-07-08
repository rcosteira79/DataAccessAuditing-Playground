package com.example.dataaccessauditing

import android.app.Application

class DataAccessAuditingApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Auditor.audit(this, mainExecutor)
    }
}
