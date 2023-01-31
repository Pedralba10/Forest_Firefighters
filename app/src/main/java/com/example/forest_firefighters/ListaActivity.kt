package com.example.forest_firefighters

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.Toast
import com.example.forest_firefighters.databinding.ActivityListaBinding
import com.example.forest_firefighters.databinding.ActivityRecordBinding


private lateinit var binding: ActivityListaBinding
lateinit var dbHelper: DBHelper

class ListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery("select * from incendio", null)

        val adaptador = CursorAdapterListView(this, cursor)
        binding.lista.adapter = adaptador
        db.close()
     }

    inner class CursorAdapterListView(context: Context, cursor: Cursor):
        CursorAdapter(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER) {
        override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
            val inflate = LayoutInflater.from(context)
            return inflate.inflate(R.layout.activity_record, parent, false)
        }

        override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
            val bindingItems = ActivityRecordBinding.bind(view!!)
            bindingItems.tvNombre.text = cursor!!.getString(1)
            bindingItems.tvProvincia.text = cursor!!.getString(2)
            bindingItems.tvDia.text = cursor!!.getString(12)

            view.setOnClickListener{
                Toast.makeText(this@ListaActivity, "${bindingItems.tvNombre.text} ," +
                    "${bindingItems.tvProvincia.text}", Toast.LENGTH_SHORT).show()
            }



        }

    }

}

