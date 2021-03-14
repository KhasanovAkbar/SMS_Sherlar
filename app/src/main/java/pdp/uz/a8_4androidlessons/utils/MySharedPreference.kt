package pdp.uz.a8_4androidlessons.utils

import android.content.Context
import android.content.SharedPreferences

/*
class MySharedPreference (private var context:Context){

    private var gson = Gson()
    private var sharedPreference = context.getSharedPreferences("POEM", Context.MODE_PRIVATE)

    fun getInfo():MutableList<Main>{
        val string = sharedPreference.getString("info", "")
        if (string != ""){
            val type: Type = object : TypeToken<MutableList<Main>>(){}.type
            return gson.fromJson(string, type)
        }
        else return mutableListOf()
    }

    @SuppressLint("CommitPrefEdits")
    fun addInfo(data:Main){
        val editor = sharedPreference.edit()
        val info =getInfo()
        info.add(data)
        val toJson = gson.toJson(info)
        editor.putString("info", toJson)
        editor.apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun removeInfo(isLiked:Boolean){
        val editor = sharedPreference.edit()
        val info = getInfo()
        val mutableListOf = mutableListOf<Main>()
        for (i in info){

        }
    }



}*/

object MySharedPreference {

    private const val NAME = "POEM"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var info: String?
        get() = sharedPreferences.getString("info", "")
        set(value) = sharedPreferences.edit {
            if (value != null) {
                it.putString("info", value)
            }
        }
}