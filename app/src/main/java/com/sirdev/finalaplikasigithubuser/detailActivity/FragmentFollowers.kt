package com.sirdev.finalaplikasigithubuser.detailActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sirdev.finalaplikasigithubuser.R
import com.sirdev.finalaplikasigithubuser.databinding.FragFollowBinding
import com.sirdev.finalaplikasigithubuser.mainActivity.UserAdapter

class FragmentFollowers : Fragment(R.layout.frag_follow) {

    private var _binding: FragFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments
        username = arg?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowUsers.setHasFixedSize(true)
            rvFollowUsers.layoutManager = LinearLayoutManager(activity)
            rvFollowUsers.adapter = adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}