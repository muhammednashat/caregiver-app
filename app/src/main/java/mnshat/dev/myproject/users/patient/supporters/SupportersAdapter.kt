package mnshat.dev.myproject.users.patient.supporters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mnshat.dev.myproject.R
import mnshat.dev.myproject.databinding.ItemViewSupporterBinding
import mnshat.dev.myproject.model.RegistrationData

class SupportersAdapter
    ( val context:Context,
    private val clickListener: SupporterListener
   ) :
    ListAdapter<RegistrationData, SupportersAdapter.SupportersViewHolder>(ItemDiffUtil()) {

    class SupportersViewHolder private constructor(val binding: ItemViewSupporterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            supporter: RegistrationData, clickListener: SupporterListener
            ) {

            binding.supporter = supporter
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SupportersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewSupporterBinding.inflate(layoutInflater, parent, false)
                return SupportersViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupportersViewHolder {
        return SupportersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SupportersViewHolder, position: Int) {
        
        holder.bind(getItem(position), clickListener)
        val supporter = getItem(position)
        holder.binding.nameSupporter.text = supporter.name
        
        if (supporter.status == 1){
            holder.binding.status.background =    AppCompatResources. getDrawable(context,R.drawable.corner_light_blue)

            holder.binding.status.text = context.getString(R.string.active)
        }else{
            holder.binding.status.background =    AppCompatResources. getDrawable(context,R.drawable.corner_red)
            holder.binding.status.text = context.getString(R.string.not_active)
        }
       
 
    }
}


class ItemDiffUtil() : DiffUtil.ItemCallback<RegistrationData>() {
    override fun areItemsTheSame(old: RegistrationData, new: RegistrationData): Boolean {
        return old.id == new.id
    }

    override fun areContentsTheSame(old: RegistrationData, new: RegistrationData): Boolean {
        return old.equals(new)
    }

}

class SupporterListener(private val clickListener: (supporter: RegistrationData) -> Unit) {
    fun onClick(supporter: RegistrationData) = clickListener(supporter)
}