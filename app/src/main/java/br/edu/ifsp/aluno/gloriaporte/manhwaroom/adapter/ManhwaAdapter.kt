package br.edu.ifsp.aluno.gloriaporte.manhwaroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.data.Manhwa
import br.edu.ifsp.aluno.gloriaporte.manhwaroom.databinding.ManhwaCelulaBinding

class ManhwaAdapter(): RecyclerView.Adapter<ManhwaAdapter.ManhwaViewHolder>(), Filterable {

    var listener: ManhwaListener?=null
    var manhwasLista = ArrayList<Manhwa>()
    var manhwasListaFilterable = ArrayList<Manhwa>()

    private lateinit var binding: ManhwaCelulaBinding

    fun updateList(newList: ArrayList<Manhwa>) {
        manhwasLista = newList
        manhwasListaFilterable = manhwasLista
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ManhwaListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ManhwaViewHolder {
        binding = ManhwaCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManhwaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ManhwaViewHolder, position: Int) {
        holder.tituloVH.text = manhwasListaFilterable[position].titulo
        holder.statusVH.text = manhwasListaFilterable[position].status
        holder.generoVH.text = manhwasListaFilterable[position].genero
    }

    override fun getItemCount(): Int {
        return manhwasListaFilterable.size
    }

    inner class ManhwaViewHolder(view: ManhwaCelulaBinding): RecyclerView.ViewHolder(view.root) {
        val tituloVH = view.titulo
        val statusVH = view.status
        val generoVH = view.genero
        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface ManhwaListener {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if(p0.toString().isEmpty())
                    manhwasListaFilterable = manhwasLista
                else {
                    val resultList = ArrayList<Manhwa>()
                    for (row in manhwasLista)
                        if (row.titulo.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    manhwasListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = manhwasListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                manhwasListaFilterable = p1?.values as ArrayList<Manhwa>
                notifyDataSetChanged()
            }

        }
    }
}