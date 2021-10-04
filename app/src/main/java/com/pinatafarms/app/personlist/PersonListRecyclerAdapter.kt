package com.pinatafarms.app.personlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pinatafarms.app.databinding.ItemPersonListBinding
import com.pinatafarms.domain.AppConstants
import com.pinatafarms.domain.entities.Person

typealias PersonListClickListener = (person: Person) -> Unit

class PersonListRecyclerAdapter(
    private val onClickListener: PersonListClickListener
) : RecyclerView.Adapter<PersonListRecyclerAdapter.PersonListRecyclerViewHolder>() {

    //Completely not necessary as per current acceptance criteria, but once remote data fetching is introduced to
    // the app - this asyncListDiffer will come in handy.
    private val asyncListDiffer = AsyncListDiffer(this, PersonListDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonListRecyclerViewHolder {
        return PersonListRecyclerViewHolder(
            onClickListener,
            ItemPersonListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PersonListRecyclerViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    fun submit(personList: List<Person>) {
        asyncListDiffer.submitList(personList)
    }

    class PersonListRecyclerViewHolder(
        onClickListener: PersonListClickListener,
        private val binding: ItemPersonListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { _ ->
                binding.personData?.let { onClickListener(it) }
            }
        }

        fun bind(person: Person) {
            binding.personData = person
            binding.personThumbnail.load(person.relativeImageFilePath)
        }
    }
}

private class PersonListDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.imagePath == newItem.imagePath && oldItem.fullName == newItem.fullName
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.faceBounds?.equals(newItem.faceBounds) == true
    }

}