package com.backend.todo_tasker

import android.Manifest
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.backend.todo_tasker.database.DatabaseBackupRestore
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File


@RunWith(AndroidJUnit4::class)
class DatabaseBackupRestoreTest {

    @Test
    fun testBackup() {
        GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val backup = DatabaseBackupRestore(appContext, null)
        backup.backup()
        val f= appContext.getExternalFilesDir("/todoBackup/")
        val file = File(f,"/TodoDBBackup")
        if(file!=null)
            assert(file.exists())
        else
            assert(false)
    }
}