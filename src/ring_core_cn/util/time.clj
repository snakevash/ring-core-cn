(ns ring-core-cn.util.time
  "处理http请求日期和时间的问题"
  (:require [clojure.string :as str])
  (:import [java.text ParseException SimpleDateFormat]
           [java.util Locale TimeZone]))

;; 定义一些时间字符串的格式
(def
  ^:no-doc
  http-date-formats
  {:rfc1123 "EEE, dd MMM yyyy HH:mm:ss zzz"
   :rfc1036 "EEEE, dd-MMM-yy HH:mm:ss zzz"
   :asctime "EEE MMM d HH:mm:ss yyyy"})

;; 美国本地时间
(defn-
  ^SimpleDateFormat
  formatter [format]
  (doto (SimpleDateFormat. ^String (http-date-formats format) Locale/US)
    (.setTimeZone (TimeZone/getTimeZone "GMT"))))

;; 尝试解析时间 如果出错就返回nil
(defn- attempt-parse [date format]
  (try
    (.parse (formatter format) date)
    (catch ParseException _ nil)))

;; 去掉引号
(defn- trim-quotes [s]
  (str/replace s #"^'|'$" ""))

(defn parse-date
  "尝试解析HTTP日期
  返回nil如果没有成功"
  {:added "1.2"}
  [http-date]
  (->> (keys http-date-formats)
       (map (partial attempt-parse (trim-quotes http-date)))
       (remove nil?)
       (first)))

(defn format-date
  "RFC1123格式化"
  {:added "1.2"}
  [^java.util.Date date]
  (.format (formatter :rfc1123) date))
