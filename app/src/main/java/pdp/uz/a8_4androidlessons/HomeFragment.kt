package pdp.uz.a8_4androidlessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.view.*
import pdp.uz.a8_4androidlessons.models.Info
import pdp.uz.a8_4androidlessons.models.Info2
import pdp.uz.a8_4androidlessons.utils.MySharedPreference
import java.lang.reflect.Type


class HomeFragment : Fragment() {
    lateinit var categoryList: ArrayList<String>

    //    lateinit var lst: ArrayList<Info2>
    private var savedList = ArrayList<Info>()
    private var gson = Gson()
    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        MySharedPreference.init(container!!.context)
        val bundle = Bundle()
        val type: Type = object : TypeToken<ArrayList<Info2>>() {}.type
        loadData()
        val info = MySharedPreference.info
        val fromJson = gson.fromJson<ArrayList<Info2>>(info, type)
        val navOptions = NavOptions.Builder()
        navOptions.setEnterAnim(R.anim.exit_anim)
        navOptions.setExitAnim(R.anim.pop_enter_anim)
        navOptions.setPopEnterAnim(R.anim.enter_anim)
        navOptions.setPopExitAnim(R.anim.pop_exit_anim)
        root.sevgi_btn.setOnClickListener {
            bundle.putInt("id", 0)
            bundle.putString("category", categoryList[0])
            root.findNavController().navigate(R.id.firstFragment, bundle, navOptions.build())

        }
        root.soginch_btn.setOnClickListener {

            bundle.putInt("id", 1)
            bundle.putString("category", categoryList[1])
            root.findNavController().navigate(R.id.firstFragment, bundle, navOptions.build())
        }
        root.tabrik_btn.setOnClickListener {
            bundle.putInt("id", 2)
            bundle.putString("category", categoryList[2])
            root.findNavController().navigate(R.id.firstFragment, bundle, navOptions.build())
        }
        root.parent_btn.setOnClickListener {
            bundle.putInt("id", 3)
            bundle.putString("category", categoryList[3])
            root.findNavController().navigate(R.id.firstFragment, bundle, navOptions.build())
        }
        root.friend_btn.setOnClickListener {
            bundle.putInt("id", 4)
            bundle.putString("category", categoryList[4])
            root.findNavController().navigate(R.id.firstFragment, bundle, navOptions.build())
        }
        root.hazil_btn.setOnClickListener {

            bundle.putInt("id", 5)
            bundle.putString("category", categoryList[5])
            root.findNavController().navigate(R.id.firstFragment, bundle, navOptions.build())
        }

        root.yangilar.setOnClickListener {
//            bundle.putString("category", categoryList[6])
            root.findNavController().navigate(R.id.yangilarFragment, bundle, navOptions.build())
        }

        root.saqlanganlar.setOnClickListener {
//            bundle.putString("category", categoryList[7])
            root.findNavController().navigate(R.id.saqlanganlarFragment, bundle, navOptions.build())
        }

        var isSavedCount = 0
        root.poem_number.text =
            (fromJson[0].info!!.size + fromJson[1].info!!.size + fromJson[2].info!!.size + fromJson[3].info!!.size + fromJson[4].info!!.size + fromJson[5].info!!.size).toString()
        for (i in fromJson) {
            for (j in i.info!!) {
                if (j.isSaved) {
                    isSavedCount++
                }
            }
        }


        root.poem_saved.text = isSavedCount.toString()
        return root
    }

    private fun loadData() {
        categoryList = arrayListOf(
            "Sevgi she'rlari",
            "Sog'inch armon",
            "Tabrik she'rlari",
            "Ota-ona haqida",
            "Do'stlik she'rlari",
            "Hazil she'rlari",
            "Yangilar",
            "Saqlanganlar"
        )
    }
}