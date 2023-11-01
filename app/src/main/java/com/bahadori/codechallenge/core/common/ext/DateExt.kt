package com.bahadori.codechallenge.core.common.ext

import java.text.DateFormat
import java.util.Date


fun Date.format(): String {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(this)
}