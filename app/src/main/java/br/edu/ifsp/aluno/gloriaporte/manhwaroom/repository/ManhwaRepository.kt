package br.edu.ifsp.aluno.gloriaporte.manhwaroom.repository

import androidx.lifecycle.LiveData
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.data.Manhwa
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.data.ManhwaDAO

class ManhwaRepository(private val manhwaDAO: ManhwaDAO) {

    suspend fun insert(manhwa: Manhwa) {
        manhwaDAO.insert(manhwa)
    }

    suspend fun update(manhwa: Manhwa) {
        manhwaDAO.update(manhwa)
    }

    suspend fun delete(manhwa: Manhwa) {
        manhwaDAO.delete(manhwa)
    }

    fun getAllManhwas(): LiveData<List<Manhwa>> {
        return manhwaDAO.getAllManhwas()
    }

    fun getManhwaById(id: Int): LiveData<Manhwa> {
        return manhwaDAO.getManhwaById(id)
    }
}