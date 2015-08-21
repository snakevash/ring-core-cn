(ns ring-core-cn.util.request
  "处理请求映射的函数"
  (:require [ring-core-cn.util.parsing :refer [re-value]]))

(defn request-url
  "返回完整URL请求"
  {:added "1.2"}
  [request]
  ;; 协议 主机 地址 参数
  (str (-> request :scheme name)
       "://"
       (get-in request [:headers "host"])
       (:uri request)
       (if-let [query (:query-string request)]
         (str "?" query))))

(defn content-type
  "获取请求内容格式 没有则nil"
  {:added "1.3"}
  [request]
  (if-let [type (get-in request [:headers "content-type"])]
    (second (re-find #"^(.*?)(?:;|$)" type))))

(defn content-length
  "获取请求内容长度 没有则nil"
  {:added "1.3"}
  [request]
  (if-let [^String length (get-in request [:headers "content-length"])]
    (Long. length)))

;; 编码字符串
(def
  ^:private
  charset-pattern
  (re-pattern
   (str ";(?:.*\\s)?(?i:charset)=(" re-value ")\\s*(?:;|$)")))

(defn character-encoding
  "获取请求编码 没有则nil"
  {:added "1.3"}
  [request]
  (if-let [type (get-in request [:headers "content-type"])]
    (second (re-find charset-pattern type))))

(defn urlencoded-form?
  "判断是否是表单提交"
  {:added "1.3"}
  [request]
  (if-let [^String type (content-type request)]
    (.startsWith type "application/x-www-form-urlencoded")))

(defmulti body-string
  "获取请求体"
  {:arglists '([request])
   :added "1.2"}
  (comp class :body))

(defmethod body-string nil [_] nil)

(defmethod body-string
  String
  [request]
  (:body request))

(defmethod body-string
  clojure.lang.ISeq
  [request]
  (apply str (:body request)))

(defmethod body-string
  java.io.File
  [request]
  (slurp (:body request)))

(defmethod body-string
  java.io.InputStream
  [request]
  (slurp (:body request)))

(defn path-info
  "返回相对路径"
  {:added "1.2"}
  [request]
  (or (:path-info request)
      (:uri request)))

(defn in-context?
  "返回路径是否是请求的子路经"
  {:added "1.2"}
  [request context]
  (.startsWith ^String (:uri request) context))

(defn set-context
  "给请求增加子路径"
  {:added "1.2"}
  [request ^String context]
  {:pre [(in-context? request context)]}
  (assoc request
    :context context
    :path-info (subs (:uri request) (.length context))))
