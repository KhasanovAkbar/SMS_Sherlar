package pdp.uz.a8_4androidlessons

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.custom_dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_saqlanganlar.*
import pdp.uz.a8_4androidlessons.adapters.SavedAdapter
import pdp.uz.a8_4androidlessons.models.Info
import pdp.uz.a8_4androidlessons.models.Info2
import pdp.uz.a8_4androidlessons.utils.MySharedPreference
import java.lang.reflect.Type


class SaqlanganlarFragment : Fragment() {
    lateinit var fromJson: ArrayList<Info2>
    lateinit var savedAdapter: SavedAdapter
    private var arr = arrayListOf<Info>()
    private var gson = Gson()

    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_saqlanganlar, container, false)
        MySharedPreference.init(container!!.context)
        val type: Type = object : TypeToken<ArrayList<Info2>>() {}.type
        val info = MySharedPreference.info
        fromJson = gson.fromJson(info, type)
        for (i in fromJson) {
            for (j in i.info!!) {
                if (j.isSaved) {
                    arr.add(j)
                }
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        rv_saved.apply {
            savedAdapter = SavedAdapter(
                arr,
                object : SavedAdapter.OnMyItemClickListener {

                    @SuppressLint("UseCompatLoadingForDrawables")
                    override fun onMyItemClick(data: Info, position: Int) {
                        bundle.putSerializable("info", data)

                        val customDialog = AlertDialog.Builder(context)
                        val create = customDialog.create()
                        val inflate =
                            layoutInflater.inflate(R.layout.custom_dialog_view, null, false)
                        create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        (create.window?.decorView as ViewGroup)
                            .getChildAt(0).startAnimation(
                                AnimationUtils.loadAnimation(
                                    context, android.R.anim.slide_in_left
                                )
                            )

                        val main = bundle.getSerializable("info") as Info
                        create.setView(inflate)

                        inflate.poem_title.text = main.title
                        inflate.poem_info.text = main.poem
                        inflate.send_btn.visibility = View.INVISIBLE
                        inflate.saved_btn.visibility = View.INVISIBLE
                        inflate.share_btn.visibility = View.INVISIBLE
                        inflate.copy_btn.visibility = View.INVISIBLE
                        create.show()
                    }
                })
            rv_saved.adapter = savedAdapter
        }

    }

}