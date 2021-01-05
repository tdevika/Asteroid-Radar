package com.udacity.asteroidradar.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.AsteroidApplication
import com.udacity.asteroidradar.MainActivity
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.util.AsteroidViewModelFactory
import javax.inject.Inject


class MainFragment : Fragment(), ItemClickListener {
    @Inject
    lateinit var viewModelFactory: AsteroidViewModelFactory

    private lateinit var viewModel: MainViewModel


    lateinit var asteroidAdapter: AsteroidAdapter

    lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as AsteroidApplication).daggerAppComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        setRecycler()
        setObserver()
        setHasOptionsMenu(true)
        return binding.root
    }


    private fun setRecycler() {
        asteroidAdapter = AsteroidAdapter(this)
        binding.asteroidRecycler.apply {
            adapter = asteroidAdapter
        }
    }

    private fun setObserver() {
        viewModel.asteroids.observe(viewLifecycleOwner, Observer {
            binding.statusLoadingWheel.visibility = View.VISIBLE
            it.let {
                asteroidAdapter.submitList(it)
                binding.statusLoadingWheel.visibility = View.GONE

            }
        })

        viewModel.pictureUrl.observe(viewLifecycleOwner, Observer {
            it.let {
                when (it.media_type) {
                    "video" -> {
                        binding.activityMainImageOfTheDay.setImageResource(R.drawable.placeholder_picture_of_day)
                    }
                    else -> {
                        Picasso.with(context).load(it.url).into(binding.activityMainImageOfTheDay)
                    }
                }
                binding.textView.text = it.title
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.groupId == R.id.multi_language)
            if (!item.isChecked) {
                item.isChecked = true
                when (item.itemId) {
                    R.id.english -> setLocate("en")
                    R.id.kannada -> setLocate("kn")
                    else -> setLocate("de")
                }
            }
        when (item.itemId) {
            R.id.show_all_menu -> viewModel.getAllAsteroids()
            R.id.show_rent_menu -> viewModel.getTodayAsteroids()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setLocate(lang: String) {
        val sharedPref = activity?.getSharedPreferences(
            "Settings", Context.MODE_PRIVATE
        )
        sharedPref?.edit()?.putString("Language", lang)?.apply()
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    override fun onClick(asteroid: Asteroid) {
        findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
    }
}

interface ItemClickListener {
    fun onClick(asteroid: Asteroid)
}
