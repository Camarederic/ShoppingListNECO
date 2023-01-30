package com.example.shoppinglistneco.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistneco.R
import com.example.shoppinglistneco.database.MainViewModel
import com.example.shoppinglistneco.database.ShopListItemAdapter
import com.example.shoppinglistneco.databinding.ActivityShopListBinding
import com.example.shoppinglistneco.entities.ShopListItem
import com.example.shoppinglistneco.entities.ShopListNameItem

class ShopListActivity : AppCompatActivity(), ShopListItemAdapter.Listener {

    private lateinit var binding: ActivityShopListBinding
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem
    private var edItem: EditText? = null
    private var adapter:ShopListItemAdapter? = null

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        initRecyclerView()

        listItemObserver()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item)!!
        val newItem = menu.findItem(R.id.new_item)
        edItem = newItem.actionView.findViewById(R.id.edNewShopItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_item){
            addNewShopItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNewShopItem() {
        if (edItem?.text.toString().isEmpty()) return
        val item = ShopListItem(
            null, edItem?.text.toString(),
            null,
            0,
            shopListNameItem?.id!!,
            0
        )
        edItem?.setText("")
        mainViewModel.insertShopItem(item)
    }

    private fun listItemObserver(){
        mainViewModel.getAllItemsFromList(shopListNameItem?.id!!).observe(this) {
            adapter?.submitList(it)
            binding.tvEmpty.visibility = if (it.isEmpty()){
                View.VISIBLE
            }else{
                View.GONE
            }
        }
    }

    private fun initRecyclerView(){
        adapter = ShopListItemAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                saveItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                saveItem.isVisible = false
                invalidateOptionsMenu()
                return true
            }

        }
    }

    private fun init() {
        shopListNameItem = intent.getSerializableExtra(SHOP_LIST_NAME) as ShopListNameItem

    }

    companion object {
        const val SHOP_LIST_NAME = "shop_list_name"
    }

    override fun deleteItem(id: Int) {

    }

    override fun updateItem(shopListNameItem: ShopListNameItem) {

    }

    override fun onClickItem(shopListNameItem: ShopListNameItem) {

    }
}