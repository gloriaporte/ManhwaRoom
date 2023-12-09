package br.edu.ifsp.aluno.gloriaporte.manhwaroom.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ManhwaDAO {
    @Insert
    suspend fun insert(manhwa: Manhwa)

    @Update
    suspend fun update(manhwa: Manhwa)

    @Delete
    suspend fun delete(manhwa: Manhwa)

    @Query("SELECT * FROM manhwa ORDER BY titulo")
    fun getAllManhwas(): LiveData<List<Manhwa>>

    @Query("SELECT * FROM manhwa WHERE id=:id")
    fun getManhwaById(id: Int): LiveData<Manhwa>
}