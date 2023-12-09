package br.edu.ifsp.aluno.gloriaporte.manhwaroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.R
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.data.Manhwa
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.databinding.FragmentDetalheBinding
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.viewmodel.ManhwaViewModel
import com.google.android.material.snackbar.Snackbar


class DetalheFragment : Fragment() {
    private var _binding: FragmentDetalheBinding? = null
    private val binding get() = _binding!!

    lateinit var manhwa: Manhwa

    lateinit var tituloEditText: EditText
    lateinit var statusEditText: EditText
    lateinit var generoEditText: EditText

    lateinit var viewModel: ManhwaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ManhwaViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetalheBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tituloEditText = binding.commonLayout.editTextTitulo
        generoEditText = binding.commonLayout.editTextGenero
        statusEditText = binding.commonLayout.editTextStatus

        val idManhwa = requireArguments().getInt("idManhwa")

        viewModel.getManhwaById(idManhwa)

        viewModel.manhwa.observe(viewLifecycleOwner) { result ->
            result?.let {
                manhwa = result
                tituloEditText.setText(manhwa.titulo)
                generoEditText.setText(manhwa.genero)
                statusEditText.setText(manhwa.status)
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.detalhe_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_alterarManhwa -> {

                        manhwa.titulo = tituloEditText.text.toString()
                        manhwa.genero = generoEditText.text.toString()
                        manhwa.status = statusEditText.text.toString()

                        viewModel.update(manhwa)

                        Snackbar.make(binding.root, "Manhwa alterado", Snackbar.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                        true
                    }
                    R.id.action_excluirManhwa ->{
                        viewModel.delete(manhwa)

                        Snackbar.make(binding.root, "Manhwa apagado", Snackbar.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}