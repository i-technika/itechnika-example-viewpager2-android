package com.itechnika.example.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.itechnika.example.viewpager2.databinding.ActivityMainBinding
import com.itechnika.example.viewpager2.databinding.FragmentOneBinding
import com.itechnika.example.viewpager2.databinding.FragmentThreeBinding
import com.itechnika.example.viewpager2.databinding.FragmentTwoBinding

class MainActivity : AppCompatActivity() {

    private val recyclerViewItems = ArrayList<RecyclerViewItem>()
    private val recyclerViewHolderMap = HashMap<Int, ViewPagerHolder>()

    inner class RecyclerViewItem(var imageResId: Int, var textResId: Int)

    inner class ViewPagerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var ivImage: ImageView = itemView.findViewById(R.id.iv_image)
        private var tvText: TextView = itemView.findViewById(R.id.tv_text)

        fun onBind(item: RecyclerViewItem) {
            ivImage.setImageResource(item.imageResId)
            tvText.setText(item.textResId)
        }
    }

    inner class RecyclerViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cell_image, parent, false)
            return ViewPagerHolder(view)
        }

        override fun getItemCount(): Int = recyclerViewItems.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ViewPagerHolder).let {
                recyclerViewHolderMap[position] = it
                it.onBind(recyclerViewItems[position])
            }
        }
    }

    class Fragment1: Fragment() {
        private lateinit var fragmentBinding: FragmentOneBinding

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            fragmentBinding = FragmentOneBinding.inflate(inflater, container, false)
            return fragmentBinding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val bundle = arguments
            bundle?.getInt("text_res_id")?.let {
                fragmentBinding.tvText.setText(it)
            }
        }
    }

    class Fragment2: Fragment() {
        private lateinit var fragmentBinding: FragmentTwoBinding

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            super.onCreateView(inflater, container, savedInstanceState)
            fragmentBinding = FragmentTwoBinding.inflate(inflater, container, false)
            return fragmentBinding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val bundle = arguments
            bundle?.getInt("image_res_id")?.let {
                fragmentBinding.ivImage.setImageResource(it)
            }
        }
    }

    class Fragment3: Fragment() {
        private lateinit var fragmentBinding: FragmentThreeBinding

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            fragmentBinding = FragmentThreeBinding.inflate(inflater, container, false)
            return fragmentBinding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val bundle = arguments
            bundle?.getInt("text_res_id")?.let {
                fragmentBinding.tvText.setText(it)
            }
            bundle?.getInt("image_res_id")?.let {
                fragmentBinding.ivImage.setImageResource(it)
            }
        }
    }

    inner class FragmentStateViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
             when (position) {
                1 -> {
                    val fragment = Fragment2()
                    val bundle = Bundle()
                    bundle.putInt("image_res_id", R.mipmap.image_2)
                    fragment.arguments = bundle
                    return fragment
                }
                2 -> {
                    val fragment = Fragment3()
                    val bundle = Bundle()
                    bundle.putInt("image_res_id", R.mipmap.image_3)
                    bundle.putInt("text_res_id", R.string.fs_text_3)
                    fragment.arguments = bundle
                    return fragment
                }
                else -> {
                    val fragment = Fragment1()
                    val bundle = Bundle()
                    bundle.putInt("text_res_id", R.string.fs_text_1)
                    fragment.arguments = bundle
                    return fragment
                }
            }
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerViewItems.add(RecyclerViewItem(R.mipmap.image_1, R.string.rv_text_1))
        recyclerViewItems.add(RecyclerViewItem(R.mipmap.image_2, R.string.rv_text_2))
        recyclerViewItems.add(RecyclerViewItem(R.mipmap.image_3, R.string.rv_text_3))
        recyclerViewItems.add(RecyclerViewItem(R.mipmap.image_4, R.string.rv_text_4))
        recyclerViewItems.add(RecyclerViewItem(R.mipmap.image_5, R.string.rv_text_5))
        binding.vp2Recycler.adapter = RecyclerViewPagerAdapter()

        binding.vp2Fragment.adapter = FragmentStateViewPagerAdapter(supportFragmentManager, lifecycle)
    }
}