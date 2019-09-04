package mdev.k017_readjsonfileassets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import mdev.k017_readjsonfileassets.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class MainActivity : AppCompatActivity() , View.OnClickListener{

    lateinit var binding : ActivityMainBinding
    lateinit var dataSet : ArrayList<String>
    var indexFocus = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        dataSet = ArrayList()
        setOnClickView()
        loadJsonFile()
        loadJsonFile()?.let {
            getJSONArray(it)
        }
    }

    private fun getJSONArray(txtJson: String){
        var jObj : JSONObject
        var jArr = JSONArray(txtJson.substring(txtJson.indexOf("["),txtJson.lastIndexOf("]") + 1))
        for (i in 0 until jArr.length()){
            jObj = jArr.getJSONObject(i)
            dataSet.add(jObj.getString("name"))
        }
        if (dataSet.size > 0){
            indexFocus++
            binding.tvName.text = dataSet[indexFocus]
        }
    }

    private fun loadJsonFile() : String?{
        var json: String?
        try {
            val  inputStream: InputStream = assets.open("versions.json")
            json = inputStream.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun setOnClickView() {
        binding.tvPrevious.setOnClickListener(this)
        binding.tvNext.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvPrevious ->{
                if (indexFocus > 1) {
                    indexFocus--
                    binding.tvName.text = dataSet[indexFocus]
                }
            }
            R.id.tvNext ->{
                if (indexFocus < dataSet.size - 1){
                    indexFocus++
                    binding.tvName.text = dataSet[indexFocus]
                }

            }
        }
    }
}
