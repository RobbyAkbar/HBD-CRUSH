package com.example.hbdcrush.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hbdcrush.R
import com.example.hbdcrush.TypeWriter

/**
 * Created by robby on 31/07/22.
 */
class OnBoardingItemsAdapter(private val onBoardingItems: List<OnBoardingItem>): RecyclerView.Adapter<OnBoardingItemsAdapter.OnBoardingItemViewHolder>() {

    private lateinit var textDescription: TypeWriter

    inner class OnBoardingItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val imageOnBoarding = view.findViewById<ImageView>(R.id.imageOnBoarding)
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)

        fun bind(onBoardingItem: OnBoardingItem) {
            imageOnBoarding.setImageResource(onBoardingItem.onBoardingImage)
            textTitle.text = onBoardingItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item_container, parent, false
            )
        )
    }

    fun animateText(position: Int) {
        textDescription.text = ""
        textDescription.setCharacterDelay(150)
        textDescription.animateText(onBoardingItems[position].description)
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        textDescription = holder.itemView.findViewById(R.id.textDescription)

        textDescription.text = ""
        textDescription.setCharacterDelay(150)
        textDescription.animateText(onBoardingItems[position].description)

        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

}
