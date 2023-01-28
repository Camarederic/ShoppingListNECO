package com.example.shoppinglistneco.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistneco.activities.MainApp
import com.example.shoppinglistneco.database.MainViewModel
import com.example.shoppinglistneco.database.ShopListNameAdapter
import com.example.shoppinglistneco.databinding.FragmentShopListNamesBinding
import com.example.shoppinglistneco.dialogs.DeleteDialog
import com.example.shoppinglistneco.dialogs.NewListDialog
import com.example.shoppinglistneco.entities.NoteItem
import com.example.shoppinglistneco.entities.ShoppingListName
import com.example.shoppinglistneco.utils.TimeManager


class ShopListNamesFragment : BaseFragment(), ShopListNameAdapter.Listener {

    private lateinit var binding: FragmentShopListNamesBinding
    private lateinit var adapter: ShopListNameAdapter


    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observer()
    }

    private fun initRecyclerView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = ShopListNameAdapter(this@ShopListNamesFragment)
        recyclerView.adapter = adapter
    }

    private fun observer() {
        mainViewModel.allShopListNames.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener {

            override fun onClick(name: String) {
                val shopListName = ShoppingListName(
                    null, name, TimeManager.getCurrentTime(),
                    0, 0, ""
                )
                mainViewModel.insertShopListName(shopListName)

                Log.d("MyLog", "Name: $name")
            }

        })
    }

    companion object {

        fun newInstance() = ShopListNamesFragment()
    }

    override fun deleteItem(id: Int) {
        DeleteDialog.showDialog(context as AppCompatActivity, object : DeleteDialog.Listener {

            override fun onClick() {
                mainViewModel.deleteShopListName(id)
            }

        })
    }

    override fun onclickItem(note: NoteItem) {

    }

}