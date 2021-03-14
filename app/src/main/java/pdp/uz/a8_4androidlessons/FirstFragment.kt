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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.custom_dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import pdp.uz.a8_4androidlessons.adapters.PoemAdapter
import pdp.uz.a8_4androidlessons.models.Info
import pdp.uz.a8_4androidlessons.models.Info2
import pdp.uz.a8_4androidlessons.utils.MySharedPreference
import java.lang.reflect.Type


class FirstFragment : Fragment() {

    lateinit var fromJson: ArrayList<Info2>
    private var gson = Gson()
    lateinit var poemAdapter: PoemAdapter
    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_first, container, false)
        root.back_btn.setOnClickListener {

        }


        MySharedPreference.init(container!!.context)
        val type: Type = object : TypeToken<ArrayList<Info2>>() {}.type
        val info = MySharedPreference.info
        fromJson = gson.fromJson(info, type)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val activity = activity as? MainActivity
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        val category = arguments?.getString("category")
        category_tv.text = category
        rv.apply {
            val id = arguments?.getInt("id")
            poemAdapter = PoemAdapter(
                fromJson[id!!].info,
                object : PoemAdapter.OnMyItemLongClickListener {

                    @SuppressLint("UseCompatLoadingForDrawables")
                    override fun onMyItemLongClick(data: Info, position: Int) {
                        bundle.putSerializable("info", data)

                        val customDialog = AlertDialog.Builder(context)
                        val create = customDialog.create()
                        val inflate =
                            layoutInflater.inflate(R.layout.custom_dialog_view, null, false)
                        create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                    create.window?.setWindowAnimations(AnimationUtils.loadAnimation(context, R.anim.fragment_close_enter))
//                    create.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
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
                        if (fromJson[id].info!![position].isSaved) {
                            inflate.heart.setImageDrawable(resources.getDrawable(R.drawable.like))
                        } else {
                            inflate.heart.setImageDrawable(resources.getDrawable(R.drawable.love))
                        }
                        inflate.send_btn.setOnClickListener {
                            Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show()
                        }
                        var clickCount = 0
//                        tv.text = "asfdfsdf"
                        inflate.saved_btn.setOnClickListener {
                            clickCount++
                            if (clickCount % 2 != 0) {
                                if (fromJson[id].info!![position].isSaved) {
                                    inflate.heart.setImageDrawable(resources.getDrawable(R.drawable.love))
                                    Toast.makeText(context, "Not saved", Toast.LENGTH_SHORT).show()
                                    fromJson[id].info!![position].isSaved = false

                                } else {
                                    inflate.heart.setImageDrawable(resources.getDrawable(R.drawable.like))
                                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                                    fromJson[id].info!![position].isSaved = true

                                }
                            } else {
                                if (fromJson[id].info!![position].isSaved) {
                                    inflate.heart.setImageDrawable(resources.getDrawable(R.drawable.love))
                                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                                    fromJson[id].info!![position].isSaved = false

                                } else {
                                    inflate.heart.setImageDrawable(resources.getDrawable(R.drawable.like))
                                    Toast.makeText(context, "Not saved", Toast.LENGTH_SHORT).show()
                                    fromJson[id].info!![position].isSaved = true
                                }
                            }

                            val toJson = gson.toJson(fromJson)
                            MySharedPreference.info = toJson

                        }
                        inflate.share_btn.setOnClickListener {
                            Toast.makeText(context, "Shared", Toast.LENGTH_SHORT).show()
                        }
                        inflate.copy_btn.setOnClickListener {
                            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
                        }

                        create.show()
                    }

                })
            rv.adapter = poemAdapter
        }
    }
}