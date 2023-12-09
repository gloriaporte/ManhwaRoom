package br.edu.ifsp.aluno.gloriaporte.manhwaroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.R
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.data.Manhwa
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.databinding.FragmentCadastroBinding
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.viewmodel.ManhwaViewModel
import com.google.android.material.snackbar.Snackbar

class CadastroFragment : Fragment() {
    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ManhwaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ManhwaViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuinflater: MenuInflater) {
                //Adicionar itens aqui
                menuinflater.inflate(R.menu.cadastro_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                //Seleção de menu
                return when (menuItem.itemId) {
                    R.id.action_salvarManhwa -> {
                        val titulo = binding.commonLayout.editTextTitulo.text.toString()
                        val status = binding.commonLayout.editTextStatus.text.toString()
                        val genero = binding.commonLayout.editTextGenero.text.toString()

                        val manhwa = Manhwa(0, titulo, status, genero)
                        viewModel.insert(manhwa)
                        Snackbar.make(binding.root, "Manhwa cadastrado", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}