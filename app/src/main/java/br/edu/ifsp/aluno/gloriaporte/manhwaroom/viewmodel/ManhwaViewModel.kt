package br.edu.ifsp.aluno.gloriaporte.manhwaroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.data.Manhwa
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.data.ManhwaDatabase
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.repository.ManhwaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManhwaViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ManhwaRepository
    var allManhwas: LiveData<List<Manhwa>>
    lateinit var manhwa: LiveData<Manhwa>

    init {
        val dao = ManhwaDatabase.getDatabase(application).manhwaDAO()
        repository = ManhwaRepository(dao)
        allManhwas = repository.getAllManhwas()
    }

    fun insert(manhwa: Manhwa) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(manhwa)
    }

    fun update(manhwa: Manhwa) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(manhwa)
    }

    fun delete(manhwa: Manhwa) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(manhwa)
    }

    fun getManhwaById(id: Int) {
        viewModelScope.launch {
            manhwa = repository.getManhwaById(id)
        }
    }
}