package com.example.ultimateencyclopedia.move

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.ultimateencyclopedia.R
import com.example.ultimateencyclopedia.database.fighter.FighterEntity
import com.example.ultimateencyclopedia.utils.UltimateEnums.Companion.ENUM_FIGHTER
import com.example.ultimateencyclopedia.utils.UltimateEnums.Companion.ENUM_MOVE
import com.example.ultimateencyclopedia.database.move.MoveEntity
import com.example.ultimateencyclopedia.home.BaseActivity
import com.example.ultimateencyclopedia.utils.Utils

class MoveDetailActivity : BaseActivity() {
    private lateinit var tvFighterName: TextView
    private lateinit var tvInput: TextView
    private lateinit var ivHitbox: ImageView
    private lateinit var tvDamage: TextView
    private lateinit var tvDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_move)
        hideStatusBar()
        bindViews()
        val fighterName = intent.getStringExtra(ENUM_FIGHTER)!!
        val fighter = Utils.retrieveFighterByName(fighterName, applicationContext)
        val moveId = intent.getIntExtra(ENUM_MOVE, -1)
        val move = Utils.getMoveById(moveId, applicationContext)
        setData(move, fighter)
        startBackgroundAnimation()
    }

    private fun bindViews() {
        tvFighterName = findViewById(R.id.tv_fighter_name)
        tvInput = findViewById(R.id.tv_move_input)
        ivHitbox = findViewById(R.id.iv_hitbox)
        tvDamage = findViewById(R.id.tv_move_damage)
        tvDescription = findViewById(R.id.tv_full_description)
    }

    private fun setData(move: MoveEntity?, fighter: FighterEntity?) {
        if (fighter != null && move != null) {
            tvFighterName.text = fighter.name
            tvInput.text = move.input
            tvDescription.text = move.description
            tvDamage.text = move.damage // Utils.getDamageStringHorizontal(move.damage)

            Glide.with(applicationContext)
                .load(move.hitbox)
                .placeholder(Utils.getProgressIndicator(applicationContext))
                .error(R.drawable.ic_broken_image)
                .listener(hideProgressAfterLoadListener())
                .into(ivHitbox)
        }
    }

    private fun hideProgressAfterLoadListener(): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                val layoutParams = ivHitbox.layoutParams
                layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                ivHitbox.layoutParams = layoutParams
                ivHitbox.requestLayout()
                return false
            }

        }
    }
}