package br.edu.ifsp.aluno.gloriaporte.manhwaroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.R
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.adapter.ManhwaAdapter
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.data.Manhwa
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.databinding.ActivityMainBinding
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.databinding.FragmentListaManhwaBinding
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.viewmodel.ManhwaViewModel

class ListaManhwaFragment : Fragment() {
    private var _binding: FragmentListaManhwaBinding? = null
    private val binding get() = _binding!!
    lateinit var manhwaAdapter: ManhwaAdapter
    lateinit var viewModel: ManhwaViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaManhwaBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listaManhwaFragment_to_cadastroFragment)}

        configureRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
                //Não vai mais ter busca nessa versão
                val actionNewItem = menu.findItem(R.id.action_new)

                actionNewItem.actionView?.setOnClickListener {
                    findNavController().navigate(R.id.action_listaManhwaFragment_to_cadastroFragment)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                findNavController().navigate(R.id.action_listaManhwaFragment_to_cadastroFragment)
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun configureRecyclerView() {
        viewModel = ViewModelProvider(this).get(ManhwaViewModel::class.java)

        viewModel.allManhwas.observe(viewLifecycleOwner) { list ->
            list?.let {
                manhwaAdapter.updateList(list as ArrayList<Manhwa>)
            }
        }

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        manhwaAdapter = ManhwaAdapter()
        recyclerView.adapter = manhwaAdapter

        val listener = object: ManhwaAdapter.ManhwaListener {
            override fun onItemClick(pos: Int) {
                val c = manhwaAdapter.manhwasListaFilterable[pos]

                val bundle = Bundle()
                bundle.putInt("idManhwa", c.id)

                findNavController().navigate(
                    R.id.action_listaManhwaFragment_to_detalheFragment,
                    bundle
                )
            }
        }
        manhwaAdapter.setClickListener(listener)
    }
}