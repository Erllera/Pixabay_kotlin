package com.geektech.pixabay_kotlin

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geektech.pixabay_kotlin.databinding.FragmentMainBinding
import com.geektech.pixabay_kotlin.model.Hit
import com.geektech.pixabay_kotlin.model.PhotoData
import com.geektech.pixabay_kotlin.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    private lateinit var retrofitService: RetrofitService
    private lateinit var adapter: PhotoAdapter
    private lateinit var binding: FragmentMainBinding
    private var page = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        click()
        retrofitService = RetrofitService()

    }

    private fun click() {
        with(binding) {

            searchBtn.setOnClickListener {
                val keyWord = searchEt.text.toString()
                getPhotoApi(keyWord, 1, 5)
            }
            swipe.setOnRefreshListener {
                val keyWord = searchEt.text.toString()
                page++
                getPhotoApi(keyWord = keyWord,page,10)
                Log.e("ololo", page.toString())
                Handler().postDelayed(Runnable {
                    swipe.isRefreshing = false
                }, 3000)
            }
        }
    }

    private fun FragmentMainBinding.getPhotoApi(keyWord: String, page: Int, per_page: Int) {
        retrofitService.getApi().getPhotos(namePhoto = keyWord, page = page, per_page = per_page)
            .enqueue(object : Callback<PhotoData> {
                override fun onResponse(
                    call: Call<PhotoData>,
                    response: Response<PhotoData>
                ) {
                    if (response.isSuccessful) {

                        adapter = PhotoAdapter()
                        adapter.list = response.body()?.hits as ArrayList<Hit>
                        recycler.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<PhotoData>, t: Throwable) {
                    Log.e("ololo", t.message.toString())
                }

            })
    }
}