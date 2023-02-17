package com.example.mvvmwithdaggerhilt.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmwithdaggerhilt.databinding.FragmentHomeBinding
import com.example.mvvmwithdaggerhilt.ui.adapter.LoadingStateAdapter
import com.example.mvvmwithdaggerhilt.ui.adapter.UserListAdapter
import com.example.mvvmwithdaggerhilt.ui.adapter.UserPagingListAdapter
import com.example.mvvmwithdaggerhilt.ui.model.Data
import com.example.mvvmwithdaggerhilt.ui.viewmodel.UserViewModel
import com.example.mvvmwithdaggerhilt.utils.NetworkResult
import com.example.mvvmwithdaggerhilt.utils.Utils.setLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val TAG = this.javaClass.name
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var userList:List<Data> = ArrayList()
    private var userListAdapter:UserListAdapter? = null
    private val userViewModel : UserViewModel by viewModels()

    @Inject
    lateinit var userPagingListAdapter: UserPagingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMain.layoutManager = LinearLayoutManager(requireContext())

        /*bindViewObserver()
        lifecycleScope.launch{
            userViewModel.getUserList()
        }*/
        initRecyclerView()
        lifecycleScope.launchWhenStarted{
            userViewModel.getUserListWithPaging().collectLatest {
                binding.apply {
                    pbProgress.isVisible = false
                }
                userPagingListAdapter.submitData(it)
            }
        }
    }

    private fun bindViewObserver(){
        lifecycleScope.launchWhenStarted {
            userViewModel.getUserList.collect{
                binding.pbProgress.isVisible = false
                when(it){
                    is NetworkResult.Error -> {
                        setLog(TAG, "Ërror=${it.message}")
                    }
                    is NetworkResult.Loading -> {binding.pbProgress.isVisible = true}
                    is NetworkResult.Success -> {
                        userList = it.data?.data!!
                        userListAdapter = UserListAdapter(requireContext(), userList)
                        binding.rvMain.adapter = userListAdapter
                    }
                }
            }
        }
    }

    private fun initRecyclerView(){
        binding.apply {
            rvMain.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = userPagingListAdapter.withLoadStateHeaderAndFooter(
                    header = LoadingStateAdapter{userPagingListAdapter.retry()},
                    footer = LoadingStateAdapter{userPagingListAdapter.retry()},
                )
            }
        }
    }
}