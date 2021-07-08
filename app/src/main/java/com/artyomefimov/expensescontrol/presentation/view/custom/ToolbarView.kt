package com.artyomefimov.expensescontrol.presentation.view.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.artyomefimov.expensescontrol.R
import com.artyomefimov.expensescontrol.databinding.ViewToolbarBinding

/**
 * Элемент тулбара с текстом и иконкой справа
 */
class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewToolbarBinding

    /**
     * Текст тулбара
     */
    var title: String = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    /**
     * Видимость иконки тулбара
     */
    var isIconVisible: Boolean = true
        set(value) {
            field = value
            binding.toolbarIconButton.isVisible = value
        }

    /**
     * Действие по нажатию на иконку
     */
    var onIconPressedListener: (() -> Unit)? = null

    /**
     * Иконка тулбара
     */
    private var icon: Drawable? = null
        set(value) {
            field = value
            with(binding.toolbarIconButton) {
                value?.let { setImageDrawable(it) }
                isVisible = value != null
            }

        }

    init {
        inflate(context, R.layout.view_toolbar, this)
        binding = ViewToolbarBinding.bind(this)
        binding.root.background = ContextCompat.getDrawable(context, R.color.colorPrimaryDark)
        binding.toolbarIconButton.setOnClickListener {
            onIconPressedListener?.invoke()
        }
        applyAttributes(attrs, defStyleAttr)
    }

    private fun applyAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.withStyledAttributes(
            set = attrs,
            attrs = R.styleable.ToolbarView,
            defStyleAttr = defStyleAttr,
        ) {
            title = getString(R.styleable.ToolbarView_title).orEmpty()
            icon = getDrawable(R.styleable.ToolbarView_icon)
        }
    }
}