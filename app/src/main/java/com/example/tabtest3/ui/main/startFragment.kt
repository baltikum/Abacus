package com.example.tabtest3.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.tabtest3.R



    class startFragment : Fragment() {

        private var ctx: Context? = null
        private var self: View? = null

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            ctx = container?.context
            self = LayoutInflater.from(ctx).inflate(R.layout.fragment_start, container, false)

            val button1 = self?.findViewById<Button>(R.id.button1)
            val button2 = self?.findViewById<Button>(R.id.button2)
            val button3 = self?.findViewById<Button>(R.id.button3)
            val button4 = self?.findViewById<Button>(R.id.button4)
            val button5 = self?.findViewById<Button>(R.id.button5)
            val button6 = self?.findViewById<Button>(R.id.button6)
            val resultTextView1 = self?.findViewById<TextView>(R.id.showInfo1)
            val resultTextView2 = self?.findViewById<TextView>(R.id.showInfo2)
            val resultTextView3 = self?.findViewById<TextView>(R.id.showInfo3)
            val resultTextView4 = self?.findViewById<TextView>(R.id.showInfo4)
            val resultTextView5 = self?.findViewById<TextView>(R.id.showInfo5)
            val resultTextView6 = self?.findViewById<TextView>(R.id.showInfo6)
            val resultTextView7 = self?.findViewById<TextView>(R.id.showInfo7)

            button1?.setOnClickListener {

                resultTextView1?.text = "Femman"
                resultTextView2?.text = "1253.3"
                resultTextView3?.text = "84"
                resultTextView4?.text = "4452"
                resultTextView5?.text = "75 %"
                resultTextView6?.text = "0.532"
                resultTextView7?.text = "0.9363"

                Toast.makeText(ctx, "button works!", Toast.LENGTH_SHORT).show()

            }
            button2?.setOnClickListener {
                resultTextView1?.text = "Haga Norra"
                resultTextView2?.text = "2313"
                resultTextView3?.text = "44"
                resultTextView4?.text = "258"
                resultTextView5?.text = "12 %"
                resultTextView6?.text = "4.632"
                resultTextView7?.text = "933"

            }
            button3?.setOnClickListener {

                resultTextView1?.text = "Lejonet"
                resultTextView2?.text = "3215.3"
                resultTextView3?.text = "213"
                resultTextView4?.text = "5454"
                resultTextView5?.text = "15 %"
                resultTextView6?.text = "3.55"
                resultTextView7?.text = "0.88"
            }

            button4?.setOnClickListener {

                resultTextView1?.text = "Mobil 1"
                resultTextView2?.text = "38.3"
                resultTextView3?.text = "1"
                resultTextView4?.text = "65"
                resultTextView5?.text = "88 %"
                resultTextView6?.text = "0.11"
                resultTextView7?.text = "15"
            }

            button5?.setOnClickListener {

                resultTextView1?.text = "Mobil 2"
                resultTextView2?.text = "989.5"
                resultTextView3?.text = "45"
                resultTextView4?.text = "8"
                resultTextView5?.text = "1 %"
                resultTextView6?.text = "0.6834"
                resultTextView7?.text = "0.868"
            }

            button6?.setOnClickListener {

                resultTextView1?.text = "Mobil 3"
                resultTextView2?.text = "12.3"
                resultTextView3?.text = "8686"
                resultTextView4?.text = "15"
                resultTextView5?.text = "23 %"
                resultTextView6?.text = "1.354"
                resultTextView7?.text = "1.11"
            }

            return self
        }
    }


