(ns ring-core-cn.util.parsing
  "HTTP正则表达式
  内部使用")

(def
  ^{:doc "RFC2068 HTTP 串"
    :added "1.3"}
  re-token
  #"[!#$%&'*\-+.0-9A-Z\^_`a-z\|~]+")

(def
  ^{:doc "RFC2068 引号"
    :added "1.3"}
  re-quoted
  #"\"(\\\"|[^\"])*\"")

(def
  ^{:doc "RFC2109"
    :added "1.3"}
  re-value
  (str re-token "|" re-quoted))
