package com.example.finalproject
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.finalproject.databinding.ActivityProductPageBinding
import com.example.finalproject.retrofit.RetrofitHelper
import com.example.finalproject.service.ServerAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import com.example.finalproject.interfaces.Product as PrInterface
import com.example.finalproject.interfaces.Category
import kotlinx.coroutines.async
class ProductsPage : AppCompatActivity() {
    lateinit var binding: ActivityProductPageBinding
    val client = OkHttpClient.Builder().build()
    val retrofit = RetrofitHelper(client)
    val rf = retrofit.getInstance()
    val itemAPI = rf.create(ServerAPI::class.java)
    private lateinit var listViewAdapter : ExpandableListViewAdapter
    private lateinit var chapterList : List<String>
    private lateinit var topicList : HashMap<String, List<PrInterface>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPageBinding.inflate(layoutInflater)
        val products_page = binding.root
        setContentView(products_page)

        val userId = intent.getIntExtra("userId", 1)
        val userName = intent.getStringExtra("userName")?:""
        CoroutineScope(Dispatchers.Main).launch {
            val call = itemAPI.getProducts()
            val category = itemAPI.getCategories()

            showList(call, category)


            listViewAdapter = ExpandableListViewAdapter(this@ProductsPage, chapterList, topicList, userId, userName)
            Log.d("tuopics", topicList.toString())
            val eListView = binding.eListView
            eListView.setAdapter(listViewAdapter)
        }
//        CoroutineScope(Dispatchers.Main).launch {
//            pr.await()
//        }
    }
    private fun showList(call: List<PrInterface>, category: List<Category>) {
        Log.d("call:" , call.toString())
        chapterList = ArrayList()
        topicList = HashMap()
        //      (chapterList as ArrayList<String>).add(call[0].name)
        //     (chapterList as ArrayList<String>).add(call[1].name)
        //     (chapterList as ArrayList<String>).add(call[2].name)
        //    (chapterList as ArrayList<String>).add(call[3].name)
        //     (chapterList as ArrayList<String>).add(call[4].name)
        for (item in category.indices){
            (chapterList as ArrayList<String>).add(category[item].name)
            val topic : MutableList<PrInterface> = ArrayList()
            for(prod in call){
                if(prod.category.toInt() == category[item].id){
                    Log.d("", "asdas")
                    topic.add(prod)
                }
            }
            topicList[chapterList[item]] = topic
        }
//        val topic1 : MutableList<PrInterface> = ArrayList()
//        topic1.add(call[0])
//
//        val topic2 : MutableList<PrInterface> = ArrayList()
//        topic2.add(call[1])
//
//        val topic3 : MutableList<PrInterface> = ArrayList()
//        topic3.add(call[2])
//
//        val topic4 : MutableList<PrInterface> = ArrayList()
//        topic4.add(call[3])
//
//        val topic5 : MutableList<PrInterface> = ArrayList()
//        topic5.add(call[4])
//
//        Log.d("topic5", topic5.toString())
//        topicList[chapterList[0]] = topic1
//        topicList[chapterList[1]] = topic2
//        topicList[chapterList[2]] = topic3
//        topicList[chapterList[3]] = topic4
//        topicList[chapterList[4]] = topic5
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_products_page, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle settings backButton click
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}