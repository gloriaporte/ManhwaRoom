package br.edu.ifsp.aluno.gloriaporte.manhwaroom.data

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Manhwa::class], version = 1)
 abstract class ManhwaDatabase:RoomDatabase() {
     abstract fun manhwaDAO(): ManhwaDAO

     companion object{
         @Volatile
         private var INSTANCE: ManhwaDatabase?= null

         fun getDatabase(context: Context): ManhwaDatabase {
             return INSTANCE?: synchronized(this) {
                 val instance = Room.databaseBuilder(
                     context.applicationContext,
                     ManhwaDatabase::class.java,
                     "manhwaroom.db"
                 ).build()
                 INSTANCE = instance
                 instance
             }
         }
     }
}