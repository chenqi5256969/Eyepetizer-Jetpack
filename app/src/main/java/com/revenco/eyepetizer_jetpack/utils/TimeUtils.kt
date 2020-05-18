package com.revenco.eyepetizer_jetpack.utils


class TimeUtils {
    companion object {
        /**
         * 将int类型数字转换成时分秒毫秒的格式数据
         *
         * @param time long类型的数据
         * @return mm:ss
         */
        fun msecToTime(time: Int): String {
            var timeStr: String? = null
            var hour: Int
            var minute: Int
            var second: Int
            if (time <= 0)
                return "00:00"
            else {
                second = time
                minute = second / 60
                when {
                    second < 60 -> timeStr = "00:" + unitFormat(second)
                    minute < 60 -> {
                        second %= 60
                        timeStr =
                            unitFormat(minute) + ":" + unitFormat(second)
                    }
                    else -> {
                        // 数字>=3600 000的时候
                        hour = minute / 60
                        minute %= 60
                        second = second - hour * 3600 - minute * 60
                        timeStr =
                            (unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second))
                    }
                }
            }
            return timeStr
        }

        private fun unitFormat(i: Int): String {
            // 时分秒的格式转换
            var retStr: String? = if (i in 0..9)
                "0$i"
            else
                "" + i
            return retStr.toString()

        }
    }
}