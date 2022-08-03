package com.example.hbdcrush

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.hbdcrush.onboarding.OnBoardingItem
import com.example.hbdcrush.onboarding.OnBoardingItemsAdapter
import com.google.android.material.button.MaterialButton
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class MainActivity : AppCompatActivity() {

    private lateinit var onBoardingItemsAdapter: OnBoardingItemsAdapter
    private lateinit var indicatorsContainer: LinearLayout

    private lateinit var viewKonfetti: KonfettiView
    private lateinit var music: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnBoardingItem()
        setupIndicators()
        setCurrentIndicator(0)

        viewKonfetti = findViewById(R.id.viewKonfetti)
        music = MediaPlayer.create(this, R.raw.hbd)
    }

    override fun onStart() {
        super.onStart()
        music.start()
    }

    override fun onPause() {
        super.onPause()
        music.pause()
    }

    override fun onResume() {
        super.onResume()
        music.start()
    }

    override fun onStop() {
        super.onStop()
        music.stop()
    }

    private fun setOnBoardingItem() {
        onBoardingItemsAdapter = OnBoardingItemsAdapter(
            listOf(
                OnBoardingItem(
                    onBoardingImage = R.drawable.ballon,
                    title = "Selamat Ulang Tahun",
                    description = "Di hari ulang tahunmu hari ini, " +
                            "lorem ipsum dolor sit amet. jangan cuman bisa menyusun puluhan ribu bari code aja. ayo cobalah untuk merangkai kalimat indah yang tulus dari hatimu untuk sang pujaan hati."
                ),
                OnBoardingItem(
                    onBoardingImage = R.drawable.cake,
                    title = "Nama Crushmu",
                    description = "Aku ingin memberikanmu lorem ipsum dolor sit amet, karena " +
                            "mungkin kamu sudah biasa mendapatkan hadiah maupun ucapan dengan cara mainstream. Aku ingin menjadi yang berbeda untuk kamu."
                ),
                OnBoardingItem(
                    onBoardingImage = R.drawable.gift,
                    title = "Semoga Bahagia",
                    description = "Terimalah Gift Card App ini. " +
                            "Aplikasi ini premium dan limited edition. Tidak tersedia di Play Store, " +
                            "namun aku berikan secara gratis di hari ulang tahunmu. " +
                            "Selamat ulang tahun, semoga kamu bahagia selamanya!"
                )
            )
        )

        val onBoardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onBoardingViewPager.adapter = onBoardingItemsAdapter
        onBoardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                onBoardingItemsAdapter.animateText(position)
            }
        })
        (onBoardingViewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        findViewById<ImageView>(R.id.imageNext).setOnClickListener {
            if (onBoardingViewPager.currentItem + 1 < onBoardingItemsAdapter.itemCount) {
                onBoardingViewPager.currentItem += 1
            } else {
                konfetti()
            }
        }

        findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener {
            konfetti()
        }
    }

    private fun konfetti(){
        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(300, 5000L)
    }

    private fun setupIndicators() {
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onBoardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                    applicationContext, R.drawable.indicator_inactive_background
                ))
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i==position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}