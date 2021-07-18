package ru.gendalf13666.combustiblelubricants

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var editNumber1: EditText? = null
    private var editNumber2: EditText? = null
    private var button: Button? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // находим элементы
        editNumber1 = findViewById(R.id.editNumber1)
        editNumber2 = findViewById(R.id.editNumber2)
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)

        editNumber1?.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(6))
        editNumber2?.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(6))

        // прописываем обработчик
        button!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val result: Float

        // Проверяем поля на пустоту и на длину
        if (TextUtils.isEmpty(editNumber1!!.text.toString()) ||
            TextUtils.isEmpty(editNumber2!!.text.toString())
        ) {
            Toast.makeText(this, "Вам нужно внести значения в обе формы", Toast.LENGTH_SHORT).show()
            return
        } else if (editNumber1!!.length() > 100000 && editNumber2!!.length() > 100000) {
            Toast.makeText(this, "Вы ввели слишком большое значение", Toast.LENGTH_SHORT).show()
            return
        }

        // читаем EditText и заполняем переменные числами
        val num1: Float = editNumber1!!.text.toString().toFloat()
        val num2: Float = editNumber2!!.text.toString().toFloat()

        result = num1 - num2

        // формируем строку вывода
        val result2: Double = String.format("%.6f", result).toDouble()
        textView!!.text = "$result2"
    }

    // ограничиваем знаки после запятой
    class DecimalDigitsInputFilter(digitsAfterZero: Int) : InputFilter {
        var mPattern: Pattern
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            val matcher: Matcher = mPattern.matcher(dest)
            return if (!matcher.matches()) "" else null
        }

        init {
            mPattern =
                Pattern.compile("[0-9]+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
        }
    }
}
