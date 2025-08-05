package mnshat.dev.myproject.users.patient.tools.supplications.prisentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import mnshat.dev.myproject.R
import mnshat.dev.myproject.adapters.UserSupplicationAdapter
import mnshat.dev.myproject.base.BaseFragment
import mnshat.dev.myproject.databinding.FragmentUserSupplicationsBinding
import mnshat.dev.myproject.interfaces.ItemSupplicationClicked
import mnshat.dev.myproject.model.Supplication
import mnshat.dev.myproject.util.log
import kotlin.getValue

@AndroidEntryPoint

class UserSupplicationsFragment : BaseFragment(),
    ItemSupplicationClicked {

    private val viewModel: SupplicationViewModel by activityViewModels()

    private lateinit var binding: FragmentUserSupplicationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSupplicationsBinding.inflate(inflater, container, false)
        setupClickListener()
        observeViewModel()
        return binding.root
    }


    private fun setupClickListener() {
        binding.fab.setOnClickListener {
            AddSupplicationDialog().show(parentFragmentManager, "AddSupplicationDialog")
        }
        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }
    }



    private fun setupUserSupplicationRecyclerView(
        userSupplication: List<Supplication>
    ) {
        val adapterUserSupplication = UserSupplicationAdapter(userSupplication,this)
        binding.userSupplicationRecyclerView.adapter = adapterUserSupplication
    }


    private fun observeViewModel() {
        viewModel.userSupplications.observe(viewLifecycleOwner) {
            setupUserSupplicationRecyclerView(it)
        }
    }


    override fun onItemClicked(view: View,supplication: Supplication) {
        showPopupMenu(view,supplication)
    }


    private fun showPopupMenu(view: View,supplication:Supplication) {
        val popupMenu = PopupMenu(requireActivity(), view)
        popupMenu.inflate(R.menu.item_supplicaion_menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            handleMenuItemClick(menuItem,supplication)
        }
        popupMenu.show()
    }

    private fun handleMenuItemClick(menuItem: MenuItem ,supplication:Supplication ): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_modification -> {
                true
            }
            R.id.menu_sharing -> {
                copyTextToClipboard( supplication.name!!)
                true
            }
            R.id.menu_delete -> {
                true
            }
            else -> false
        }

}
}