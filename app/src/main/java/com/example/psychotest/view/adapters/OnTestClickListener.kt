package com.example.psychotest.view.adapters

import com.example.psychotest.db.data.TestCard

interface OnTestClickListener {

    fun onClick(hide: Boolean, testCard: TestCard)
}