package com.backend.todo_tasker.database

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.io.*


class DatabaseBackupRestore(Context: Context, activity: Activity?) {

    private var Activity = activity

    private var appContext = Context
    fun backup() {
        if(Activity!= null){
            verifyStoragePermissions(Activity)
        }
        val db = DatabaseClass(appContext)
        val datab = db.createDb()
        datab.close()
        val dbFile = appContext.getDatabasePath("todo-database").absolutePath

        val sdir = appContext.getExternalFilesDir("/todoBackup/")
        if(sdir != null){
            val sfpath =
                    sdir.path + File.separator.toString() + "TodoDBBackup"
                            .toString()
            if (!sdir.exists()) {
                sdir.mkdirs()
            }
            val savefile = File(sfpath)
            val created= savefile.createNewFile()
            val buffersize = 8 * 1024
            val buffer = ByteArray(buffersize)
            var bytes_read = buffersize
            val savedb: OutputStream = FileOutputStream(sfpath)
            val indb: InputStream = FileInputStream(dbFile)
            while (indb.read(buffer, 0, buffersize).also({ bytes_read = it }) > 0) {
                savedb.write(buffer, 0, bytes_read)
            }
            savedb.flush()
            indb.close()
            savedb.close()

        }
    }


    fun verifyStoragePermissions(activity: Activity?) {
        val REQUEST_EXTERNAL_STORAGE = 1
        val PERMISSIONS_STORAGE =
                arrayOf<String>( //Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
        val permission = ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}