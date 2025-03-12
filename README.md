# itechnika-example-viewpager2-android
RecyclerView.Adapter vs FragmentStateAdapter: Which Adapter is suitable for ViewPager2?

***

### Blog  
***Korean***: https://d2j2logs.blogspot.com/2025/03/recyclerviewadapter-vs-fragmentstateadapter.html  
***English***: https://d2j2logs-en.blogspot.com/2025/03/recyclerviewadapter-vs-fragmentstateadapter.html  
***Basaha Indonesia***: https://d2j2logs-id.blogspot.com/2025/03/recyclerviewadapter-vs-fragmentstateadapter.html  

***

### Run Example

<img src="https://github.com/user-attachments/assets/1d4344b0-242d-4228-9f02-bd56a6a9caaa" width="25%">


### RecyclerView.Adapter Example for ViewPager2

```kotlin
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
```

### FragmentStateAdapter Example for ViewPager2

```kotlin
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
```

