package com.superking.parchisi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService
import java.lang.Math.floor

class Prodigy : AppCompatActivity() {

    private var itemsRes = intArrayOf(
        R.drawable.item1,
        R.drawable.item2,
        R.drawable.item3,
        R.drawable.item4,
        R.drawable.item5,
        R.drawable.item6,
    )

    var noOfBlock = 8
    private var widthOfBlock = 0
    private var widthOfScreen = 0
    var itemToBeDragged = 0
    var itemToBeReplaced = 0

    // In ms
    private var interval = 200
    private var score = 0

    private var itemsVi = ArrayList<ImageView>()
    private var notCandy = R.drawable.transparent

    var mHandler: Handler? = null
    private var scoreResult: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prodigy)

        scoreResult = findViewById(R.id.score)

        // Android 10+
        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            getSystemService<WindowManager>()?.defaultDisplay?.getMetrics(displayMetrics)
        } else {
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        }

        widthOfScreen = 800
        displayMetrics.heightPixels

        widthOfBlock = widthOfScreen / noOfBlock
        createBoard()

        for (imageView in itemsVi) {
            imageView.setOnTouchListener(object : OnSwipeListener(this@Prodigy) {
                override fun OnSwipeLeft() {
                    super.OnSwipeLeft()
                    itemToBeDragged = imageView.id
                    itemToBeReplaced = itemToBeDragged - 1
                    candyInterChange()
                }

                override fun OnSwipeRight() {
                    super.OnSwipeRight()
                    itemToBeDragged = imageView.id
                    itemToBeReplaced = itemToBeDragged + 1
                    candyInterChange()
                }

                override fun OnSwipeTop() {
                    super.OnSwipeTop()
                    itemToBeDragged = imageView.id
                    itemToBeReplaced = itemToBeDragged - noOfBlock
                    candyInterChange()
                }

                override fun OnSwipeBottom() {
                    super.OnSwipeBottom()
                    itemToBeDragged = imageView.id
                    itemToBeReplaced = itemToBeDragged + noOfBlock
                    candyInterChange()
                }
            })
        }

        mHandler = Handler(Looper.getMainLooper())
        startRepeat()
    }

    private fun checkRowThree() {
        for (i in 0..61) {
            val choseItem = itemsVi[i].tag as Int
            val isBlank = itemsVi[i].tag as Int == notCandy
            val notValid = arrayOf(6, 7, 14, 15, 22, 23, 30, 31, 39, 46, 47, 54, 55)
            val list = listOf(*notValid)

            if (!list.contains(i)) {
                var x = i
                if (itemsVi[x++].tag as Int == choseItem && !isBlank && itemsVi[x++].tag as Int == choseItem && itemsVi[x].tag as Int == choseItem) {
                    score += 3
                    scoreResult!!.text = score.toString()
                    itemsVi[x].setImageResource(notCandy)
                    itemsVi[x].tag = notCandy
                    x--
                    itemsVi[x].setImageResource(notCandy)
                    itemsVi[x].tag = notCandy
                    x--
                    itemsVi[x].setImageResource(notCandy)
                    itemsVi[x].tag = notCandy
                }
            }
        }
        moveDownItems()
    }

    private fun checkColumnThree() {
        for (i in 0..46) {
            val choseItem = itemsVi[i].tag as Int
            val isBlank = itemsVi[i].tag as Int == notCandy
            var x = i
            if (itemsVi[x].tag as Int == choseItem && !isBlank && itemsVi[x + noOfBlock].tag as Int == choseItem && itemsVi[x + 2 * noOfBlock].tag as Int == choseItem) {
                score += 3
                scoreResult!!.text = score.toString()
                itemsVi[x].setImageResource(notCandy)
                itemsVi[x].tag = notCandy
                x += noOfBlock
                itemsVi[x].setImageResource(notCandy)
                itemsVi[x].tag = notCandy
                x += noOfBlock
                itemsVi[x].setImageResource(notCandy)
                itemsVi[x].tag = notCandy
            }
        }
        moveDownItems()
    }

    private fun moveDownItems() {
        val firstRow = arrayOf(0, 1, 2, 3, 4, 5, 6, 7)
        val list = listOf(*firstRow)
        for (i in 55 downTo 0) {
            if (itemsVi[i + noOfBlock].tag as Int == notCandy) {
                itemsVi[i + noOfBlock].setImageResource(itemsVi[i].tag as Int)
                itemsVi[i + noOfBlock].tag = itemsVi[i].tag
                itemsVi[i].setImageResource(notCandy)
                itemsVi[i].tag = notCandy
                if (list.contains(i) && itemsVi[i].tag as Int == notCandy) {
                    val randomColor = floor(Math.random() * itemsRes.size).toInt()
                    itemsVi[i].setImageResource(itemsRes[randomColor])
                    itemsVi[i].tag = itemsRes[randomColor]
                }
            }
        }
        for (i in 0..7) {
            if (itemsVi[i].tag as Int == notCandy) {
                val randomColor = floor(Math.random() * itemsRes.size).toInt()
                itemsVi[i].setImageResource(itemsRes[randomColor])
                itemsVi[i].tag = itemsRes[randomColor]
            }
        }
    }

    private var repeatChecker: Runnable = object:Runnable {
        override fun run() {
            try {
                checkRowThree()
                checkColumnThree()
                moveDownItems()
            } finally {
                mHandler!!.postDelayed(this, interval.toLong())
            }
        }
    }

    private fun startRepeat() {
        repeatChecker.run()
    }

    private fun candyInterChange() {
        val background = itemsVi[itemToBeReplaced].tag as Int
        val background1 = itemsVi[itemToBeDragged].tag as Int

        itemsVi[itemToBeDragged].setImageResource(background)
        itemsVi[itemToBeReplaced].setImageResource(background1)

        itemsVi[itemToBeDragged].tag = background
        itemsVi[itemToBeReplaced].tag = background1

    }

    private fun createBoard() {
        val gridLayout = findViewById<GridLayout>(R.id.fieldItems)

        gridLayout.rowCount = noOfBlock
        gridLayout.columnCount = noOfBlock

        gridLayout.layoutParams.width = widthOfScreen
        gridLayout.layoutParams.height = widthOfScreen

        for (i in 0 until noOfBlock * noOfBlock) {
            val imageView = ImageView(this)
            imageView.id = i
            imageView.layoutParams = ViewGroup.LayoutParams(widthOfBlock, widthOfBlock)

            imageView.maxHeight = widthOfBlock
            imageView.maxWidth = widthOfBlock
            val randomCandy = floor(Math.random() * itemsRes.size).toInt()

            imageView.setImageResource(itemsRes[randomCandy])
            imageView.tag = itemsRes[randomCandy]

            itemsVi.add(imageView)
            gridLayout.addView(imageView)
        }
    }
}