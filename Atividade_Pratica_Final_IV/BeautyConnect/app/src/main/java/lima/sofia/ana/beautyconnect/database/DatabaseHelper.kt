package lima.sofia.ana.beautyconnect.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import lima.sofia.ana.beautyconnect.model.Produto

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "beauty_connect.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PRODUTOS = "produtos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOME = "nome"
        private const val COLUMN_MARCA = "marca"
        private const val COLUMN_QUANTIDADE = "quantidade"
        private const val COLUMN_PRECO = "preco"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_PRODUTOS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOME + " TEXT, "
                + COLUMN_MARCA + " TEXT, "
                + COLUMN_QUANTIDADE + " INTEGER, "
                + COLUMN_PRECO + " REAL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUTOS")
        onCreate(db)
    }

    fun addProduto(produto: Produto): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOME, produto.nome)
            put(COLUMN_MARCA, produto.marca)
            put(COLUMN_QUANTIDADE, produto.quantidade)
            put(COLUMN_PRECO, produto.preco)
        }
        val result = db.insert(TABLE_PRODUTOS, null, values)
        db.close()
        return result != -1L
    }

    fun getAllProdutos(): List<Produto> {
        val lista = mutableListOf<Produto>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUTOS ORDER BY $COLUMN_ID DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME))
                val marca = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MARCA))
                val qtd = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTIDADE))
                val preco = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECO))
                lista.add(Produto(id, nome, marca, qtd, preco))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun getTotalEstoquePorMarca(marca: String): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT SUM($COLUMN_QUANTIDADE) FROM $TABLE_PRODUTOS WHERE LOWER($COLUMN_MARCA) = LOWER(?)",
            arrayOf(marca)
        )
        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return total
    }
}