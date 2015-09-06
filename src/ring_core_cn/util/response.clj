(ns ring-core-cn.util.response
  ""
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [ring-core-cn.util.io :refer [last-modified-date]]
            [ring-core-cn.util.time :refer [format-date]])
  (:import [java.io File]
           [java.util Date]
           [java.net URL URLDecoder URLEncoder]))

(def
  ^{:added "1.4"}
  redirect-status-codes
  "跳转映射状态码"
  {:moved-permanently 301
   :found 302
   :see-other 303
   :temporary-redirect 307
   :permanent-redirect 308})

(defn redirect
  "跳转302的回复
  状态码是映射里的数字
  默认302"
  ([url] (redirect url :found))
  ([url status]
   {:status (redirect-status-codes status status)
    :headers {"Location" url}
    :body ""}))

(defn redirect-after-post
  "303回复返回
  建议使用状态码映射"
  {:deprecated "1.4"}
  [url]
  {:status 303
   :headers {"Location" url}
   :body ""})

(defn created
  ""
  {:added "1.2"}
  ([url] (created url nil))
  ([url body]
   {:status 201
    :headers {"Location" url}
    :body body}))
